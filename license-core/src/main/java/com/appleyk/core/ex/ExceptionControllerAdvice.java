package com.appleyk.core.ex;

import com.appleyk.core.result.ResponseResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * <p>全局接口异常拦截器</p>
 *
 * @author appleyk
 * @version v1.0.0
 * @blob https://blog.csdn.net/appleyk
 * @date created on 00:41 下午 2020/8/31
 */
@CrossOrigin
@ControllerAdvice
@RestControllerAdvice
public class ExceptionControllerAdvice {
    public ExceptionControllerAdvice() {
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    @ModelAttribute
    public void addAttributes(Model model) {
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity errorHandler(Exception ex) {
        if (ex instanceof CommonException) {
            CommonException commonException = (CommonException) ex;
            return ResponseEntity.ok(commonException.buildResult());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseResult.fail(ex.getMessage()));
    }
}

