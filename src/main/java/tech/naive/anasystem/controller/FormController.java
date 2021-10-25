package tech.naive.anasystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.naive.anasystem.service.FormService;
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

    @RequestMapping(value = "/getFormsByUserId",method = RequestMethod.GET)
    public Result getFormsByUserId(Long userId){
        return Result.ok(formService.getFormsByUser(userId));
    }
}