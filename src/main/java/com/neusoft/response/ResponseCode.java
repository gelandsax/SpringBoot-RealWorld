package com.neusoft.response;

public enum ResponseCode {
    SUCCESS(200, "操作成功"),
    NEED_LOGIN(401, "需要登录后操作"),
    NO_OPERATOR_AUTH(403, "无权限操作"),
    PAGE_NOT_FOUND(404,"页面不存在"),
    SYSTEM_ERROR(500, "出现错误");

    // 自定义状态码
    private final int code;

    // 自定义描述
    private final String msg;

    ResponseCode(int code, String errorMessage) {
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
