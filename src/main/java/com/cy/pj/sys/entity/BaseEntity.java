package com.cy.pj.sys.entity;

import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity {
    /**创建用户*/
    private String createdUser;
    /**修改用户*/
    private String modifiedUser;
    private Date createdTime;
    private Date modifiedTime;
}
