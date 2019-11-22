package com.nju.netease.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StartController {

    @RequestMapping("/ML")
    public String getMLPage(){
        return "ML/MLShow";
    }
}
