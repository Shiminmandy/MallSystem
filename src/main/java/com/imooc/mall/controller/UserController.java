package com.imooc.mall.controller;

import com.imooc.mall.pojo.User;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseVo register(@RequestBody User user){
        log.info("username={}",user.getUsername());
        return ResponseVo.success("注册成功");
    }
}
