package com.test.modulebrary.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by meijunqiang on 2017/11/21  15:53.
 * 描述：FileHelper 文件助手
 */

public class FileHelper {
    /**
     * 从sd卡中读取文件
     *
     * @param fileName
     * @return
     */
    public static String readTextFromSDcard(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return null;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            int availableLength = fileInputStream.available();
            byte[] buffer = new byte[availableLength];
            fileInputStream.read(buffer);
            fileInputStream.close();
            return new String(buffer, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据key保存该key文件到磁盘
     * @param fileName
     * @param text
     * @return
     */
    public static boolean saveText2Sdcard(String fileName, String text) {
        File file = new File(fileName);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(text);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            file.delete();
            return false;
        }
        return true;
    }
}
