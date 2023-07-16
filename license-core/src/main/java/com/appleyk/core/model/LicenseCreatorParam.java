package com.appleyk.core.model;

import com.appleyk.core.utils.JsonUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>License创建（生成）需要的参数</p>
 *
 * @author appleyk
 * @version v1.0.0
 * @blob https://blog.csdn.net/appleyk
 * @date created on  10:42 下午 2020/8/21
 */
public class LicenseCreatorParam implements Serializable {

    private static final long serialVersionUID = -7793154252684580872L;

    /**证书主题*/
    private String subject;

    /**私钥别名*/
    private String privateAlias;

    /**私钥密码（需要妥善保管，不能让使用者知道*/
    private String keyPass;

    /**私钥库存储路径*/
    private String privateKeysStorePath;

    /**访问私钥库的密码*/
    private String storePass;

    /**证书生成路径*/
    private String licensePath;

    /** 证书生效时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date issuedTime = new Date();

    /** 证书失效时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expiryTime;

    /**用户类型*/
    private String consumerType = "user";

    /**用户数量*/
    private Integer consumerAmount = 1;

    /**描述信息*/
    private String description = "";

    /**额外的服务器硬件校验信息（或者其他的信息都可以放）*/
    private LicenseExtraParam licenseCheck;

    /**证书下载地址 == 一旦证书create成功，这个值就会填充上*/
    private String licUrl;

    public LicenseCreatorParam(){

    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPrivateAlias() {
        return privateAlias;
    }

    public void setPrivateAlias(String privateAlias) {
        this.privateAlias = privateAlias;
    }

    public String getKeyPass() {
        return keyPass;
    }

    public void setKeyPass(String keyPass) {
        this.keyPass = keyPass;
    }

    public String getStorePass() {
        return storePass;
    }

    public void setStorePass(String storePass) {
        this.storePass = storePass;
    }

    public String getLicensePath() {
        return licensePath;
    }

    public void setLicensePath(String licensePath) {
        this.licensePath = licensePath;
    }

    public String getPrivateKeysStorePath() {
        return privateKeysStorePath;
    }

    public void setPrivateKeysStorePath(String privateKeysStorePath) {
        this.privateKeysStorePath = privateKeysStorePath;
    }

    public Date getIssuedTime() {
        return issuedTime;
    }

    public void setIssuedTime(Date issuedTime) {
        this.issuedTime = issuedTime;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getConsumerType() {
        return consumerType;
    }

    public void setConsumerType(String consumerType) {
        this.consumerType = consumerType;
    }

    public Integer getConsumerAmount() {
        return consumerAmount;
    }

    public void setConsumerAmount(Integer consumerAmount) {
        this.consumerAmount = consumerAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LicenseExtraParam getLicenseCheck() {
        return licenseCheck;
    }

    public void setLicenseCheck(LicenseExtraParam licenseCheck) {
        this.licenseCheck = licenseCheck;
    }

    public String getLicUrl() {
        return licUrl;
    }

    public void setLicUrl(String licUrl) {
        this.licUrl = licUrl;
    }

    @Override
    public String toString() {
        return "LicenseCreatorParam{" +
                "subject='" + subject + '\'' +
                ", privateAlias='" + privateAlias + '\'' +
                ", keyPass='" + keyPass + '\'' +
                ", privateKeysStorePath='" + privateKeysStorePath + '\'' +
                ", storePass='" + storePass + '\'' +
                ", licensePath='" + licensePath + '\'' +
                ", issuedTime=" + issuedTime +
                ", expiryTime=" + expiryTime +
                ", consumerType='" + consumerType + '\'' +
                ", consumerAmount=" + consumerAmount +
                ", description='" + description + '\'' +
                ", licenseCheck=" + licenseCheck +
                ", licUrl='" + licUrl + '\'' +
                '}';
    }

    public static void main(String[] args) {
        LicenseCreatorParam param = new LicenseCreatorParam();
        LicenseExtraParam check = new LicenseExtraParam();
        param.setLicenseCheck(check);
        System.out.println(JsonUtils.objectToJson(param));
    }
}
