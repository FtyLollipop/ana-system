package tech.naive.anasystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tech.naive.anasystem.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import tech.naive.anasystem.service.UserService;
import tech.naive.anasystem.utils.Encrypt;
import tech.naive.anasystem.utils.JWTUtil;
import tech.naive.anasystem.utils.Result;

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
    String USER_REG = "^[^0-9][\\w_]{3,12}$";
    String PWD_REG = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$";

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Result register(@RequestParam("userName") String userName,
                           @RequestParam("password") String password,
                           @RequestParam("realName") String realName){
        User user = userService.getUserByUserName(userName);
        if(!userName.matches(USER_REG)){
            return Result.build(200,"INVALID_USER_NAME","用户名必须由4-12位的字母、数字和下划线组成，且不能以数字开头");
        }else if(!password.matches((PWD_REG))){
            return Result.build(200,"INVALID_PWD","密码必须为8-16位，且至少包含一个大写字母、一个小写字母、一个数字和一个特殊符号");
        }else if(user != null){
            return Result.build(200,"EXIST_USER","用户名已被占用");
        }else{
            userService.createUser(userName, Encrypt.encrypt(password),realName);
            return Result.ok("成功");
        }
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password){
        User user = userService.getUserByUserName(userName);
        if(user == null || user.getDeleted() == 1 || !user.getPassword().equals(Encrypt.getSHA(password))){
            return Result.build(200,"WRONG","用户名或密码错误");
        }else{
            return Result.ok(JWTUtil.token(user.getUserId(),user.getUserName(),user.getUserName()));
        }
    }

    @RequestMapping(value = "/deleteUser",method = RequestMethod.GET)
    public Result deleteUser(@RequestParam("userId") Long userId,
                             @RequestAttribute("requestUserId") Long requestUserId){
        User requestUser = userService.getUserById(requestUserId);
        if(requestUser.getRole() != 1) {
            return Result.build(200, "PMS_DND", "权限不足");
        }else if(userService.getUserById(userId) == null){
            return Result.build(200,"NOT_FOUND","未找到该用户");
        }else{
            userService.deleteUser(userId);
            return Result.ok("成功");
        }

    }

    @RequestMapping(value = "/changePassword",method = RequestMethod.POST)
    public Result changePassword(@RequestParam("password") String password,@RequestAttribute("requestUserId") Long requestUserId){
        User user = userService.getUserById(requestUserId);
        String encryptedPassword = Encrypt.encrypt(password);
        if(!password.matches(PWD_REG)){
            return Result.build(200,"INVALID_PWD","密码必须为8-16位，且至少包含一个大写字母、一个小写字母、一个数字和一个特殊符号");
        }else if(encryptedPassword.equals(user.getPassword())){
            return Result.build(200,"SAME_PWD","使用了原密码");
        } else{
            userService.changePassword(requestUserId,encryptedPassword);
            return Result.ok("成功");
        }
    }
}