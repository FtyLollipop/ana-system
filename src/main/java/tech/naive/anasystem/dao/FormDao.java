package tech.naive.anasystem.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tech.naive.anasystem.entity.Form;

import java.util.List;

/**
 * @author 2018034605 冯天阳
 * @version 1.0
 * @date 10/25/2021 3:40 PM
 */
@Mapper
public interface FormDao {
    @Select("insert into forms(user_id,title,content,create_time,state,approve_time,approve_user_id)" +
            "values(#{userId},#{title},#{content},#{create_time},#{state},#{approve_time},#{approve_user_id})")
    boolean insertForm(Form form);

    @Delete("delete from forms where form_id = #{formId}")
    boolean deleteForm(Long formId);

    @Update("update user set user_name = #{userName},password = #{password},real_name = #{realName},state = #{state} where form_id = #{formId}")
    boolean updateForm(Form form);

    @Select("select * from forms where form_id = #{formId}")
    Form getForm(Long formId);

    @Select("select * from forms where approve_user_id = #{approveUserId}")
    List<Form> getFormsByApproveUser(Long approveUserId);

    @Select("select * from forms where user_id = #{userId}")
    List<Form> getFormsByUser(Long userId);

    @Select("select * from form where state = #{state}")
    List<Form> getFormsByState(Integer state);

    @Select("select * from form")
    List<Form> getFormList();
}
