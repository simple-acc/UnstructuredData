package com.lmt.data.unstructured.api;

import com.lmt.data.unstructured.entity.Resource;
import com.lmt.data.unstructured.service.ResourceService;
import com.lmt.data.unstructured.util.FileUtil;
import com.lmt.data.unstructured.util.UdConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author MT-Lin
 * @date 2018/1/12 9:52
 */
@RestController
@RequestMapping("/FileApi")
public class FileApi {

    private Logger logger = LoggerFactory.getLogger(FileApi.class);

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private ResourceService resourceService;

    @RequestMapping("/download")
    public void download(@RequestParam String resourceId, HttpServletResponse response){
        Resource resource = this.resourceService.findOneById(resourceId);
        if (null == resource){
            logger.error("该资源[ID={}]信息不存在", resourceId);
            return;
        }
        String resourceFileName = resource.getResourceFileName();
        String resourceName = resource.getDesignation();
        File downloadFile = new File(fileUtil.getFullFilePath(resourceFileName));
        if (!downloadFile.exists()){
            logger.error("文件 [" + resourceFileName + "] 不存在");
            return;
        }
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            // 设置头部
            response.setContentType("application/octet-stream");
            response.setHeader("content-disposition", "attachment;filename="
                    + URLEncoder.encode(resourceName, "UTF-8"));
            os = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(downloadFile));
            int bytesReader;
            byte[] buffer = new byte[UdConstant.FILE_READ_BUFFER_SIZE];
            while ((bytesReader = bis.read(buffer)) != -1){
                os.write(buffer, 0, bytesReader);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("文件下载出现异常");
        } finally {
            if (null != os){
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error("文件输出流关闭出现异常");
                    e.printStackTrace();
                }
            }
            if (null != bis){
                try {
                    bis.close();
                } catch (IOException e) {
                    logger.error("输入流关闭出现异常");
                    e.printStackTrace();
                }
            }
        }
    }
}
