package com.lmt.data.unstructured.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/3 16:59
 */
public class ResultData {

    private static final int CORRECT_CODE = UdConstant.RESULT_CORRECT_CODE;
    private static final int ERROR_CODE = UdConstant.RESULT_ERROR_CODE;
    private Map<String, Object> returnMap;

    private ResultData(String message, int code){
        super();
        returnMap = new HashMap<>();
        returnMap.put(UdConstant.RESULT_CODE, code);
        returnMap.put("msg", message);
        returnMap.put("data", null);
    }

    public static Map newOK(String message){
        return new ResultData(message, CORRECT_CODE).toMap();
    }

    public static Map newOk(String message, Object data){
        ResultData resultData = new ResultData(message, CORRECT_CODE);
        resultData.returnMap.replace("data", data);
        return resultData.toMap();
    }


    public static Map newError(String message){
        return new ResultData(message, ERROR_CODE).toMap();
    }

    private Map toMap() {
        return returnMap;
    }
}
