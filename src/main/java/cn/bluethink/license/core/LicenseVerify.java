package cn.bluethink.license.core;

import de.schlichtherle.license.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.prefs.Preferences;

/**
 * <p>License校验类</p>
 *
 * @author appleyk
 * @version V.1.0.1
 * @blob https://blog.csdn.net/appleyk
 * @date created on 下午 3:40 2019-9-26
 */
public class LicenseVerify {

    private static Logger logger = LogManager.getLogger(LicenseVerify.class);

    /**
     * <p>安装License证书</p>
     */
    public synchronized LicenseContent install(LicenseVerifyParam param){

        LicenseContent result = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //1. 安装证书
        try{

            LicenseManager licenseManager =new LicenseManager(initLicenseParam(param));
            // 先卸载证书 == 给null
            licenseManager.uninstall();
            // 安装
            result = licenseManager.install(new File(param.getLicensePath()));
            logger.info(MessageFormat.format("证书安装成功，证书有效期：{0} - {1}",format.format(result.getNotBefore()),format.format(result.getNotAfter())));
        }catch (Exception e){
            logger.error("证书安装失败！",e);
        }

        return result;

    }

    /**
     * <p>校验License证书</p>
     */
    public boolean verify(LicenseVerifyParam param){

        LicenseManager licenseManager = new CustomLicenseManager(initLicenseParam(param));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //2. 校验证书
        try {
            LicenseContent licenseContent = licenseManager.verify();
            logger.info(MessageFormat.format("证书校验通过，证书有效期：{0} - {1}",format.format(licenseContent.getNotBefore()),format.format(licenseContent.getNotAfter())));
            return true;
        }catch (Exception e){
            logger.error("证书校验失败！",e);
            return false;
        }
    }

    /**
     * <p>初始化证书生成参数</p>
     * @param param License校验类需要的参数
     */
    private LicenseParam initLicenseParam(LicenseVerifyParam param){

        Preferences preferences = Preferences.userNodeForPackage(LicenseVerify.class);
        CipherParam cipherParam = new DefaultCipherParam(param.getStorePass());
        KeyStoreParam publicStoreParam = new DefaultKeyStoreParam(LicenseVerify.class
                ,param.getPublicKeysStorePath()
                ,param.getPublicAlias()
                ,param.getStorePass()
                ,null);

        return new DefaultLicenseParam(param.getSubject()
                ,preferences
                ,publicStoreParam
                ,cipherParam);
    }

}
