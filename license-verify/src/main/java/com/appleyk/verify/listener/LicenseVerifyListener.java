package com.appleyk.verify.listener;

import com.appleyk.core.helper.LoggerHelper;
import com.appleyk.core.model.LicenseResult;
import com.appleyk.core.model.LicenseVerifyManager;
import com.appleyk.core.utils.CommonUtils;
import com.appleyk.verify.config.LicenseVerifyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * <p>项目启动时安装证书&定时检测lic变化，自动更替lic</p>
 *
 * @author appleyk
 * @version V.0.2.1
 * @blob https://blog.csdn.net/appleyk
 * @date created on 00:02 上午 2020/8/22
 */
@Component
public class LicenseVerifyListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private LicenseVerifyProperties properties;

    /**文件唯一身份标识 == 相当于人类的指纹一样*/
    private static String md5 = "";
    private static boolean isLoad = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(CommonUtils.isNotEmpty(properties.getLicensePath())){
            install();
            try{
                String readMd5 = getMd5(properties.getLicensePath());
                isLoad = true;
                if(LicenseVerifyListener.md5 == null || "".equals(LicenseVerifyListener.md5)){
                    LicenseVerifyListener.md5 =readMd5;
                }
            }catch (Exception e){

            }
        }
    }

    /**5秒检测一次，不能太快也不能太慢*/
    @Scheduled(cron = "0/5 * * * * ?")
    protected void timer() throws Exception {
        if(!isLoad){
            return;
        }
        String readMd5 = getMd5(properties.getLicensePath());
        // 不相等，说明lic变化了
        if(!readMd5.equals(LicenseVerifyListener.md5)){
            install();
            LicenseVerifyListener.md5 = readMd5;
        }
    }

    private void install() {
        LoggerHelper.info("++++++++ 开始安装证书 ++++++++");
        LicenseVerifyManager licenseVerifyManager = new LicenseVerifyManager();
        /** 走定义校验证书并安装 */
        LicenseResult result = licenseVerifyManager.install(properties.getVerifyParam());
        if(result.getResult()){
            LoggerHelper.info("++++++++ 证书安装成功 ++++++++");
        }else{
            LoggerHelper.info("++++++++ 证书安装失败 ++++++++");
        }
    }

    /**
     * <p>获取文件的md5</p>
     */
    public String getMd5(String filePath) throws Exception {
        File file;
        String md5 = "";
        try {
            file = ResourceUtils.getFile(filePath);
            if (file.exists()) {
                FileInputStream is = new FileInputStream(file);
                byte[] data = new byte[is.available()];
                is.read(data);
                md5 = DigestUtils.md5DigestAsHex(data);
                is.close();
            }
        } catch (FileNotFoundException e) {

        }
        return md5;
    }

}
