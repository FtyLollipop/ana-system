package tech.naive.anasystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.naive.anasystem.dao.FormDao;
import tech.naive.anasystem.entity.Form;

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

    public List<Form> getFormsByUser(Long userId){
        return formDao.getFormsByUser(userId);
    }
}