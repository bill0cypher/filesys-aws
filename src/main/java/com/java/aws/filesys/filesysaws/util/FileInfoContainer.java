package com.java.aws.filesys.filesysaws.util;

import java.io.InputStream;

public class FileInfoContainer {

    private String fileName;
    private String filePath;
    private InputStream content;

    public FileInfoContainer(String fileName, String filePath, InputStream content) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.content = content;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public InputStream getContent() {
        return content;
    }
}
