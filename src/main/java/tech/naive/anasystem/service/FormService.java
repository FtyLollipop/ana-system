package tech.naive.anasystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.naive.anasystem.dao.FormDao;
import tech.naive.anasystem.entity.Form;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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

    public void editForm(Long formId,String title,String content){
        Form form = formDao.getForm(formId);;
        form.setTitle(title);
        form.setContent(content);
        formDao.updateForm(form);
    }

    public List<Form> getFormsByUser(Long userId,Integer page){

        return formDao.getFormsByUser(userId,9,9*(page-1));
    }
}