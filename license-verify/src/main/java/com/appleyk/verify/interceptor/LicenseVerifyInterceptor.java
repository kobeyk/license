package com.appleyk.verify.interceptor;

import com.appleyk.core.ex.CommonException;
import com.appleyk.core.model.LicenseExtraParam;
import com.appleyk.core.model.LicenseResult;
import com.appleyk.core.model.LicenseVerifyManager;
import com.appleyk.core.result.ResultCode;
import com.appleyk.core.utils.CommonUtils;
import com.appleyk.verify.annotion.VLicense;
import com.appleyk.verify.config.LicenseVerifyProperties;
import com.appleyk.core.listener.ACustomVerifyListener;
import de.schlichtherle.license.LicenseContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

/**
 * <p>License验证拦截器</p>
 *
 * @author appleyk
 * @version v1.0.0
 * @blob https://blog.csdn.net/appleyk
 * @date created on 00:32 上午 2020/8/22
 */
public class LicenseVerifyInterceptor implements HandlerInterceptor {

    @Autowired
    private LicenseVerifyProperties properties;

    public LicenseVerifyInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            VLicense annotation = method.getAnnotation(VLicense.class);
            if (CommonUtils.isNotEmpty(annotation)) {
                LicenseVerifyManager licenseVerifyManager = new LicenseVerifyManager();
                /** 1、校验证书是否有效 */
                LicenseResult verifyResult = licenseVerifyManager.verify(properties.getVerifyParam());
                if(!verifyResult.getResult()){
                    throw new CommonException(verifyResult.getMessage());
                }
                LicenseContent content = verifyResult.getContent();
                LicenseExtraParam licenseCheck = (LicenseExtraParam) content.getExtra();
                if (verifyResult.getResult()) {
                    /** 增加业务系统监听，是否自定义验证 */
                    List<ACustomVerifyListener> customListenerList = ACustomVerifyListener.getCustomListenerList();
                    boolean compare = true;
                    for (ACustomVerifyListener listener : customListenerList) {
                        boolean verify = listener.verify(licenseCheck);
                        compare = compare && verify;
                    }
                    return compare;
                }
                throw new CommonException(ResultCode.INTERNAL,verifyResult.getException().getMessage());
            }
        }
        return true;
    }
}
