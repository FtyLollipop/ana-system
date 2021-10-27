package tech.naive.anasystem.service;

import org.bouncycastle.pqc.crypto.ntru.IndexGenerator;
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

    public void editUser(Long userId,String realName,Integer role,Integer state){
        User user = userDao.getUser(userId);
        user.setRealName(realName);
        user.setRole(role);
        user.setState(state);
        userDao.updateUser(user);
    }

    public User getUserById(Long userId){
        return userDao.getUser(userId);
    }

    public User getUserByUserName(String userName){
        return userDao.getUserByUserName(userName);
    }

    public List<User> getUsers(Integer page){
        return userDao.getUsers(9,9*(page-1));
    }

    public Integer getUsersTotal(){
        int count = userDao.getUsersTotal();
        int total = 0;
        if(count%9>0)
            total ++;
        total += count/9;
        return total;
    }

    public void deleteUser(Long userId){
        userDao.deleteUser(userId);
    }

    public void changePassword(Long userId,String password){
        User user = userDao.getUser(userId);
        user.setPassword(password);
        userDao.updateUser(user);
    }

    public void changeRealName(Long userId,String realName){
        User user = userDao.getUser(userId);
        user.setRealName(realName);
        userDao.updateUser(user);
    }
}