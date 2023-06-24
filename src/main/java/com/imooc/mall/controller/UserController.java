package com.imooc.mall.controller;

import com.imooc.mall.consts.MallConst;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.form.UserLoginForm;
import com.imooc.mall.form.UserRegisterForm;
import com.imooc.mall.pojo.User;
import com.imooc.mall.service.IUserService;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;




@RestController
@RequestMapping("/user")
@Slf4j
//注册
//登入
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    //form url encode
//    public void register(@RequestParam String username){
//        log.info("username={}",username);
//    }
    //json
    public ResponseVo register(@Valid @RequestBody UserRegisterForm userForm, BindingResult bindingResult){
        //表单验证
        if (bindingResult.hasErrors()) {
            log.error("注册提交的参数有误，{} {}",
                    bindingResult.getFieldError().getField(),
                    bindingResult.getFieldError().getDefaultMessage());
            return ResponseVo.error(ResponseEnum.PARAM_ERROR, bindingResult);
        }
        //log.info("username={}",userForm.getUsername());
        //return ResponseVo.success("注册成功");
        //return ResponseVo.error(ResponseEnum.NEED_LOGIN);

        User user = new User();
        //拷贝userForm to user
        BeanUtils.copyProperties(userForm,user);
        //dto
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseVo<User> login(@Valid @RequestBody UserLoginForm userLoginForm,
                                  BindingResult bindingResult, HttpSession httpSession){
        if (bindingResult.hasErrors()) {
            return ResponseVo.error(ResponseEnum.PARAM_ERROR, bindingResult);
        }
        ResponseVo<User> userResponseVo = userService.login(userLoginForm.getUsername(),userLoginForm.getPassword());

        //设置Session
        httpSession.setAttribute(MallConst.CURRENT_USER,userResponseVo.getData());
        return userResponseVo;
    }
}
