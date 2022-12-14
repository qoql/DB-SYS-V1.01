package com.cy.pj.sys.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysDept {
    private Integer id;
    private String name;
    private Integer parentId;
    private Integer sort;
    private String note;
    private Date createdTime;
    private Date modifiedTime;
    private String createdUser;
    private String modifiedUser;
}
