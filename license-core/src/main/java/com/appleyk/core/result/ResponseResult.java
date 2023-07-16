package com.appleyk.core.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * <p>请求结果（封装）</p>
 *
 * @author appleyk
 * @version v1.0.0
 * @blob https://blog.csdn.net/appleyk
 * @date created on  10:32 下午 2020/8/21
 */
public class ResponseResult {

    /** 响应结果状态码*/
    private Integer status;

    /** 响应结果消息*/
    private String message;

    /** 响应结果对应的（包含）的数据，空的话不反序列话*/
    @JsonInclude(value = Include.NON_NULL)
    private Object data;

    /** 响应时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timeStamp = new Date();

    public ResponseResult() {
        this.status = null;
        this.message = null;
        this.data = null;
    }

    public ResponseResult(Integer status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }

    public ResponseResult(Integer status ,String message,Object data){
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseResult(ResultCode resultCode, String message){
        this.status = resultCode.getCode();
        this.message = message;
    }

    /**
     * 默认成功返回的实例
     * @param data 对象
     */
    private ResponseResult(Object data){
        this.status = ResultCode.OK.getCode();
        this.message = ResultCode.OK.getName();
        this.data = data;
    }

    private ResponseResult(String message,Object data){
        this.status = ResultCode.OK.getCode();
        this.message = message;
        this.data = data;
    }

    private ResponseResult(ResultCode code){
        this.status = code.getCode();
        this.message = code.getName();
        this.data = null;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public static ResponseResult ok(Object data){
        return new ResponseResult(data);
    }

    public static ResponseResult ok(String message,Object data){
        return new ResponseResult(data);
    }

    public static ResponseResult fail(ResultCode code){
        return new ResponseResult(code);
    }

    public static ResponseResult fail(String message){
        return new ResponseResult(HttpStatus.BAD_REQUEST.value(),"失败",message);
    }
}
