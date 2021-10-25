package tech.naive.anasystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.naive.anasystem.entity.User;
import tech.naive.anasystem.dao.UserDao;

import java.util.List;

/**
 * @author 2018034605 冯天阳
 * @version 1.0
 * @date 10/25/2021 9:33 AM
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public void createUser(User user){
         userDao.insertUser(user);
    }

    public User getUserById(Long userId){
        return userDao.getUser(userId);
    }

    public User getUserByUserName(String userName){
        return userDao.getUserByUserName(userName);
    }

    public List<User> getUsers(){
        return userDao.getUserList();
    }
}