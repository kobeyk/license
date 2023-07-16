package com.appleyk.core.controller;

import com.appleyk.core.result.ResponseResult;
import com.appleyk.core.service.AServerInfos;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>服务器硬件信息获取API</p>
 *
 * @author appleyk
 * @version v1.0.0
 * @blob https://blog.csdn.net/appleyk
 * @date created on 10:30 下午 2020/8/21
 */
@CrossOrigin
@RestController
@RequestMapping("/license")
public class HardWareInfoController {

    /**
     * <p>获取服务器硬件信息</p>
     * @param osName 操作系统类型，如果为空则自动判断
     */
    @RequestMapping(value = "/getServerInfos",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseResult getServerInfos(@RequestParam(value = "osName",required = false) String osName) {
        return ResponseResult.ok(AServerInfos.getServer(osName).getServerInfos());
    }
}
