package com.appleyk.core.model;

import java.io.Serializable;
import java.util.List;

/**
 * <p>自定义需要校验的License参数</p>
 *
 * @author appleyk
 * @version v1.0.0
 * @blob https://blog.csdn.net/appleyk
 * @date created on  10:42 下午 2020/8/21
 */
public class LicenseExtraParam implements Serializable {

    private static final long serialVersionUID = 8600137500316662317L;

    /** 是否认证ip*/
    private boolean isIpCheck ;

    /** 可被允许的IP地址*/
    private List<String> ipAddress;

    /**是否认证mac*/
    private boolean isMacCheck ;

    /** 可被允许的mac地址*/
    private List<String> macAddress;

    /**是否认证cpu序列号*/
    private boolean isCpuCheck ;

    /** 可被允许的CPU序列号*/
    private String cpuSerial;

    /** 是否认证主板号*/
    private boolean isBoardCheck ;

    /**可被允许的主板序列号*/
    private String mainBoardSerial;

    /** 是否限制注册人数*/
    private boolean isRegisterCheck;

    /** 限制系统中可注册的人数*/
    private Long registerAmount;

    /**其他可自行扩展字段*/

    public LicenseExtraParam(){

    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<String> getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(List<String> ipAddress) {
        this.ipAddress = ipAddress;
    }

    public List<String> getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(List<String> macAddress) {
        this.macAddress = macAddress;
    }

    public String getCpuSerial() {
        return cpuSerial;
    }

    public void setCpuSerial(String cpuSerial) {
        this.cpuSerial = cpuSerial;
    }

    public String getMainBoardSerial() {
        return mainBoardSerial;
    }

    public void setMainBoardSerial(String mainBoardSerial) {
        this.mainBoardSerial = mainBoardSerial;
    }

    public boolean isIpCheck() {
        return isIpCheck;
    }

    public void setIpCheck(boolean ipCheck) {
        isIpCheck = ipCheck;
    }

    public boolean isMacCheck() {
        return isMacCheck;
    }

    public void setMacCheck(boolean macCheck) {
        isMacCheck = macCheck;
    }

    public boolean isCpuCheck() {
        return isCpuCheck;
    }

    public void setCpuCheck(boolean cpuCheck) {
        isCpuCheck = cpuCheck;
    }

    public boolean isBoardCheck() {
        return isBoardCheck;
    }

    public void setBoardCheck(boolean boardCheck) {
        isBoardCheck = boardCheck;
    }

    public Long getRegisterAmount() {
        return registerAmount;
    }

    public void setRegisterAmount(Long registerAmount) {
        this.registerAmount = registerAmount;
    }

    public boolean isRegisterCheck() {
        return isRegisterCheck;
    }

    public void setRegisterCheck(boolean registerCheck) {
        isRegisterCheck = registerCheck;
    }

    @Override
    public String toString() {
        return "LicenseExtraParam{" +
                "ipAddress=" + ipAddress +
                ", macAddress=" + macAddress +
                ", cpuSerial='" + cpuSerial + '\'' +
                ", mainBoardSerial='" + mainBoardSerial + '\'' +
                ", registerAmount='" + registerAmount + '\'' +
                '}';
    }
}
