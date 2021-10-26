package tech.naive.anasystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.naive.anasystem.entity.User;
import tech.naive.anasystem.dao.UserDao;

import java.sql.Timestamp;
import java.util.Date;
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

    public void createUser(String userName,String password,String realName){
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setRealName(realName);
        user.setRole(0);
        user.setState(0);
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        user.setCreateTime(timestamp.toString());
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

    public void deleteUser(Long userId){
        userDao.deleteUser(userId);
    }

    public void changePassword(Long userId,String password){
        User user = userDao.getUser(userId);
        user.setPassword(password);
        userDao.updateUser(user);
    }
}