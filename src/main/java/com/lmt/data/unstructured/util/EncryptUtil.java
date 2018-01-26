package com.lmt.data.unstructured.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @apiNote 加密
 * @author MT-Lin
 */
public class EncryptUtil {

    /**
     * @apiNote MD5加密
     * @param arg 需要加密的String
     * @return 加密结果
     */
    public static String encrypt(String arg){
        MessageDigest messageDigest = null;
        String result = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            result = bytesToHex(messageDigest.digest(arg.getBytes("utf-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @apiNote 二进制转十六进制
     * @param bytes 需要转换的byt数组
     * @return 转换完成之后的String
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer hexStr = new StringBuffer();
        int num;
        for (int i = 0; i < bytes.length; i++) {
            num = bytes[i];
            if(num < 0) {
                num += 256;
            }
            if(num < 16){
                hexStr.append("0");
            }
            hexStr.append(Integer.toHexString(num));
        }
        return hexStr.toString().toUpperCase();
    }
}  