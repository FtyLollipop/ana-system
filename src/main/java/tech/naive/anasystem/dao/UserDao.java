package tech.naive.anasystem.dao;

import org.apache.ibatis.annotations.*;
import tech.naive.anasystem.entity.User;

import java.util.List;

/**
 * @author 2018034605 冯天阳
 * @version 1.0
 * @date 10/25/2021 9:33 AM
 */
@Mapper
public interface UserDao {

    @Select("insert into user(user_id,user_name,password,real_name,role,create_time,state)" +
            "values(#{userId},#{userName},#{password},#{realName},#{role},#{createTime},#{state})")
    void insertUser(User user);

    @Delete("delete from user where user_id = #{userId}")
    boolean deleteUser(Long userId);

    @Update("update user set user_name = #{userName},password = #{password},real_name = #{realName},state = #{state} where user_id = #{userId}")
    boolean updateUser(User user);

    @Select("select * from user where user_id = #{userId}")
    User getUser(Long userId);

    @Select("select * from user where user_name = #{userName}")
    User getUserByUserName(String userName);

    @Select("select * from user")
    List<User> getUserList();
}
