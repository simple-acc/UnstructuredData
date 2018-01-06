package com.lmt.data.unstructured.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author MT-Lin
 * @date 2018/1/4 10:54
 */
public class BaseToString {

    @Override
    public String toString() {
        Class<? extends BaseToString> clazz = getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        String className = clazz.getName();
        StringBuffer sb = new StringBuffer();
        sb.append(className.substring(className.lastIndexOf(".") + 1));
        sb.append("{");
        try {
            for (Field field : declaredFields) {
                String name = field.getName();
                sb.append(name);
                sb.append("=");
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                Method method = clazz.getMethod("get" + name);
                // 调用getter方法获取属性值
                Object value = method.invoke(this);
                if (null == value) {
                    sb.append("null");
                } else {
                    sb.append(value);
                }
                sb.append(", ");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        if (declaredFields.length > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("}");
        return sb.toString();
    }
}
