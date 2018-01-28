package com.lmt.data.unstructured.util;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

/**
 * @apiNote 获取MD5值工具 from:http://hw1287789687.iteye.com/blog/1924190
 * @author MT-Lin
 * @date 2018/1/9 21:29
 */
public class Md5Util {

    public static String getFileMD5(File file) {
            if (!file.exists() || !file.isFile()) {
                return null;
            }
            MessageDigest digest;
            FileInputStream in;
            byte[] buffer = new byte[UdConstant.FILE_READ_BUFFER_SIZE];
            int length;
            try {
                digest = MessageDigest.getInstance("MD5");
                in = new FileInputStream(file);
                while ((length = in.read(buffer, 0, UdConstant.FILE_READ_BUFFER_SIZE)) != -1) {
                    digest.update(buffer, 0, length);
                }
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return toHexString(digest.digest());
        }

    /***
     * convert byte array to hex(16) bit string
     *
     * @return hex(16) bit string
     */
    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(UdConstant.HEX_CHAR[(b[i] & 0xf0) >>> 4]);
            sb.append(UdConstant.HEX_CHAR[b[i] & 0x0f]);
        }
        return sb.toString();
    }

        /***
         * Get MD5 of one file！test ok!
         *
         * @param filePath 文件路径
         * @return String
         */
        public static String getFileMD5(String filePath) {
            File file = new File(filePath);
            return getFileMD5(file);
        }

        /**
         * MD5 encrypt,test ok
         *
         * @param data
         * @return byte[]
         * @throws Exception
         */
        public static byte[] encryptMD5(byte[] data) throws Exception {
            MessageDigest md5 = MessageDigest.getInstance(UdConstant.KEY_MD5);
            md5.update(data);
            return md5.digest();
        }

        public static byte[] encryptMD5(String data) throws Exception {
            return encryptMD5(data.getBytes(UdConstant.CHARSET_ISO88591));
        }
}
