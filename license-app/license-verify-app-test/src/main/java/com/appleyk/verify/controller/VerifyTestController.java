package com.appleyk.verify.controller;

import com.appleyk.verify.annotion.VLicense;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>证书认证接口测试</p>
 *
 * @author appleyk
 * @version v1.0.0
 * @blob https://blog.csdn.net/appleyk
 * @date created on 00:02 上午 2020/8/22
 */
@CrossOrigin
@RestController
@RequestMapping("license")
public class VerifyTestController {

    @VLicense
    @GetMapping("/hello")
    public String sayHello(){
        return "hello license !";
    }

}
