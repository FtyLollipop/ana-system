package tech.naive.anasystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.naive.anasystem.dao.FormDao;
import tech.naive.anasystem.entity.AdminForm;
import tech.naive.anasystem.entity.Form;
import tech.naive.anasystem.entity.UserForm;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 2018034605 冯天阳
 * @version 1.0
 * @date 10/25/2021 3:57 PM
 */
@Service
public class FormService {
    @Autowired
    FormDao formDao;

    public void createForm(Long userId,String title,String content){
        Form form = new Form();
        form.setUserId(userId);
        form.setTitle(title);
        form.setContent(content);
        form.setState(0);
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        form.setCreateTime(timestamp.toString());
        formDao.insertForm(form);
    }

    public void approveForm(Long formId,Long approveUserId,Integer approveState){
        Form form = formDao.getForm(formId);
        form.setApproveUserId(approveUserId);
        form.setState(approveState);
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        form.setApproveTime(timestamp.toString());
        formDao.updateForm(form);
    }

    public void deleteForm(Long formId){
        formDao.deleteForm(formId);
    }

    public void editForm(Long formId,String title,String content,Integer state){
        Form form = formDao.getForm(formId);;
        form.setTitle(title);
        form.setContent(content);
        form.setState(state);
        form.setApproveTime(null);
        form.setApproveUserId(null);
        formDao.updateForm(form);
    }

    public List<UserForm> getFormsByUser(Long userId, Integer page){
        return formDao.getFormsByUser(userId,9,9*(page-1));
    }

    public Integer getFormsByUserTotal(Long userId){
        int count = formDao.getFormsByUserTotal(userId);
        int total = 0;
        if(count%9>0)
            total ++;
        total += count/9;
        return total;
    }

    public List<UserForm> getFormsByUserAndState(Long userId,Integer state,Integer page){
        return formDao.getFormsByUserAndState(userId,state,9,9*(page-1));
    }

    public Integer getFormsByUserAndStateTotal(Long userId,Integer state){
        int count = formDao.getFormsByUserAndStateTotal(userId,state);
        int total = 0;
        if(count%9>0)
            total ++;
        total += count/9;
        return total;
    }

    public List<AdminForm> getForms(Integer page){
        return formDao.getForms(9,9*(page-1));
    }

    public Integer getFormsTotal(){
        int count = formDao.getFormsTotal();
        int total = 0;
        if(count%9>0)
            total ++;
        total += count/9;
        return total;
    }

    public List<AdminForm> getFormsByState(Integer state,Integer page){
        return formDao.getFormsByState(state,9,9*(page-1));
    }

    public Integer getFormsByStateTotal(Integer state){
        int count = formDao.getFormsByStateTotal(state);
        int total = 0;
        if(count%9>0)
            total ++;
        total += count/9;
        return total;
    }

    public Form getForm(Long formId){
        return formDao.getForm(formId);
    }
}