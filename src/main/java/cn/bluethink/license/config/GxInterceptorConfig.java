package cn.bluethink.license.config;

import cn.bluethink.license.interceptor.LicenseCheckInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>拦截器配置类</p>
 *
 * @author appleyk
 * @version V.1.0.1
 * @blob https://blog.csdn.net/appleyk
 * @date created on 下午 4:57 2019-9-26
 */
@Configuration
public class GxInterceptorConfig implements WebMvcConfigurer {

    @Bean
    public LicenseCheckInterceptor getLicenseCheckInterceptor() {
        return new LicenseCheckInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.getLicenseCheckInterceptor()).addPathPatterns(new String[]{"/**"});
    }
}
