package tech.naive.anasystem.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tech.naive.anasystem.domain.Form;
import tech.naive.anasystem.domain.User;
import tech.naive.anasystem.service.FormService;
import tech.naive.anasystem.service.UserService;
import tech.naive.anasystem.utils.Result;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/form")
@Controller
@ResponseBody
@CrossOrigin
public class FormController {
    @Autowired
    private  UserService userService;

    @Autowired
    private FormService formService;

    @RequestMapping(value = "/createForm",method = RequestMethod.POST)
    public Result createForm(@RequestParam("title") String title,
                             @RequestParam("content") String content,
                             @RequestAttribute("requestUserId") Long requestUserId){

        if(userService.getUserById(requestUserId).getRole() != 0){
            return Result.build(200,"ACCESS_DEN","无权限使用此功能");
        }

        if(!StringUtils.isNoneBlank(title)){
            return Result.build(200,"NULL_TITLE","标题不能为空");
        }else if(!StringUtils.isNoneBlank(content)){
            return Result.build(200,"NULL_CONTENT","内容不能为空");
        }else{
            formService.createForm(requestUserId,title,content);
            return Result.ok("成功");
        }
    }

    @RequestMapping(value = "/getUserForms",method = RequestMethod.GET)
    public Result getUserForms(@RequestParam("page") Integer page,@RequestAttribute("requestUserId") Long requestUserId){

        if(userService.getUserById(requestUserId).getRole() != 0){
            return Result.build(200,"ACCESS_DEN","无权限使用此功能");
        }

        Map<String,Object> data = new HashMap<>();
        data.put("list",formService.getFormsByUser(requestUserId,page));
        data.put("total",formService.getFormsByUserTotal(requestUserId));
        return Result.ok(data);
    }

    @RequestMapping(value = "/getUserFormsByState",method = RequestMethod.GET)
    public Result getUserFormsByState(@RequestParam("state") Integer state,@RequestParam("page") Integer page,@RequestAttribute("requestUserId") Long requestUserId){

        if(userService.getUserById(requestUserId).getRole() != 0){
            return Result.build(200,"ACCESS_DEN","无权限使用此功能");
        }

        Map<String,Object> data = new HashMap<>();
        data.put("list",formService.getFormsByUserAndState(requestUserId,state,page));
        data.put("total",formService.getFormsByUserAndStateTotal(requestUserId,state));
        return Result.ok(data);
    }

    @RequestMapping(value = "/getAdminForms",method = RequestMethod.GET)
    public Result getAdminForms(@RequestParam("page") Integer page,@RequestAttribute("requestUserId") Long requestUserId){

        if(userService.getUserById(requestUserId).getRole() == 0){
            return Result.build(200,"ACCESS_DEN","权限不足");
        }

        Map<String,Object> data = new HashMap<>();
        data.put("list",formService.getForms(page));
        data.put("total",formService.getFormsTotal());
        return Result.ok(data);
    }

    @RequestMapping(value = "/getAdminFormsByState",method = RequestMethod.GET)
    public Result getAdminFormsByState(@RequestParam("page") Integer page,@RequestParam("state") Integer state,@RequestAttribute("requestUserId") Long requestUserId){

        if(userService.getUserById(requestUserId).getRole() == 0){
            return Result.build(200,"ACCESS_DEN","权限不足");
        }

        Map<String,Object> data = new HashMap<>();
        data.put("list",formService.getFormsByState(state,page));
        data.put("total",formService.getFormsByStateTotal(state));
        return Result.ok(data);
    }

    @RequestMapping(value = "/cancelForm",method = RequestMethod.DELETE)
    public Result cancelForm(@RequestParam("formId") Long formId,@RequestAttribute("requestUserId") Long requestUserId){
        Form form = formService.getForm(formId);
        if(form.getUserId() != requestUserId || form.getState()!=0){
            return Result.build(200,"ACCESS_DEN","权限不足");
        }else{
            formService.deleteForm(formId);
            return Result.ok("成功");
        }
    }

    @RequestMapping(value = "/approveForm",method = RequestMethod.POST)
    public Result approveForm(@RequestParam("formId") Long formId,@RequestParam("state") Integer state,@RequestAttribute("requestUserId") Long requestUserId){
        Form form = formService.getForm(formId);
        User user = userService.getUserById(requestUserId);
        if(user.getRole() != 1 || form.getState() != 0){
            return Result.build(200,"ACCESS_DEN","权限不足");
        }else{
            formService.approveForm(formId,requestUserId,state);
            return Result.ok("成功");
        }
    }

    @RequestMapping(value = "/deleteForm",method = RequestMethod.DELETE)
    public Result deleteForm(@RequestParam("formId") Long formId,@RequestAttribute("requestUserId") Long requestUserId){
        Form form = formService.getForm(formId);
        User user = userService.getUserById(requestUserId);
        if(user.getRole() != 2){
            return Result.build(200,"ACCESS_DEN","权限不足");
        }else{
            formService.deleteForm(formId);
            return Result.ok("成功");
        }
    }

    @RequestMapping(value = "/editForm",method = RequestMethod.POST)
    public Result editForm(@RequestParam("formId") Long formId,
                           @RequestParam("title") String title,
                           @RequestParam("content") String content,
                           @RequestParam("state") Integer state,
                           @RequestAttribute("requestUserId") Long requestUserId){
        Form form = formService.getForm(formId);
        User user = userService.getUserById(requestUserId);
        if(user.getRole() != 2){
            return Result.build(200,"ACCESS_DEN","权限不足");
        }else{
            formService.editForm(formId,title,content,state);
            return Result.ok("成功");
        }
    }
}