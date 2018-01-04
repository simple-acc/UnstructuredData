package com.lmt.data.unstructured.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/3 16:59
 */
public class ResultData {

    private static final int CORRECT_CODE = 3;
    private static final int ERROR_CODE = 0;
    private Map returnMap;

    private ResultData(String message, int code){
        super();
        returnMap = new HashMap<String, Object>();
        returnMap.put("code", code);
        returnMap.put("msg", message);
        returnMap.put("data", null);
    }

    public static ResultData newOK(String message){
        return new ResultData(message, CORRECT_CODE);
    }

    public static ResultData newOk(String message, Object data){
        ResultData resultData = new ResultData(message, CORRECT_CODE);
        resultData.returnMap.replace("data", data);
        return resultData;
    }


    public static ResultData newError(String message){
        return new ResultData(message, ERROR_CODE);
    }

    public static ResultData newError(String message, Object data){
        ResultData resultData = new ResultData(message, ERROR_CODE);
        resultData.returnMap.replace("data", data);
        return resultData;
    }

    public Map toMap() {
        return returnMap;
    }
}
