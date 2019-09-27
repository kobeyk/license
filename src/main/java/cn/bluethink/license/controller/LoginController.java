package cn.bluethink.license.controller;

import cn.bluethink.license.interceptor.GxLicense;
import cn.bluethink.license.core.User;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>用户登录</p>
 *
 * @author appleyk
 * @version V.1.0.1
 * @blob https://blog.csdn.net/appleyk
 * @date created on 下午 4:11 2019-9-26
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class LoginController {

    @GxLicense
    @PostMapping("/login")
    public Map<String, Object> checkLicense(@RequestBody User user){

        Map<String,Object> result = new HashMap<>();
        if("appleyk".equals(user.getUserName()) && "123456a".equals(user.getPassWord())){
            result.put("code",200);
        }else{
            result.put("code",500);
            result.put("msg","用户名或密码不对！");
        }
        return result ;

    }

}
