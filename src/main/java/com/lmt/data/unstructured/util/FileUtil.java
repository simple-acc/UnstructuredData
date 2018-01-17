package com.lmt.data.unstructured.util;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MT-Lin
 * @date 2018/1/10 17:49
 */
@Component
public class FileUtil {

    private Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private List<String> resolutionFileTypes;

    @Autowired
    private Environment environment;

    {
        resolutionFileTypes = new ArrayList<>();
        resolutionFileTypes.add("pdf");
        resolutionFileTypes.add("doc");
        resolutionFileTypes.add("docx");
        resolutionFileTypes.add("xls");
        resolutionFileTypes.add("xlsx");
        resolutionFileTypes.add("txt");
    }

    public String getFileContent(String resourceFileName) {
        if (!this.existResourceFile(resourceFileName)){
            logger.error("资源文件 [resourceFileName={}] 不存在，获取资源内容失败", resourceFileName);
            return null;
        }
        String extention = resourceFileName.substring(resourceFileName.lastIndexOf(UdConstant.FILE_EXTENSION_SPLIT) + 1);
        if (!this.resolutionFileTypes.contains(extention)){
            return null;
        }
        if (extention.endsWith(UdConstant.FILE_TYPE_PDF)){
            return this.getPdfFileContent(resourceFileName);
        }
        return null;
    }

    private String getPdfFileContent(String resourceFileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            PdfReader pdfReader = new PdfReader(this.getFullFilePath(resourceFileName));
            PdfReaderContentParser parser = new PdfReaderContentParser(pdfReader);
            TextExtractionStrategy strategy;
            for (int i = 1; i < pdfReader.getNumberOfPages(); i++){
                strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
                stringBuilder.append(strategy.getResultantText());
            }
            pdfReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public String getFullFilePath(String fileName){
        String filePath = environment.getProperty(UdConstant.RESOURCE_TEMP);
        return filePath + fileName;
    }

    public boolean existResourceFile(String resourceFileName){
        String filePath = environment.getProperty(UdConstant.RESOURCE_TEMP);
        File resourceFile = new File(filePath + resourceFileName);
        return resourceFile.exists();
    }

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
            //noinspection ResultOfMethodCallIgnored
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
            while ((bytesReader = inputStream.read(buffer, 0, buffer.length)) != -1){
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
