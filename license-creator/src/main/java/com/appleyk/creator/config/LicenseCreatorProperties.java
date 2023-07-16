package com.appleyk.creator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

/**
 * <p>License生成配置类</p>
 *
 * @author appleyk
 * @version v1.0.0
 * @blob https://blog.csdn.net/appleyk
 * @date created on 11:35 下午 2020/8/21
 */
@ConfigurationProperties(prefix = "springboot.license.generate")
public class LicenseCreatorProperties {

    /**证书生成临时存放路径*/
    private String tempPath;

    public LicenseCreatorProperties() {
    }

    public String getTempPath() {
        return tempPath;
    }

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
        File file = new File(tempPath);
        if(!file.exists()){
            file.mkdirs();
        }
    }
}
