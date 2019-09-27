package cn.bluethink.license.core;

import de.schlichtherle.license.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.security.auth.x500.X500Principal;
import java.io.File;
import java.text.MessageFormat;
import java.util.prefs.Preferences;

/**
 * <p>生成许可</p>
 *
 * @author appleyk
 * @version V.1.0.1
 * @blob https://blog.csdn.net/appleyk
 * @date created on 下午 1:31 2019-9-26
 */
public class LicenseCreator {

    private static Logger logger = LogManager.getLogger(LicenseCreator.class);

    // 证书的发行者和主体字段信息
    private final static X500Principal DEFAULT_HOLDER_AND_ISSUER =
            new X500Principal("CN=YuKun, OU=SuZhou, O=LanDi, L=GX, ST=GX, C=CN");
    private LicenseCreatorParam param;

    public LicenseCreator(LicenseCreatorParam param) {
        this.param = param;
    }

    /**
     * <p>生成License证书</p>
     */
    public boolean generateLicense(){

        try {
            LicenseManager licenseManager = new CustomLicenseManager(initLicenseParam());
            LicenseContent licenseContent = initLicenseContent();
            licenseManager.store(licenseContent,new File(param.getLicensePath()));
            return true;
        }catch (Exception e){
            logger.error(MessageFormat.format("证书生成失败：{0}",param),e);
            return false;
        }

    }

    /**
     * <p>初始化证书生成参数</p>
     */
    private LicenseParam initLicenseParam(){

        Preferences preferences = Preferences.userNodeForPackage(LicenseCreator.class);
        //设置对证书内容加密的秘钥
        CipherParam cipherParam = new DefaultCipherParam(param.getStorePass());
        KeyStoreParam privateStoreParam = new DefaultKeyStoreParam(LicenseCreator.class
                ,param.getPrivateKeysStorePath()
                ,param.getPrivateAlias()
                ,param.getStorePass()
                ,param.getKeyPass());

        return new DefaultLicenseParam(param.getSubject()
                ,preferences
                ,privateStoreParam
                ,cipherParam);
    }

    /**
     * <p>初始化证书内容信息对象</p>
     */
    private LicenseContent initLicenseContent(){

        LicenseContent licenseContent = new LicenseContent();
        licenseContent.setHolder(DEFAULT_HOLDER_AND_ISSUER);
        licenseContent.setIssuer(DEFAULT_HOLDER_AND_ISSUER);

        // 设置证书名称
        licenseContent.setSubject(param.getSubject());
        // 设置证书有效期
        licenseContent.setIssued(param.getIssuedTime());
        // 设置证书生效日期
        licenseContent.setNotBefore(param.getIssuedTime());
        // 设置证书失效日期
        licenseContent.setNotAfter(param.getExpiryTime());
        // 设置证书用户类型
        licenseContent.setConsumerType(param.getConsumerType());
        // 设置证书用户数量
        licenseContent.setConsumerAmount(param.getConsumerAmount());
        // 设置证书描述信息
        licenseContent.setInfo(param.getDescription());

        //设置证书扩展信息（对象 -- 额外的ip、mac、cpu等信息）
        licenseContent.setExtra(param.getLicenseCheck());
        return licenseContent;
    }
}
