package cn.bluethink.license.core;

import cn.bluethink.license.service.AGxServerInfos;
import cn.bluethink.license.service.LinuxServerInfos;
import cn.bluethink.license.service.WindowsServerInfos;
import de.schlichtherle.license.*;
import de.schlichtherle.xml.GenericCertificate;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * <p>自定义LicenseManager，用于增加额外的服务器硬件信息校验</p>
 *
 * @author appleyk
 * @version V.1.0.1
 * @blob https://blog.csdn.net/appleyk
 * @date created on 下午 1:30 2019-9-26
 */
public class CustomLicenseManager extends LicenseManager {

    private static Logger logger = LogManager.getLogger(CustomLicenseManager.class);

    //XML编码
    private static final String XML_CHARSET = "UTF-8";

    //默认BUFSIZE
    private static final int DEFAULT_BUFSIZE = 8 * 1024;

    public CustomLicenseManager() {

    }

    public CustomLicenseManager(LicenseParam param) {
        super(param);
    }

    /**
     * <p>重写LicenseManager的create方法</p>
     */
    @Override
    protected synchronized byte[] create(
            LicenseContent content,
            LicenseNotary notary)
            throws Exception {
        initialize(content);
        // 假如自己额外的许可内容信息认证 == 主要友情提示
        this.validateCreate(content);
        final GenericCertificate certificate = notary.sign(content);
        return getPrivacyGuard().cert2key(certificate);
    }

    /**
     * <p>重写install方法</p>
     */
    @Override
    protected synchronized LicenseContent install(
            final byte[] key,
            final LicenseNotary notary)
            throws Exception {

        final GenericCertificate certificate = getPrivacyGuard().key2cert(key);
        notary.verify(certificate);
        final LicenseContent content = (LicenseContent)this.load(certificate.getEncoded());
        // 增加额外的自己的license校验方法，校验ip、mac、cpu序列号等
        this.validate(content);
        setLicenseKey(key);
        setCertificate(certificate);
        return content;

    }

    /**
     * <p>重写verify方法</p>
     */
    @Override
    protected synchronized LicenseContent verify(final LicenseNotary notary)
            throws Exception {

        // Load license key from preferences,
        final byte[] key = getLicenseKey();
        if (null == key){
            throw new NoLicenseInstalledException(getLicenseParam().getSubject());
        }

        GenericCertificate certificate = getPrivacyGuard().key2cert(key);
        notary.verify(certificate);
        final LicenseContent content = (LicenseContent)this.load(certificate.getEncoded());
        // 增加额外的自己的license校验方法，校验ip、mac、cpu序列号等
        this.validate(content);
        setCertificate(certificate);
        return content;

    }

    /**
     * <p>校验生成证书的参数信息</p>
     */
    protected synchronized void validateCreate(final LicenseContent content)
            throws LicenseContentException {

        // 当前时间
        final Date now = new Date();
        // 生效时间
        final Date notBefore = content.getNotBefore();
        // 失效时间
        final Date notAfter = content.getNotAfter();

        if (null != notAfter && now.after(notAfter)){
            logger.error("证书失效时间不能早于当前时间");
            throw new LicenseContentException("证书失效时间不能早于当前时间");
        }

        if (null != notBefore && null != notAfter && notAfter.before(notBefore)){
            logger.error("证书生效时间不能晚于证书失效时间");
            throw new LicenseContentException("证书生效时间不能晚于证书失效时间");
        }

        final String consumerType = content.getConsumerType();
        if (null == consumerType){
            logger.error("用户类型不能为空");
            throw new LicenseContentException("用户类型不能为空");
        }

    }


