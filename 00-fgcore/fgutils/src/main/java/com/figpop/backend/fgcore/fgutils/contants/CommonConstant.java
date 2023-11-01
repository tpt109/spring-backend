package com.figpop.backend.fgcore.fgutils.contants;

public interface CommonConstant {
	Integer STATUS_NORMAL = 0;
	Integer STATUS_DISABLE = -1;

	Integer DEL_FLAG_1 = 1;

	Integer DEL_FLAG_0 = 0;

	int LOG_TYPE_1 = 1;

	/** {@code 500 Server Error} (HTTP/1.0 - RFC 1945) */
    Integer SC_INTERNAL_SERVER_ERROR_500 = 500;
    /** {@code 200 OK} (HTTP/1.0 - RFC 1945) */
    Integer SC_OK_200 = 200;

    String PREFIX_USER_TOKEN  = "prefix_user_token:";

    int  TOKEN_EXPIRE_TIME  = 3600;
    String  LOGIN_QRCODE_PRE  = "QRCODELOGIN:";
    String  LOGIN_QRCODE  = "LQ:";
    String  LOGIN_QRCODE_TOKEN  = "LQT:";
    Integer MENU_TYPE_0  = 0;
    Integer MENU_TYPE_1  = 1;
    Integer MENU_TYPE_2  = 2;
    String MSG_TYPE_UESR  = "USER";
    String MSG_TYPE_ALL  = "ALL";
    String NO_SEND  = "0";
    String HAS_SEND  = "1";
    String HAS_CANCLE  = "2";
    Integer HAS_READ_FLAG  = 1;
    Integer NO_READ_FLAG  = 0;
    String PRIORITY_L  = "L";
    String PRIORITY_M  = "M";
    String PRIORITY_H  = "H";
    String SMS_TPL_TYPE_0  = "0";
    String SMS_TPL_TYPE_1  = "1";
    String SMS_TPL_TYPE_2  = "2";
    String STATUS_0 = "0";
    String STATUS_1 = "1";

    String X_ACCESS_TOKEN = "X-Access-Token";
    String X_SIGN = "X-Sign";
    String X_TIMESTAMP = "X-TIMESTAMP";
    String TENANT_ID = "X-Tenant-Id";

    String HTTP_POST = "POST";

    String HTTP_PUT = "PUT";

    String HTTP_PATCH = "PATCH";

    String UNKNOWN = "unknown";

    String STR_HTTP = "http";

    String STRING_NULL = "null";

    String VERSION="X-Version";

    String DYNAMIC_TABLE_NAME="DYNAMIC_TABLE_NAME";
    String HTTP_PROTOCOL = "http://";

    String HTTPS_PROTOCOL = "https://";

    String LOGIN_TOKEN = "{LOGIN_TOKEN}";

    String MSG_HREF_URL = "url";

    String DATA_LOG_TYPE_COMMENT = "comment";

    String DATA_LOG_TYPE_JSON = "json";


    /**
     * 排序类型：升序
     */
    String ORDER_TYPE_ASC = "ASC";
    /**
     * 排序类型：降序
     */
    String ORDER_TYPE_DESC = "DESC";

 
}
