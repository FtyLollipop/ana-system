package tech.naive.anasystem.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tech.naive.anasystem.entity.User;
import tech.naive.anasystem.service.FormService;
import tech.naive.anasystem.service.UserService;
import tech.naive.anasystem.utils.Result;


/**
 * @author 2018034605 冯天阳
 * @version 1.0
 * @date 10/25/2021 3:39 PM
 */
@RequestMapping("/api/form")
@Controller
@ResponseBody
public class FormController {
    @Autowired
    private FormService formService;

    @RequestMapping(value = "/createForm",method = RequestMethod.POST)
    public Result createForm(@RequestParam("title") String title,
                             @RequestParam("content") String content,
                             @RequestAttribute("requestUserId") Long requestUserId){
        if(!StringUtils.isNoneBlank(title)){
            return Result.build(200,"NULL_TITLE","标题不能为空");
        }else if(!StringUtils.isNoneBlank(content)){
            return Result.build(200,"NULL_CONTENT","内容不能为空");
        }else{
            formService.createForm(requestUserId,title,content);
            return Result.ok("成功");
        }
    }

    @RequestMapping(value = "/getFormsByUserId",method = RequestMethod.GET)
    public Result getFormsByUserId(@RequestParam("userId") Long userId,@RequestParam("page") Integer page,@RequestAttribute("requestUserId") Long requestUserId){
        System.out.println(requestUserId);
        return Result.ok(formService.getFormsByUser(userId,page));
    }
}