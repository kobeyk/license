package com.appleyk.verify.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>WebPage映射</p>
 *
 * @author appleyk
 * @version V.0.2.1
 * @blob https://blog.csdn.net/appleyk
 * @date created on 00:02 上午 2020/8/22
 */
@Controller
public class PageController {

    @GetMapping("/{page}")
    public String getLogin(@PathVariable(name = "page") String page){
        return page+".html";
    }

}
