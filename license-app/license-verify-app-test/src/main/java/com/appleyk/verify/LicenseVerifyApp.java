package com.appleyk.verify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
/**
 * 开启定时器，{@link com.appleyk.verify.listener.LicenseVerifyInstallListener}
 * 主要功能：
 *  1、安装lic，安装的过程就是验证lic文件
 *  2、检测lic文件是不是被用户 "投机取巧"删除了，如果删除报错
 *  3、动态检测lic，如果lic过期，后面用户又更新了lic则重新安装lic，目的就是为了热更新lic
 */
@EnableScheduling
public class LicenseVerifyApp extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(LicenseVerifyApp.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LicenseVerifyApp.class);
    }
}
