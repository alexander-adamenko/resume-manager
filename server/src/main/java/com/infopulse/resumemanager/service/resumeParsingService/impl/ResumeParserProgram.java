package com.infopulse.resumemanager.service.resumeParsingService.impl;

import com.infopulse.resumemanager.dto.parsed.ExtendedCandidate;
import gate.*;
import gate.util.GateException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.ToXMLContentHandler;
import org.springframework.stereotype.Component;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.*;
import java.net.URL;
import java.util.*;

import static gate.Utils.stringFor;

@Component
public class ResumeParserProgram {

    public File parseToHTMLUsingApacheTikka(String file) throws IOException, SAXException, TikaException {
        String outputFileFormat = ".html";

        String OUTPUT_FILE_NAME = FilenameUtils.removeExtension(file) + outputFileFormat;
        ContentHandler handler = new ToXMLContentHandler();
        InputStream stream = new FileInputStream(file);
        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        try {
            parser.parse(stream, handler, metadata);
            FileWriter htmlFileWriter = new FileWriter(OUTPUT_FILE_NAME);
            htmlFileWriter.write(handler.toString());
            htmlFileWriter.close();
            return new File(OUTPUT_FILE_NAME);
        } finally {
            stream.close();
        }
    }

    public ExtendedCandidate parseUsingGateAndAnnie(File file, String path) throws GateException, IOException {
        System.setProperty("gate.site.config", System.getProperty("user.dir")+"/GATEFiles/gate.xml");
        if (Gate.getGateHome() == null)
            Gate.setGateHome(new File(System.getProperty("user.dir")+"/GATEFiles"));
        if (Gate.getPluginsHome() == null)
            Gate.setPluginsHome(new File(System.getProperty("user.dir")+"/GATEFiles/plugins"));
        Gate.init();

        Annie annie = new Annie();
        annie.initAnnie();

        Corpus corpus = Factory.newCorpus("Annie corpus");
        URL u = file.toURI().toURL();
        FeatureMap params = Factory.newFeatureMap();
        params.put("sourceUrl", u);
        params.put("preserveOriginalContent", Boolean.TRUE);
        params.put("collectRepositioningInfo", Boolean.TRUE);
        Document resume = (Document) Factory.createResource("gate.corpora.DocumentImpl", params);
        corpus.add(resume);

        annie.setCorpus(corpus);
        annie.execute();

        return parseIntoCandidateExpand(corpus, path);

    }

    private ExtendedCandidate parseIntoCandidateExpand(Corpus corpus, String path){
        Iterator<Document> iter = corpus.iterator();
        ExtendedCandidate extendedCandidate = null;
        if (iter.hasNext()) {
            Document doc = (Document) iter.next();
            AnnotationSet defaultAnnotSet = doc.getAnnotations();

            String email = parseAnnSectionSingleRes("EmailFinder", defaultAnnotSet, doc);
            String phone = parseAnnSectionSingleRes("PhoneFinder", defaultAnnotSet, doc);
            List<String> urls = parseAnnSection("URLFinder", defaultAnnotSet, doc);

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
