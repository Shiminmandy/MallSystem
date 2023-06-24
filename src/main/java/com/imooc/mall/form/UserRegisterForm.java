package com.imooc.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data

public class UserRegisterForm {
    //@NotBlank(message = "用户名不能为空")  //用于String 判断空格 不允许传空格
    @NotBlank
    //@NotNull
    //@NotEmpty 用于集合
    private String username;

    private String password;

    private String email;
}
