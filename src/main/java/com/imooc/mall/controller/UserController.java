package com.imooc.mall.controller;

import com.imooc.mall.consts.MallConst;
import com.imooc.mall.form.UserLoginForm;
import com.imooc.mall.form.UserRegisterForm;
import com.imooc.mall.pojo.User;
import com.imooc.mall.service.IUserService;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;




@RestController
//@RequestMapping("/user")
@Slf4j
//注册
//登入
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/user/register")
    //form url encode
//    public void register(@RequestParam String username){
//        log.info("username={}",username);
//    }
    //json
    public ResponseVo register(@Valid @RequestBody UserRegisterForm userForm){
        //表单验证
//        if (bindingResult.hasErrors()) {
//            log.error("注册提交的参数有误，{} {}",
//                    bindingResult.getFieldError().getField(),
//                    bindingResult.getFieldError().getDefaultMessage());
//            return ResponseVo.error(ResponseEnum.PARAM_ERROR, bindingResult);
//        }
        //log.info("username={}",userForm.getUsername());
        //return ResponseVo.success("注册成功");
        //return ResponseVo.error(ResponseEnum.NEED_LOGIN);

        User user = new User();
        //拷贝userForm to user
        BeanUtils.copyProperties(userForm,user);
        //dto
        return userService.register(user);
    }

    @PostMapping("/user/login")
    public ResponseVo<User> login(@Valid @RequestBody UserLoginForm userLoginForm,
                                   HttpSession httpSession){

        ResponseVo<User> userResponseVo = userService.login(userLoginForm.getUsername(),userLoginForm.getPassword());

        //设置Session
        httpSession.setAttribute(MallConst.CURRENT_USER,userResponseVo.getData());
        log.info("/login sessionId={}", httpSession.getId());
        return userResponseVo;
    }


    //获取用户信息
    //session 保存在内存里，很容易丢失，电脑或服务器重启会导致内容丢失
    //改进版本 token+redis用来存储
    @GetMapping("/user")
    public ResponseVo<User> userInfo(HttpSession session){
        log.info("/user sessionId={}",session.getId());
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return ResponseVo.success(user);
    }

    //登出功能
    //TODO 判断登入状态, 使用拦截器 -- interceptor(url),AOP(包名)
    @PostMapping("/user/logout")
    public ResponseVo logout(HttpSession session){
        log.info("/user/logout sessionId={}",session.getId());
        //有拦截器，这里不需要判断是否登入了
//        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
//        if (user == null){
//            return ResponseVo.error(ResponseEnum.NEED_LOGIN);
//        }
        session.removeAttribute(MallConst.CURRENT_USER);
        return ResponseVo.success();
    }
}
