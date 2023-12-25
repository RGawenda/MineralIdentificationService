package com.mineralidentificationservice.utils;



import org.apache.tomcat.util.codec.binary.Base64;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileUtilsConv {

    public static String saveImage(String imageBase64, String userFolderPath) {
        byte[] decodedBytes = Base64.decodeBase64(imageBase64);

        File directory = new File(userFolderPath);
        if(!directory.exists()){
            directory.mkdir();
        }
        String fileName = userFolderPath +File.separator+ generateUniqueFileName();

        File file = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(decodedBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file.getAbsolutePath();
    }

    public static String loadImage(String path) {
        return convertImageToBase64(new File(path));
    }

    public static boolean deleteImage(String path) {
        File myObj = new File(path);
        return myObj.delete();
    }

    public static List<String> saveAllImage(List<String> imageListBase64, String user) {
        List<String> imagePaths = new ArrayList<>();
        for (String imageToDelete : imageListBase64) {
            imagePaths.add(saveImage(imageToDelete, user));
        }
        return imagePaths;
    }

    public static List<String> deleteAllImage(List<String> listToDelete, String user) {
        List<String> deletedImages = new ArrayList<>();
        for (String imageToDelete : listToDelete) {
            deleteImage(user+imageToDelete);
            deletedImages.add(user+imageToDelete);
        }
        return deletedImages;
    }

    public static String convertImageToBase64(File inputFile) {
        String imageBase64;
        try (FileInputStream fileInputStreamReader = new FileInputStream(inputFile)) {
            byte[] bytes = new byte[(int) inputFile.length()];
            fileInputStreamReader.read(bytes);
            imageBase64 = new String(Base64.encodeBase64(bytes, true), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageBase64;
    }

    public static List<String> loadAllImageToBase64(List<String> inputFiles) {
        List<String> allImageBase64 = new ArrayList<>();
        for (String imageToDelete : inputFiles) {
            allImageBase64.add(loadImage(imageToDelete));
        }
        return allImageBase64;
    }

    private static String generateUniqueFileName() {
        return "image_" + System.currentTimeMillis() + ".jpg";
    }

}
