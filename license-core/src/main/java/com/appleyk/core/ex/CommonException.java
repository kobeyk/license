package com.appleyk.core.ex;


import com.appleyk.core.result.ResponseResult;
import com.appleyk.core.result.ResultCode;
import org.springframework.http.HttpStatus;

/**
 * <p>自定义通用异常类</p>
 *
 * @author appleyk
 * @version v1.0.0
 * @blob https://blog.csdn.net/appleyk
 * @date created on 上午 10:59 2019-4-27
 */
public class CommonException extends Exception{

    /** 结果状态码*/
    private ResultCode resultCode;

    /** 结果消息*/
    private String message;

    public CommonException(String message){
        super(message);
        this.resultCode = ResultCode.INTERNAL;
        this.message = message;
    }

    public CommonException(ResultCode resultCode, String message){
        super(message);
        this.resultCode = resultCode;
        this.message = message;
    }

    public class ResultError{

        /** 错误码*/
        private Integer code;

        /** 错误名称*/
        private String name;

        public ResultError(Integer code , String name){
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public ResponseResult buildResult(){
        Integer code = this.resultCode.getCode();
        String name = this.resultCode.getName();
        return new ResponseResult(HttpStatus.BAD_REQUEST.value(),this.message , new ResultError(code, name));
    }

}
