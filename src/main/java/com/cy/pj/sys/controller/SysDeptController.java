package com.cy.pj.sys.controller;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.sys.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/dept/")
public class SysDeptController {
    @Autowired
    SysDeptService service;

    @RequestMapping("doFindZTreeNodes")
    @ResponseBody
    public JsonResult doFindZTreeNodes(){
        return new JsonResult(service.findZTreeNodes());
    }
}
