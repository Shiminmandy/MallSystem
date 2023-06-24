package com.imooc.mall.enums;

import lombok.Getter;

@Getter
public enum ResponseEnum {
    ERROR(-1,"服务端错误"),
    SUCCESS(0,"登入成功"),
    PASSWORD_ERROR(1,"密码错误"),
    USER_EXIST(2,"用户已存在"),
    PARAM_ERROR(3,"参数错误"),
    NEED_LOGIN(10,"用户未登入，请先登入"),
    ;

    Integer code;
    String desc;
    ResponseEnum(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }
}