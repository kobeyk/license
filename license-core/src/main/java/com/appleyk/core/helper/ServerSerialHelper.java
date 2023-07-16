package com.appleyk.core.helper;

import com.appleyk.core.utils.CommonUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * <p>获取服务器硬件序列号辅助类</p>
 *
 * @author appleyk
 * @version v1.0.0
 * @blob https://blog.csdn.net/appleyk
 * @date created on  10:42 下午 2020/8/21
 */
public class ServerSerialHelper {

    /**
     * 执行Linux的shell获取Linux信息
     * @param shell 命令
     * @return String Server信息
     * @throws Exception 默认异常
     */
    public static String getLinuxSerial(String[] shell) throws Exception {
        String serial = "";
        /** 使用dmidecode命令获取列号 */
        Process process = Runtime.getRuntime().exec(shell);
        process.getOutputStream().close();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = reader.readLine().trim();
        if(CommonUtils.isNotEmpty(line)){
            serial = line;
        }
        reader.close();
        return serial;
    }

    /**
     * 执行windows的command获取Windows信息
     * @param command 命令
     * @return String Server信息
     * @throws Exception 默认异常
     */
    public static String getWindowsSerial(String command) throws Exception {
        String serial = "";
        /** 使用WMIC获取序列号 */
        Process process = Runtime.getRuntime().exec(command);
        process.getOutputStream().close();
        Scanner scanner = new Scanner(process.getInputStream());
        if(scanner.hasNext()){
            scanner.next();
        }
        if(scanner.hasNext()){
            serial = scanner.next().trim();
        }
        scanner.close();
        return serial;
    }
}
