package com.lmt.data.unstructured.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author MT-Lin
 * @date 2018/1/10 17:49
 */
@Component
public class FileUtil {

    private Logger logger = LoggerFactory.getLogger(FileUtil.class);

    @Autowired
    private Environment environment;

    public boolean renameFile(MultipartFile multipartFile, String resourceId){
        String oldFileName = multipartFile.getOriginalFilename();
        String newFileName =  resourceId + oldFileName.substring(oldFileName.lastIndexOf("."));
        String filePath = environment.getProperty(UdConstant.RESOURCE_TEMP);
        File renameFile = new File(filePath + newFileName);
        File file = new File(filePath + oldFileName);
        return file.renameTo(renameFile);
    }

    public String saveFile(MultipartFile multipartFile){
        if (multipartFile.isEmpty()){
            return null;
        }
        String fileName = multipartFile.getOriginalFilename();
        String filePath = environment.getProperty(UdConstant.RESOURCE_TEMP);
        File folder = new File(filePath);
        if (!folder.exists()){
            folder.mkdirs();
        }
        InputStream inputStream = null;
        OutputStream outputStream = null;
        File file = new File(filePath + fileName);
        try {
            inputStream = multipartFile.getInputStream();
            outputStream = new FileOutputStream(file);
            int bytesReader;
            byte[] buffer = new byte[UdConstant.FILE_READ_BUFFER_LENGTH];
            while ((bytesReader = inputStream.read(buffer, 0, UdConstant.FILE_READ_BUFFER_LENGTH)) != -1){
                outputStream.write(buffer, 0, bytesReader);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("文件输入流关闭异常");
                    e.printStackTrace();
                }
            }
            if (null != outputStream){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.error("文件输出流关闭异常");
                    e.printStackTrace();
                }
            }
        }
        return Md5Util.getFileMD5(file);
    }
}
