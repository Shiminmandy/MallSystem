package com.imooc.mall.controller;

import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.form.UserForm;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @PostMapping("/register")
    //form url encode
//    public void register(@RequestParam String username){
//        log.info("username={}",username);
//    }
    //json
    public ResponseVo register(@Valid @RequestBody UserForm userForm, BindingResult bindingResult){
        //表单验证
        if (bindingResult.hasErrors()) {
            log.error("注册提交的参数有误，{} {}",
                    bindingResult.getFieldError().getField(),
                    bindingResult.getFieldError().getDefaultMessage());
            return ResponseVo.error(ResponseEnum.PARAM_ERROR, bindingResult);
        }
        log.info("username={}",userForm.getUsername());
        //return ResponseVo.success("注册成功");
        return ResponseVo.error(ResponseEnum.NEED_LOGIN);
    }
}
