package com.lmt.data.unstructured.util;

/**
 * @author MT-Lin
 * @date 2018/1/7 8:30
 */
public class UdConstant {

    /**
     * 文件扩展名
     */
    public static final String FILE_TYPE_TXT = "txt";
    public static final String FILE_TYPE_XLS = "xls";
    public static final String FILE_TYPE_XLSX = "xlsx";
    public static final String FILE_TYPE_DOC = "doc";
    public static final String FILE_TYPE_DOCX = "docx";
    public static final String FILE_TYPE_PPT = "ppt";
    public static final String FILE_TYPE_PPTX = "pptx";
    public static final String FILE_TYPE_PDF = "pdf";

    /**
     * 文件扩展名分隔符
     */
    public static final String FILE_EXTENSION_SPLIT = ".";

    /**
     * ES索引
     */
    public static final String ES_INDEX = "unstructured_data";

    /**
     * ES节点分隔符
     */
    public static final String CLUSTER_NODES_SPLIT = ",";

    /**
     * ES地址和端口号分隔符
     */
    public static final String ADDRESS_PORT_SPLIT = ":";

    /**
     * EsResponse 成功状态码
     */
    public static final int ES_RESPONSE_SUCCESS = 200;

    /**
     * 收藏操作编码
     */
    public static final int COLLECTION_OPERATION_ADD = 1;

    /**
     * 取消收藏操作编码
     */
    public static final int COLLECTION_OPERATION_CANCEL = -1;

    /**
     * 返回数据为空的请求链接
     */
    public static final String DOWNLOAD_FILE_URL = "/FileApi/download";
    public static final String UPDATE_DOWNLOAD_NUM = "/ResourceApi/updateDownloadNum";

    /**
     * 用户登录失败编码
     */
    public static final String LOGIN_FAIL = "009002";

    /**
     * 用户登陆成功编码
     */
    public static final String LOGIN_SUCCESS = "009001";

    /**
     * 待审核的状态编码
     */
    public static final String AUDIT_STATUS_WAIT = "005003";

    /**
     * 审核通过的状态编码
     */
    public static final String AUDIT_STATUS_PASS = "005001";

    /**
     * 新增资源操作的审核编码
     */
    public static final String AUDIT_OPERATION_ADD = "004001";

    /**
     * 删除资源操作的审核编码
     */
    public static final String AUDIT_OPERATION_DELETE = "004002";

    /**
     * 文件流一次读取的长度
     */
    public static final int FILE_READ_BUFFER_SIZE = 3096;

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
     * 操作结果成功编码
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
     * 控件节点的value标识
     */
    public static final String PROPS_VALUE = "value";

    /**
     * 控件节点的ID标识
     */
    public static final String PROPS_ID = "id";

    /**
     * 控件节点的CODE标识
     */
    public static final String PROPS_CODE = "code";

    /**
     * 控件节点属性名称
     */
    public static final String PROPS_CHILDREN = "children";

    /**
     * 控件显示节点名的属性名称
     */
    public static final String PROPS_LABEL = "label";

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
