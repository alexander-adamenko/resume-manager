package com.study.resumemanager.exception;

public class UnsupportedFileException extends Exception{
    public UnsupportedFileException(String extension) {
        super("Unsupported file extension: pdf, doc, docx expected but " + extension + " got");
    }
}
