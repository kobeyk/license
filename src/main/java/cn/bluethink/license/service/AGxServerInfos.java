package cn.bluethink.license.service;

import cn.bluethink.license.core.LicenseCheck;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * <p>服务器硬件信息抽象类 -- 模板方法，将通用的方法抽离到父类中</p>
 *
 * @author appleyk
 * @version V.1.0.1
 * @blob https://blog.csdn.net/appleyk
 * @date created on 下午 1:24 2019-9-26
 */
public abstract class AGxServerInfos {

    private static Logger logger = LogManager.getLogger(AGxServerInfos.class);

    /**
     * <p>组装需要额外校验的License参数</p>
     */
    public LicenseCheck getServerInfos(){

        LicenseCheck result = new LicenseCheck();
        try {
            result.setIpAddress(this.getIpAddress());
            result.setMacAddress(this.getMacAddress());
            result.setCpuSerial(this.getCPUSerial());
            result.setMainBoardSerial(this.getMainBoardSerial());
        }catch (Exception e){
            logger.error("获取服务器硬件信息失败",e);
        }

        return result;
    }

    /**
     * <p>获取IP地址</p>
     */
    protected abstract List<String> getIpAddress() throws Exception;

    /**
     * <p>获取Mac地址</p>
     */
    protected abstract List<String> getMacAddress() throws Exception;

    /**
     * <p>获取CPU序列号</p>
     */
    protected abstract String getCPUSerial() throws Exception;

    /**
     * <p>获取主板序列号</p>
     */
    protected abstract String getMainBoardSerial() throws Exception;

    /**
     * <p>获取当前服务器所有符合条件的InetAddress</p>
     */
    protected List<InetAddress> getLocalAllInetAddress() throws Exception {

        List<InetAddress> result = new ArrayList<>(4);

        // 遍历所有的网络接口
        for (Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces(); networkInterfaces.hasMoreElements(); ) {
            NetworkInterface ni = (NetworkInterface) networkInterfaces.nextElement();
            // 在所有的接口下再遍历IP
            for (Enumeration addresses = ni.getInetAddresses(); addresses.hasMoreElements(); ) {
                InetAddress address = (InetAddress) addresses.nextElement();
                //排除LoopbackAddress、SiteLocalAddress、LinkLocalAddress、MulticastAddress类型的IP地址
                if(!address.isLoopbackAddress() /*&& !inetAddr.isSiteLocalAddress()*/
                        && !address.isLinkLocalAddress() && !address.isMulticastAddress()){
                    result.add(address);
                }
            }
        }
        return result;
    }

    /**
     * <p>获取某个网络地址对应的Mac地址</p>
     */
    protected String getMacByInetAddress(InetAddress inetAddr){

        try {
            byte[] mac = NetworkInterface.getByInetAddress(inetAddr).getHardwareAddress();
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<mac.length;i++){
                if(i != 0) {
                    sb.append("-");
                }

                //将十六进制byte转化为字符串
                String temp = Integer.toHexString(mac[i] & 0xff);
                if(temp.length() == 1){
                    sb.append("0" + temp);
                }else{
                    sb.append(temp);
                }
            }
            return sb.toString().toUpperCase();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return null;
    }

}
