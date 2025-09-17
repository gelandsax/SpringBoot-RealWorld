package com.neusoft.response;

import java.util.Objects;

public class ApiResponse <T> {
    /**
     * 响应编码
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String msg;
    /**
     * 响应数据
     */
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ApiResponse(){

    }

    public ApiResponse(Integer code, String msg){
        this.code=code;
        this.msg=msg;
    }

    public ApiResponse(Integer code, String msg, T data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }

    public static ApiResponse success(){
        return ApiResponse.success(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg());
    }
    public static <T> ApiResponse success(int code, String msg){
        ApiResponse<T> ApiResponse = new ApiResponse<>();
        ApiResponse.setCode(code);
        ApiResponse.setMsg(msg);
        return ApiResponse;
    }

    public static <T> ApiResponse success(T data){
        ApiResponse<T> ApiResponse = new ApiResponse<>(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg());
        if (Objects.nonNull(data)){
            ApiResponse.setData(data);
        }
        return ApiResponse;
    }

    public static <T> ApiResponse error(int code, String msg){
        ApiResponse<T> ApiResponse = new ApiResponse<>(code,msg);
        return ApiResponse;
    }

    public static <T> ApiResponse error(ResponseCode ResponseCode){
        ApiResponse<T> ApiResponse = new ApiResponse<>(ResponseCode.getCode(), ResponseCode.getMsg());
        return ApiResponse;
    }
}
