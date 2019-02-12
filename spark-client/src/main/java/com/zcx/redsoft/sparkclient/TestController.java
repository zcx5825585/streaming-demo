package com.zcx.redsoft.sparkclient;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类说明
 *
 * @author zcx
 * @version 创建时间：2018/11/30  11:24
 */
@RestController
public class TestController {
    @RequestMapping("test")
    public String test(){
        return "";
    }
}
