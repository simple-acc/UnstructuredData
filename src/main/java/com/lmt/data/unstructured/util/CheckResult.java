package com.lmt.data.unstructured.util;

import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/15 8:35
 */
public class CheckResult {

    public static boolean isCorrect(Map result){
        return Integer.valueOf(result.get(UdConstant.RESULT_CODE).toString())
                == UdConstant.RESULT_CORRECT_CODE;
    }
}
