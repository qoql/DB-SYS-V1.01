package com.cy.pj.common.vo;

import java.io.Serializable;

/**
 * VO:
 * 借助对象封装服务端的响应数据
 * 1)响应状态码（1为正常数据，0为异常数据）
 * 2)响应消息（呈现给用户的信息，例如一个弹窗中的数据）
 * 3)响应数据（要呈现的正常数据，例如日志记录信息）
 */
public class JsonResult implements Serializable {

    private static final long serialVersionUID = -3841554617700179347L;
    /*状态码，默认是正常*/
    private int state = 1;
    /*状态码对应的状态信息，默认是ok*/
    private String message = "ok";
    /*正常数据*/
    private Object data;

    public JsonResult(){}

    //如果是正常的状态信息，默认状态码是1
    public JsonResult(String message) {
        this.message = message;
    }
    //如果有数据，默认状态码是1
    public JsonResult(Object data){
        this.data = data;
    }

    public JsonResult(Throwable e){
        this.state = 0;
        this.message = e.getMessage();
    }

    public JsonResult(int state, String message, Object data) {
        this.state = state;
        this.message = message;
        this.data = data;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
