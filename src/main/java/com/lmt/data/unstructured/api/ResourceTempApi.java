package com.lmt.data.unstructured.api;

import com.lmt.data.unstructured.entity.ResourceTemp;
import com.lmt.data.unstructured.entity.TagTemp;
import com.lmt.data.unstructured.entity.search.ResourceTempSearch;
import com.lmt.data.unstructured.service.ResourceTempService;
import com.lmt.data.unstructured.service.TagTempService;
import com.lmt.data.unstructured.util.Md5Util;
import com.lmt.data.unstructured.util.RedisCache;
import com.lmt.data.unstructured.util.ResultData;
import com.lmt.data.unstructured.util.UdConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/9 12:48
 */
@RestController
@RequestMapping("/ResourceTempApi")
public class ResourceTempApi {

    private Logger logger = LoggerFactory.getLogger(ResourceTempApi.class);

    @Autowired
    private Environment environment;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ResourceTempService resourceTempService;

    @Autowired
    private TagTempService tagTempService;

    @RequestMapping("search")
    public Map search(@RequestBody ResourceTempSearch resourceTempSearch){
        return this.resourceTempService.search(resourceTempSearch);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @RequestMapping("/upload")
    public Map upload(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request){
        if (multipartFile.isEmpty()){
            return ResultData.newError("上传失败，文件是空的。");
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
        // 保存待审核资源数据
        ResourceTemp resourceTemp = new ResourceTemp();
        String tokenId = request.getSession().getAttribute(UdConstant.USER_LOGIN_EVIDENCE).toString();
        resourceTemp.setAuthor(redisCache.getUserName(tokenId));
        resourceTemp.setMd5(Md5Util.getFileMD5(file));
        resourceTemp.setDesignation(request.getParameter("designation"));
        resourceTemp.setResourceType(request.getParameter("resourceType"));
        resourceTemp.setResourceSize(Double.parseDouble(request.getParameter("resourceSize")));
        String classifyId = request.getParameter("classifyId");
        if (!StringUtils.isEmpty(classifyId)){
            resourceTemp.setClassifyId(classifyId);
        }
        String description = request.getParameter("description");
        if (!StringUtils.isEmpty(description)){
            resourceTemp.setDescription(description);
        }
        Map result = this.resourceTempService.save(resourceTemp);
        // 保存待审核资源标签
        String tags = request.getParameter("tags");
        if (!StringUtils.isEmpty(tags)){
            TagTemp tagTemp = new TagTemp();
            tagTemp.setResourceTempId(resourceTemp.getId());
            tagTemp.setTags(tags);
            Map saveTagsResult = this.tagTempService.save(tagTemp);
            if (Integer.valueOf(saveTagsResult.get(UdConstant.RESULT_CODE).toString())
                    != UdConstant.RESULT_CORRECT_CODE){
                logger.error("待审核资源 [ id = "+resourceTemp.getId()
                        +" ] 标签保存失败：" + saveTagsResult.get(UdConstant.RESULT_MSG));
            }
        }
        if (Integer.valueOf(result.get(UdConstant.RESULT_CODE).toString()) != UdConstant.RESULT_CORRECT_CODE) {
            return result;
        }
        String newFileName = resourceTemp.getId() + fileName.substring(fileName.lastIndexOf("."));
        File renameFile = new File(filePath + newFileName);
        if (!file.renameTo(renameFile)){
            return ResultData.newError("文件重命名失败，请联系管理员改BUG");
        }
        return result;
    }
}
