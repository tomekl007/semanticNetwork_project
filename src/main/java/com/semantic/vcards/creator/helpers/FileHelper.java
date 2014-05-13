package com.semantic.vcards.creator.helpers;

import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * @author Tomasz Lelek
 * @since 2014-05-13
 */
public class FileHelper {

    public static String getContentFromFile(String fileName, Class classZ) {
        try {
            File jsonFile;
            jsonFile = new File(classZ.getResource(fileName).toURI());
            return FileUtils.readFileToString(jsonFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
