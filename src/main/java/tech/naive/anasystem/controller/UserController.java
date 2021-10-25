package tech.naive.anasystem.controller;

import org.springframework.stereotype.Controller;
import tech.naive.anasystem.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.naive.anasystem.service.UserService;
import tech.naive.anasystem.utils.JWTUtil;
import tech.naive.anasystem.utils.Result;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 2018034605 冯天阳
 * @version 1.0
 * @date 10/25/2021 10:04 AM
 */
@RequestMapping("/api/user")
@ResponseBody
@Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Result register(@RequestParam("userName") String userName,
                           @RequestParam("password") String password,
                           @RequestParam("realName") String realName){

        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setRealName(realName);
        user.setRole(0);
        user.setState(0);
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        user.setCreateTime(timestamp.toString());

        userService.createUser(user);
        return Result.ok("成功");
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password){
        User user = userService.getUserByUserName(userName);
        if(user == null || !user.getPassword().equals(password)){
            return Result.build(200,"WRONG","用户名或密码错误");
        }else if(user.getState() == 1){
            return Result.build(200,"BLOCKED","该用户已被冻结");
        }else{
            return Result.ok(JWTUtil.token(userName,password));
        }
    }

    @RequestMapping(value = "/loginWithToken",method = RequestMethod.POST)
    public Map<String,String> loginWithToken(){
        Map<String,String> result = new HashMap<>();


        return result;

    }


}