    /**
     * <p>重写validate方法，增加ip地址、mac地址、cpu序列号等其他信息的校验</p>
     */
    @Override
    protected synchronized void validate(final LicenseContent content)
            throws LicenseContentException {

        //1、 首先调用父类的validate方法
        super.validate(content);

        //2、 然后校验自定义的License参数
        //License中可被允许的参数信息
        LicenseCheck expectedCheck = (LicenseCheck) content.getExtra();

        //当前服务器真实的参数信息
        LicenseCheck serverCheckModel = getServerInfos();

        if(expectedCheck != null && serverCheckModel != null){

            //校验IP地址
            if(expectedCheck.isIpCheck() && !checkIpAddress(expectedCheck.getIpAddress(),serverCheckModel.getIpAddress())){
                logger.error("证书无效，当前服务器的IP没在授权范围内");
                throw new LicenseContentException("证书无效，当前服务器的IP没在授权范围内");
            }

            //校验Mac地址
            if(expectedCheck.isIpCheck() && !checkIpAddress(expectedCheck.getMacAddress(),serverCheckModel.getMacAddress())){
                logger.error("证书无效，当前服务器的Mac地址没在授权范围内");
                throw new LicenseContentException("证书无效，当前服务器的Mac地址没在授权范围内");
            }

            //校验主板序列号
            if(expectedCheck.isIpCheck() && !checkSerial(expectedCheck.getMainBoardSerial(),serverCheckModel.getMainBoardSerial())){
                logger.error("证书无效，当前服务器的主板序列号没在授权范围内");
                throw new LicenseContentException("证书无效，当前服务器的主板序列号没在授权范围内");
            }

            //校验CPU序列号
            if(expectedCheck.isIpCheck() && !checkSerial(expectedCheck.getCpuSerial(),serverCheckModel.getCpuSerial())){
                logger.error("证书无效，当前服务器的CPU序列号没在授权范围内");
                throw new LicenseContentException("证书无效，当前服务器的CPU序列号没在授权范围内");
            }

        }else{
            logger.error("不能获取服务器硬件信息");
            throw new LicenseContentException("不能获取服务器硬件信息");
        }
    }


    /**
     * <p>重写XMLDecoder解析XML</p>
     */
    private Object load(String encoded){

        BufferedInputStream inputStream = null;
        XMLDecoder decoder = null;
        try {
            inputStream = new BufferedInputStream(new ByteArrayInputStream(encoded.getBytes(XML_CHARSET)));
            decoder = new XMLDecoder(new BufferedInputStream(inputStream, DEFAULT_BUFSIZE),null,null);
            return decoder.readObject();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            try {
                if(decoder != null){
                    decoder.close();
                }
                if(inputStream != null){
                    inputStream.close();
                }
            } catch (Exception e) {
                logger.error("XMLDecoder解析XML失败",e);
            }
        }
        return null;

    }

    /**
     * <p>获取当前服务器需要额外校验的License参数</p>
     */
    private LicenseCheck getServerInfos(){

        //操作系统类型
        String osName = System.getProperty("os.name").toLowerCase();
        AGxServerInfos abstractServerInfos = null;

        //根据不同操作系统类型选择不同的数据获取方法
        if (osName.startsWith("windows")) {
            abstractServerInfos = new WindowsServerInfos();
        } else if (osName.startsWith("linux")) {
            abstractServerInfos = new LinuxServerInfos();
        }else{//其他服务器类型
            abstractServerInfos = new LinuxServerInfos();
        }
        return abstractServerInfos.getServerInfos();
    }

    /**
     * <p>
     *     校验当前服务器的IP/Mac地址是否在可被允许的IP范围内<br/>
     *     如果存在IP在可被允许的IP/Mac地址范围内，则返回true
     * </p>
     *
     */
    private boolean checkIpAddress(List<String> expectedList, List<String> serverList){

        if(expectedList != null && expectedList.size() > 0){
            if(serverList != null && serverList.size() > 0){
                for(String expected : expectedList){
                    if(serverList.contains(expected.trim())){
                        return true;
                    }
                }
            }
            return false;
        }else {
            return true;
        }
    }

    /**
     * <p>校验当前服务器硬件（主板、CPU等）序列号是否在可允许范围内</p>
     */
    private boolean checkSerial(String expectedSerial,String serverSerial){
        if(StringUtils.isNotBlank(expectedSerial)){
            if(StringUtils.isNotBlank(serverSerial)){
                return expectedSerial.equals(serverSerial);
            }
            return false;
        }else{
            return true;
        }
    }
}
