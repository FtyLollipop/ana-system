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

    @Insert("insert into users(user_id,user_name,password,real_name,role,create_time,state)" +
            "values(#{userId},#{userName},#{password},#{realName},#{role},#{createTime},#{state})")
    void insertUser(User user);

    @Delete("update users set deleted = 1,user_name = '' where user_id = #{userId}")
    boolean deleteUser(Long userId);

    @Update("update users set user_name = #{userName},password = #{password},real_name = #{realName},role = #{role},state = #{state} where user_id = #{userId}")
    boolean updateUser(User user);

    @Select("select * from users where user_id = #{userId} and deleted <> 1")
    User getUser(Long userId);

    @Select("select * from users where user_name = #{userName} and deleted <> 1")
    User getUserByUserName(String userName);

    @Select("select * from users where deleted <> 1 order by user_id asc limit #{limit} offset #{offset}")
    List<User> getUsers(Integer limit,Integer offset);

    @Select("select count(*) from users where deleted <> 1")
    Integer getUsersTotal();
}
