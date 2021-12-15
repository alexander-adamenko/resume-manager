package com.infopulse.resumemanager.service.resumeparsing.impl;

import com.infopulse.resumemanager.dto.parsed.ExtendedCandidate;
import com.infopulse.resumemanager.repository.entity.enums.City;
import gate.*;
import gate.util.GateException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


            String summary = parseSectionHeading("summary", defaultAnnotSet, doc);
            if(summary !=null) summary = summary.trim();
            String education = parseSectionHeading("education_and_training", defaultAnnotSet, doc);
            List<Map<String, String>> skills = parseSectionHeadingWithMultipleSubSections("skills", defaultAnnotSet, doc);

            String s = readFile(path);
            City city = getLocationOfCandidate(s);

            String phone = getPhoneOfCandidate(s);
            if (phone == null) phone = parseAnnSectionSingleRes("PhoneFinder", defaultAnnotSet, doc);
            String email = getEmailOfCandidate(s);
            if (email == null) email = parseAnnSectionSingleRes("EmailFinder", defaultAnnotSet, doc);


            extendedCandidate = new ExtendedCandidate(email, phone, education, summary, path,city, skills);
        }
        return extendedCandidate;
    }
    private String getPhoneOfCandidate(String s) {
        String patterns
                = "\\d{12}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}|\\d{2}\\(\\d{3}\\) ?\\d{3}-\\d{2}-\\d{2}";
        Pattern pattern = Pattern.compile(patterns);
        Matcher matcher = pattern.matcher(s);
        if(matcher.find()){
            return matcher.group(0);
        }
        return null;
    }
    private String getEmailOfCandidate(String s) {
        String patterns = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        Pattern pattern = Pattern.compile(patterns);
        Matcher matcher = pattern.matcher(s);
        if(matcher.find()){
            return matcher.group(0);
        }
        return null;
    }


    private City getLocationOfCandidate(String s) {
        City city = null;
        if(s != null){
            Optional<City> any = Arrays.stream(City.values())
                    .filter(eCity -> s.toLowerCase().contains(eCity.toString().toLowerCase()))
                    .findAny();
            if(any.isPresent()){
                city = any.get();
            }
        }
        return city;
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

    private String readFile(String path){
        File file = new File(path);
        String text = null;
        String extension = FilenameUtils.getExtension(file.getName());
        if(extension.equals("pdf")){
            text = readPdf(path);
        } else if(extension.equals("doc") || extension.equals("docx")){
            text = readDocxFile(path);
        }
        return text;
    }

    private String readPdf(String path){
        String text = null;
        try {
            PDDocument document  = PDDocument.load(new File(path));
            if (!document.isEncrypted()) {
                PDFTextStripper stripper = new PDFTextStripper();
                text = stripper.getText(document);
            }
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;

    }


    public String readDocxFile(String path) {
        String text = null;
        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());

            XWPFDocument document = new XWPFDocument(fis);
            List<XWPFParagraph> paragraphs = document.getParagraphs();

            StringBuilder stringBuilder = new StringBuilder();
            for (XWPFParagraph para : paragraphs) {
                stringBuilder.append(para.getText());
            }
            text = stringBuilder.toString();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }
}
