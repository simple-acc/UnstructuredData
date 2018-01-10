package com.lmt.data.unstructured.util;

/**
 * @author MT-Lin
 * @date 2018/1/7 8:30
 */
public class UdConstant {

    /**
     * 待审核资源的状态编码
     */
    public static final String AUDIT_STATUS_WAIT = "005003";

    /**
     * 新增资源操作的审核编码
     */
    public static final String OPERATION_ADD_RESOURCE = "004001";

    /**
     * 文件流一次读取的长度
     */
    public static final int FILE_READ_BUFFER_LENGTH = 3096;

    /**
     * 返回结果编码标识
     */
    public static final String RESULT_CODE = "code";

    /**
     * 返回结果信息标识
     */
    public static final String RESULT_MSG = "msg";

    /**
     * 返回结果数据标识
     */
    public static final String RESULT_DATA = "data";

    /**
     * 操作姐夫哦成功编码
     */
    public static final int RESULT_CORRECT_CODE = 3;

    /**
     * 操作结果出错（失败）编码
     */
    public static final int RESULT_ERROR_CODE = 0;

    /**
     * 文件上传保存路径
     */
    public static final String RESOURCE_TEMP = "unstructured.data.resource.upload";

    /**
     * 树形控件的子节点的ID标识
     */
    public static final String TREE_PROPS_ID = "id";

    /**
     * 树形控件的子节点的CODE标识
     */
    public static final String TREE_PROPS_CODE = "code";

    /**
     * 树形控件的子节点属性名称
     */
    public static final String TREE_PROPS_CHILDREN = "children";

    /**
     * 树形控件显示节点名的属性名称
     */
    public static final String TREE_PROPS_LABEL = "label";

    /**
     * 存储分页查找数据总数的变量名
     */
    public static final String TOTAL_ELEMENTS = "totalElements";

    /**
     * 存储分页查找结果数据的变量名
     */
    public static final String CONTENT = "content";

    /**
     * 密码错误次数（5次）
     */
    public static final int PASSWORD_ERROR_TIME = 6;

    /**
     * 默认的密码错误次数的值
     */
    public static final int DEFAULT_PASSWORD_ERROR_TIME = 1;

    /**
     * 账户冻结状态码
     */
    public static final String COUNT_FREEZE_CODE = "007002";

    /**
     * 账户正常状态码
     */
    public static final String COUNT_UNFREEZE_CODE = "007001";

    /**
     * 默认密码
     */
    public static final String DEFAULT_PASSWORD = "aaaaaa";

    /**
     * 用户类型（管理员）编码
     */
    public static final String USER_TYPE_ADMIN_CODE = "008001";

    /**
     * 用户类型（普通用户）编码
     */
    public static final String USER_TYPE_USER_CODE = "008002";

    /**
     * 用户登录凭证
     */
    public static final String USER_LOGIN_EVIDENCE = "tokenId";

    public static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public static final String KEY_MD5 = "MD5";

    public static final String CHARSET_ISO88591 = "ISO-8859-1";

}
