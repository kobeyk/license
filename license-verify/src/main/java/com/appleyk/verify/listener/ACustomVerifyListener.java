package com.appleyk.verify.listener;

import com.appleyk.core.ex.CommonException;
import com.appleyk.core.model.LicenseExtraParam;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>增加业务系统中自定义证书验证监听</p>
 *
 * @author appleyk
 * @version V.0.2.1
 * @blob https://blog.csdn.net/appleyk
 * @date created on 00:02 上午 2020/8/22
 */
public abstract class ACustomVerifyListener {

    private static final List<ACustomVerifyListener> CUSTOM_VERIFY_LISTENER_LIST = new ArrayList<>(16);

    public static List<ACustomVerifyListener> getCustomListenerList(){
        return CUSTOM_VERIFY_LISTENER_LIST;
    }

    public ACustomVerifyListener() {
        addCustomListener(this);
    }

    public synchronized static void addCustomListener(ACustomVerifyListener verifyListener){
        CUSTOM_VERIFY_LISTENER_LIST.add(verifyListener);
    }

    /**
     * 业务系统自定义证书认证监听接口
     * @param licenseExtra 自定义验证参数
     * @return boolean 是否成功
     */
    public abstract boolean verify(LicenseExtraParam licenseExtra) throws CommonException;

}
