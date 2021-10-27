package tech.naive.anasystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tech.naive.anasystem.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import tech.naive.anasystem.service.UserService;
import tech.naive.anasystem.utils.Encrypt;
import tech.naive.anasystem.utils.JWTUtil;
import tech.naive.anasystem.utils.Result;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/user")
@ResponseBody
@Controller
@CrossOrigin
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
        if(user == null || user.getDeleted() == 1 || !user.getPassword().equals(Encrypt.encrypt(password))){
            return Result.build(200,"WRONG","用户名或密码错误");
        }else if(user.getState() == 1){
            return Result.build(200,"BLOCKED","用户已被冻结");
        }else{
            Map<String,String> data = new HashMap<>();
            data.put("token",JWTUtil.token(user.getUserId(),user.getUserName(),user.getUserName()));
            data.put("role",user.getRole().toString());
            return Result.ok(data);
        }
    }

    @RequestMapping(value = "/deleteUser",method = RequestMethod.DELETE)
    public Result deleteUser(@RequestParam("userId") Long userId,
                             @RequestAttribute("requestUserId") Long requestUserId){
        User requestUser = userService.getUserById(requestUserId);
        if(requestUser.getRole() != 2) {
            return Result.build(200, "ACCESS_DEN", "权限不足");
        }else if(userId == requestUserId) {
            return Result.build(200, "ACCESS_DEN", "不能删除自己");
        }else if(userService.getUserById(userId) == null){
            return Result.build(200,"NOT_FOUND","未找到该用户");
        }else{
            userService.deleteUser(userId);
            return Result.ok("成功");
        }
    }

    @RequestMapping(value = "/getUser",method = RequestMethod.GET)
    public Result getUser(@RequestAttribute("requestUserId") Long requestUserId){
        User user = userService.getUserById(requestUserId);
        if(user.getRole() == 2){
            return Result.build(200, "ACCESS_DEN", "权限不足");
        }
        return Result.ok(userService.getUserById(requestUserId));
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

    @RequestMapping(value = "/changeRealName",method = RequestMethod.POST)
    public Result changeRealName(@RequestParam("realName") String realName,@RequestAttribute("requestUserId") Long requestUserId){
        User user = userService.getUserById(requestUserId);
        if(user.getRole() == 2) {
            return Result.build(200, "ACCESS_DEN", "权限不足");
        }
        userService.changeRealName(requestUserId,realName);
        return Result.ok("成功");
    }

    @RequestMapping(value = "/getUsers",method = RequestMethod.GET)
    public Result getUsers(@RequestParam("page") Integer page,@RequestAttribute("requestUserId") Long requestUserId){
        User user = userService.getUserById(requestUserId);
        if(user.getRole() != 2) {
            return Result.build(200, "ACCESS_DEN", "权限不足");
        }

        Map<String,Object> data = new HashMap<>();
        data.put("list",userService.getUsers(page));
        data.put("total",userService.getUsersTotal());
        return Result.ok(data);
    }

    @RequestMapping(value = "/editUser",method = RequestMethod.POST)
    public Result getUsers(@RequestParam("userId") Long userId,
                           @RequestParam("realName") String realName,
                           @RequestParam("role") Integer role,
                           @RequestParam("state") Integer state,
                           @RequestAttribute("requestUserId") Long requestUserId){
        User user = userService.getUserById(requestUserId);
        if(user.getRole() != 2 ) {
            return Result.build(200, "ACCESS_DEN", "权限不足");
        }else if(userId == requestUserId){
            return Result.build(200,"ACCESS_DEN","不能修改自己的信息");
        }

        userService.editUser(userId,realName,role,state);

        return Result.ok("成功");
    }
}