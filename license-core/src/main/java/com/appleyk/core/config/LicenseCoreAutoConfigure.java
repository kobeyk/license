package com.appleyk.core.config;

import com.appleyk.core.helper.LoggerHelper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>license-core模块中的Bean实现自动装配 -- 配置类</p>
 *
 * @author appleyk
 * @version v1.0.0
 * @blob https://blog.csdn.net/appleyk
 * @date created on 10:24 下午 2020/8/21
 */
@Configuration
@ComponentScan(basePackages = {"com.appleyk.core"})
public class LicenseCoreAutoConfigure {
    public LicenseCoreAutoConfigure(){
        LoggerHelper.info("============ license-core-spring-boot-starter initialization！ ===========");
    }
}
