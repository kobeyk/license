package com.appleyk.core.model;

import com.appleyk.core.ex.CommonException;
import com.appleyk.core.helper.LoggerHelper;
import com.appleyk.core.helper.ParamInitHelper;
import com.appleyk.core.result.ResultCode;
import com.appleyk.core.utils.DateUtils;
import de.schlichtherle.license.*;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

/**
 * <p>License校验类</p>
 *
 * @author appleyk
 * @version v1.0.0
 * @blob https://blog.csdn.net/appleyk
 * @date created on  10:42 下午 2020/8/21
 */
public class LicenseVerifyManager {

    /**
     * <p>安装License证书</p>
     * @param param License校验类需要的参数
     */
    public synchronized LicenseResult install(LicenseVerifyParam param){
        try{
            /** 1、初始化License证书参数 */
            LicenseParam licenseParam = ParamInitHelper.initLicenseParam(param);
            /** 2、创建License证书管理器对象 */
//          LicenseManager licenseManager =new LicenseManager(licenseParam);
            //走自定义的Lic管理
            LicenseCustomManager licenseManager = new LicenseCustomManager(licenseParam);
            /** 3、获取要安装的证书文件 */
            File licenseFile = ResourceUtils.getFile(param.getLicensePath());
            /** 4、如果之前安装过证书，先卸载之前的证书 == 给null */
            licenseManager.uninstall();
            /** 5、开始安装 */
            LicenseContent content = licenseManager.install(licenseFile);
            String message = MessageFormat.format("证书安装成功，证书有效期：{0} - {1}",
                    DateUtils.date2Str(content.getNotBefore()),DateUtils.date2Str(content.getNotAfter()));
            LoggerHelper.info(message);
            return new LicenseResult(message,content);
        }catch (LicenseContentException contentExc){
            String message = contentExc.getMessage();
            LoggerHelper.error(message);
            return new LicenseResult(false,message,contentExc);
        } catch (Exception e){
            LoggerHelper.error(e.getMessage(),e);
            return new LicenseResult(false,e.getMessage(),e);
        }
    }

    /**
     * <p>校验License证书</p>
     * @param param License校验类需要的参数
     */
    public LicenseResult verify(LicenseVerifyParam param){

        /** 1、初始化License证书参数 */
        LicenseParam licenseParam = ParamInitHelper.initLicenseParam(param);
        /** 2、创建License证书管理器对象 */
        LicenseManager licenseManager = new LicenseCustomManager(licenseParam);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        /** 3、开始校验证书 */
        try {
            /**这里要判断下lic文件是不是被用户恶意删除了*/
            String licensePath = param.getLicensePath();
            if (licensePath == null || licensePath == ""){
                String msg = "license.lic路径未指定，验证不通过！";
                return new LicenseResult(false,msg, new CommonException(ResultCode.INTERNAL,msg));
            }
            /**下面两个检测如果文件不存在会抛异常，然后会被捕获到*/
            if (licensePath.contains("classpath:")){
                /**检测下当前应用的classes路径下有没有lic文件*/
                ResourceUtils.getFile(licensePath);
            }else{
                /**直接构建file对象检测lic文件是否存在*/
                new File(licensePath);
            }
            LicenseContent licenseContent = licenseManager.verify();
            String message = MessageFormat.format("证书校验通过，证书有效期：{0} - {1}",
                    format.format(licenseContent.getNotBefore()),format.format(licenseContent.getNotAfter()));
            LoggerHelper.info(message);
            return new LicenseResult(message,licenseContent);
        }catch (NoLicenseInstalledException ex){
            String message = "证书未安装！";
            LoggerHelper.error(message,ex);
            return new LicenseResult(false,message,ex);
        }catch (LicenseContentException cex){
            LoggerHelper.error(cex.getMessage(),cex);
            return new LicenseResult(false,cex.getMessage(),cex);
        }catch (FileNotFoundException fnfe){
            String msg =String.format("license.lic文件（%s）不存在，验证失败！",param.getLicensePath());
            return new LicenseResult(false,msg, new CommonException(ResultCode.INTERNAL,msg));
        }catch (Exception e){
            String message = "证书校验失败！";
            LoggerHelper.error(message,e);
            return new LicenseResult(false,message,e);
        }
    }


}
