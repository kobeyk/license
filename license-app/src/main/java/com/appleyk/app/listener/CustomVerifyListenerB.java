package com.appleyk.app.listener;

import com.appleyk.core.ex.CommonException;
import com.appleyk.core.model.LicenseExtraParam;
import com.appleyk.verify.listener.ACustomVerifyListener;
import org.springframework.stereotype.Component;

/**
 * <p>Lic自定义验证监听器B</p>
 *
 * @author appleyk
 * @version V.0.2.1
 * @blob https://blog.csdn.net/appleyk
 * @date created on 10:24 下午 2020/8/21
 */
@Component
public class CustomVerifyListenerB extends ACustomVerifyListener {
    @Override
    public boolean verify(LicenseExtraParam licenseExtra) throws CommonException {
        System.out.println("======= 自定义证书验证监听器B 实现verify方法  =======");
        return true;
    }
}
