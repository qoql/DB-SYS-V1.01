package com.cy.pj.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {
    /**返回登录页面*/
    @RequestMapping("doLoginUI")
    public String doLoginUI(){
        return "login";
    }

    @RequestMapping("doIndexUI")
    public String doIndexUI(){
//        System.out.println(111);
        return "starter";
    }
    //log/log_list
//    @RequestMapping("log/log_list")
//    public String doLogUI(){
//        return "sys/log_list";
//    }

//    //menu/menu_list
//    @RequestMapping("menu/menu_list")
//    public String doMenuUI(){
//        return "sys/menu_list";
//    }

    @RequestMapping("doPageUI")
    public String doPageUI(){
        return "common/page";
    }

    //基于rest框架编程风格返回某个模块的UI页面
    @RequestMapping("{module}/{ui}")
    public String doMenuUI(@PathVariable("ui") String ui){
        return "sys/"+ui;
    }


}
