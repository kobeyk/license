package cn.bluethink.license.interceptor;

import cn.bluethink.license.core.LicenseVerify;
import cn.bluethink.license.listener.LicenseCheckListener;
import cn.bluethink.license.utils.JsonUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>拦截器</p>
 *
 * @author appleyk
 * @version V.1.0.1
 * @blob https://blog.csdn.net/appleyk
 * @date created on 下午 3:54 2019-9-26
 */
public class LicenseCheckInterceptor implements HandlerInterceptor {


    @Autowired
    private LicenseCheckListener listener;

    private static Logger logger = LogManager.getLogger(LicenseCheckInterceptor.class);

    public LicenseCheckInterceptor(){
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            GxLicense annotation = method.getAnnotation(GxLicense.class);
            if (annotation != null) {

                LicenseVerify licenseVerify = new LicenseVerify();
                //校验证书是否有效
                boolean verifyResult = licenseVerify.verify(listener.getVerifyParam());
                if (verifyResult) {
                    return true;
                } else {
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("text/html;charset=utf-8");
                    Map<String, String> result = new HashMap<>(1);
                    result.put("result", "您的证书无效，请核查服务器是否取得授权或重新申请证书！");
                    response.getWriter().write(result == null ? "" : JsonUtils.objectToJson(result));
                    return false;
                }

            }
        }
        return true;
    }
}
