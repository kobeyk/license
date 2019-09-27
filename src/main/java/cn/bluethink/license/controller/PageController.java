package cn.bluethink.license.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>WebPage页面模板映射</p>
 *
 * @author appleyk
 * @version V.1.0.1
 * @blob https://blog.csdn.net/appleyk
 * @date created on 下午 3:59 2019-9-26
 */
@Controller
public class PageController {

    @GetMapping("/{page}")
    public String getLogin(@PathVariable(name = "page") String page){
        return page+".html";
    }

}
