package com.semantic.vcards.creator.helpers;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.InputStream;

/**
 * @author Tomasz Lelek
 * @since 2014-05-13
 */
public class FileHelper {
    /**
     * read content from file with given fileName
     * @param fileName
     * @param classZ
     * @return
     */
    public static String getContentFromFile(String fileName, Class classZ) {
        try {
            File jsonFile;
            System.out.println("--> " +classZ.getResource(fileName));
            InputStream inputStream= classZ.getResourceAsStream(fileName);
            //System.out.println(jsonFile.toString());
            return IOUtils.toString(inputStream, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
