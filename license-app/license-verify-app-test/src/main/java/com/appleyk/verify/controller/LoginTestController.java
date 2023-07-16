package com.appleyk.verify.controller;

import com.appleyk.core.result.ResponseResult;
import com.appleyk.verify.annotion.VLicense;
import org.springframework.web.bind.annotation.*;

/**
 * <p>登录测试</p>
 *
 * @author appleyk
 * @version v1.0.0
 * @blob https://blog.csdn.net/appleyk
 * @date created on 00:02 上午 2020/8/22
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class LoginTestController {

    @VLicense
    @PostMapping("/login")
    public ResponseResult checkLicense(@RequestBody User user){
        if("admin".equals(user.getUserName()) && "admin".equals(user.getPassword())){
           return ResponseResult.ok("登陆成功！");
        }else{
            return ResponseResult.fail("用户名或密码不对！");
        }
    }
}

class User {

    private String userName ;
    private String password ;

    public User(){

    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

