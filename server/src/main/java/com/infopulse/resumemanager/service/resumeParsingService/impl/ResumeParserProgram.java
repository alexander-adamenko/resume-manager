package com.infopulse.resumemanager.service.resumeParsingService.impl;

import com.infopulse.resumemanager.dto.parsed.ExtendedCandidate;
import gate.*;
import gate.util.GateException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static gate.Utils.stringFor;

@Component
public class ResumeParserProgram {

    public ExtendedCandidate parseUsingGateAndAnnie(String path) throws GateException, IOException {
        System.setProperty("gate.site.config", System.getProperty("user.dir")+"/GATEFiles/gate.xml");
        if (Gate.getGateHome() == null)
            Gate.setGateHome(new File(System.getProperty("user.dir")+"/GATEFiles"));
        if (Gate.getPluginsHome() == null)
            Gate.setPluginsHome(new File(System.getProperty("user.dir")+"/GATEFiles/plugins"));
        Gate.init();

        Annie annie = new Annie();
        annie.initAnnie();
        ExtendedCandidate extendedCandidate;
        File fileToParse = new File(path);
        URL u = fileToParse.toURI().toURL();
        FeatureMap params = Factory.newFeatureMap();
        params.put("sourceUrl", u);
        params.put("preserveOriginalContent", Boolean.TRUE);
        params.put("collectRepositioningInfo", Boolean.TRUE);
        Corpus corpus = Factory.newCorpus("Annie corpus");
        Document resume = (Document) Factory.createResource("gate.corpora.DocumentImpl", params);
        try {
            corpus.add(resume);
            annie.setCorpus(corpus);
            annie.execute();

            extendedCandidate = parseIntoCandidateExpand(corpus, path);
        } finally {
            corpus.clear();
            Factory.deleteResource(resume);


        }
        return extendedCandidate;

    }

    private ExtendedCandidate parseIntoCandidateExpand(Corpus corpus, String path){
        Iterator<Document> iter = corpus.iterator();
        ExtendedCandidate extendedCandidate = null;
        if (iter.hasNext()) {
            Document doc = (Document) iter.next();
            AnnotationSet defaultAnnotSet = doc.getAnnotations();

            String email = parseAnnSectionSingleRes("EmailFinder", defaultAnnotSet, doc);
            String phone = parseAnnSectionSingleRes("PhoneFinder", defaultAnnotSet, doc);
            String summary = parseSectionHeading("summary", defaultAnnotSet, doc);
            String education = parseSectionHeading("education_and_training", defaultAnnotSet, doc);
            List<Map<String, String>> skills = parseSectionHeadingWithMultipleSubSections("skills", defaultAnnotSet, doc);

            extendedCandidate = new ExtendedCandidate(email, phone, education, summary, path, skills);
        }
        return extendedCandidate;
    }

    private List<Map<String, String>> parseSectionHeadingWithMultipleSubSections(String section, AnnotationSet defaultAnnotSet, Document doc){
        AnnotationSet curAnnSet;
        Iterator it;
        Annotation currAnnot;

        curAnnSet = defaultAnnotSet.get(section);
        it = curAnnSet.iterator();
        List<Map<String, String>> sectionRes = new ArrayList<>();
        while (it.hasNext()) {
            Map<String, String> subSection = new HashMap<>();
            currAnnot = (Annotation) it.next();
            String key = (String) currAnnot.getFeatures().get("sectionHeading");
            String value = stringFor(doc, currAnnot);
            if (!StringUtils.isBlank(key) && !StringUtils.isBlank(value)) {
                subSection.put(key, value);
            }
            if (!subSection.isEmpty()) {
                sectionRes.add(subSection);
            }
        }
        return sectionRes;
    }

    private String parseSectionHeading(String section, AnnotationSet defaultAnnotSet, Document doc) {
        AnnotationSet curAnnSet;
        Iterator it;
        Annotation currAnnot;

        curAnnSet = defaultAnnotSet.get(section);
        it = curAnnSet.iterator();
        while (it.hasNext()) {
            currAnnot = (Annotation) it.next();
            String value = stringFor(doc, currAnnot);
            if (!StringUtils.isBlank(value)) {
                return value;
            }
        }
        return null;
    }

    private List<String> parseAnnSection(String annSection, AnnotationSet defaultAnnotSet, Document doc){
        AnnotationSet curAnnSet;
        Iterator it;
        Annotation currAnnot;

        curAnnSet = defaultAnnotSet.get(annSection);
        it = curAnnSet.iterator();
        List<String> strings = new ArrayList<>();
        while (it.hasNext()) {
            currAnnot = (Annotation) it.next();
            String s = stringFor(doc, currAnnot);
            if (s != null && s.length() > 0) {
                strings.add(s);
            }
        }
        return strings;
    }

    private String parseAnnSectionSingleRes(String annSection, AnnotationSet defaultAnnotSet, Document doc){
        AnnotationSet curAnnSet;
        Iterator it;
        Annotation currAnnot;

        curAnnSet = defaultAnnotSet.get(annSection);
        it = curAnnSet.iterator();
        while (it.hasNext()) {
            currAnnot = (Annotation) it.next();
            String s = stringFor(doc, currAnnot);
            if (s != null && s.length() > 0) {
                return s;
            }
        }
        return null;
    }
}
