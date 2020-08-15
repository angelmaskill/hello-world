package net.myl.business.base;




import java.util.HashMap;

/**
 * api 接口消息
 */
public class ApiResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    public static final String CODE_TAG = "code";

    /**
     * 返回内容
     */
    public static final String MSG_TAG = "msg";

    /**
     * 数据对象
     */
    public static final String DATA_TAG = "data";

    public enum ReturnCode {

        SUCCESS("10000", "成功"),
        PARAMS_EMPTY("10001", "参数不能为空"),
        PARAMS_ERROR("10002", "参数不合法"),
        PARAMS_NOFULL("10003", "必填参数缺失"),
        SIGN_ERROR("10004", "签名错误"),
        DES_ERROR("10005", "加密错误"),
        SYSTEM_ERROR("10006", "系统异常"),
        BUSINESS_ERROR("10007", "业务异常"),
        DB_ERROR("10007", "DB异常"),
        RESULT_EMPTY("10008", "返回结果为空"),
        QRCODE_INVALID("10009", "二维码已失效"),
        INVOKE_THIRD_FAIL("10010", "请求三方系统失败"),
        /**
         * 用户信息
         */
        NOT_LOGIN("10011", "用户未登陆"),
        PERMISSION_DENIED("10012", "用户权限不足"),
        /** 应用开通后需要默认调用开启，开启失败时的返回码 */
        DEFAULT_OPEN_APP_FAIL("10013", "应用默认开启失败");


        private final String code;
        private final String info;

        ReturnCode(String code, String info) {
            this.code = code;
            this.info = info;
        }

        public String getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public ApiResult() {
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param type 状态类型
     * @param msg  返回内容
     */
    public ApiResult(ReturnCode type, String msg) {
        super.put(CODE_TAG, type.code);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param returnCode 状态类型
     * @param data       数据对象
     */
    public ApiResult(ReturnCode returnCode, Object data) {
        super.put(CODE_TAG, returnCode.code);
        super.put(MSG_TAG, returnCode.info);
        if (StringUtils.isNotNull(data)) {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 返回成功消息
     *
     * @param data 数据对象
     * @return 成功消息
     */
    public static ApiResult success(Object data) {
        return new ApiResult(ReturnCode.SUCCESS.code, ReturnCode.SUCCESS.info, data);
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态类型
     * @param msg  返回内容
     * @param data 数据对象
     */
    public ApiResult(String code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (StringUtils.isNotNull(data)) {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 返回错误消息
     *
     * @param returnCode 返回内容
     * @param data       数据对象
     * @return 警告消息
     */
    public static ApiResult error(ReturnCode returnCode, Object data) {
        return new ApiResult(returnCode.code, returnCode.info, data);
    }


    public static ApiResult errorMsg(ReturnCode returnCode, String msg) {
        return new ApiResult(returnCode.code, msg,null);
    }

    public boolean isSuccess() {
        boolean isSuccess = this.get(ApiResult.CODE_TAG).equals(ReturnCode.SUCCESS.code);
        return isSuccess;
    }

    public Object data() {
        return this.get(ApiResult.DATA_TAG);
    }

    public String msg() {
        return (String)this.get(ApiResult.MSG_TAG);
    }
}
