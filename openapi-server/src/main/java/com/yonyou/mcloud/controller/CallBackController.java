package com.yonyou.mcloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * CodeController用于接收回调接口
 * Created by hubo on 2016/3/7.
 */
@RestController
@RequestMapping("/callback")
public class CallBackController extends BasicController{

    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public Object code(@RequestParam(value = "code", required = true)String code,
                       @RequestParam(value = "state", required = false)String state) {
        Map<String, String> result = new HashMap<>();
        result.put("code", code);
        result.put("state", state);
        return success(result);
    }

}
