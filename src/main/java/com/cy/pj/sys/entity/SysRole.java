package com.cy.pj.sys.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysRole extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 2572115896310178151L;
    private Integer id;
    private String name;
    private String note;
    private Date createdTime;
    private Date modifiedTime;
    private String createdUser;
    private String modifiedUser;

}
