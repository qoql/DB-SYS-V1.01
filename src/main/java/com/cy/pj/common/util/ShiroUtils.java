package com.cy.pj.common.util;

import com.cy.pj.sys.entity.SysUser;
import org.apache.shiro.SecurityUtils;

public class ShiroUtils {
    public static String getUsername(){
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        return user.getUsername();
    }
}
