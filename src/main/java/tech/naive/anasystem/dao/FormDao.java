package tech.naive.anasystem.dao;

import org.apache.ibatis.annotations.*;
import tech.naive.anasystem.entity.AdminForm;
import tech.naive.anasystem.entity.Form;
import tech.naive.anasystem.entity.UserForm;

import java.util.List;

/**
 * @author 2018034605 冯天阳
 * @version 1.0
 * @date 10/25/2021 3:40 PM
 */
@Mapper
public interface FormDao {
    @Insert("insert into forms(user_id,title,content,create_time,state,approve_time,approve_user_id) " +
            "values(#{userId},#{title},#{content},#{createTime},#{state},#{approveTime},#{approveUserId})")
    void insertForm(Form form);

    @Delete("delete from forms where form_id = #{formId}")
    void deleteForm(Long formId);

    @Update("update forms set title = #{title},content = #{content},state = #{state},approve_time = #{approveTime},approve_user_id = #{approveUserId} " +
            "where form_id = #{formId}")
    void updateForm(Form form);

    @Select("select * from forms where form_id = #{formId}")
    Form getForm(Long formId);

    @Select("select * from forms where user_id = #{userId} order by form_id desc limit #{limit} offset #{offset}")
    List<UserForm> getFormsByUser(Long userId, Integer limit, Integer offset);

    @Select("select count(*) from forms where user_id = #{userId}")
    Integer getFormsByUserTotal(Long userId);

    @Select("select form_id,title,content,create_time,state,approve_time from forms " +
            "where (user_id = #{userId}) and (state = #{state}) order by form_id desc limit #{limit} offset #{offset}")
    List<UserForm> getFormsByUserAndState(Long userId,Integer state,Integer limit,Integer offset);

    @Select("select count(*) from forms where (user_id = #{userId}) and (state = #{state})")
    Integer getFormsByUserAndStateTotal(Long userId,Integer state);

    @Select("select form_id,tb.user_id,tb.real_name,title,content,tb.create_time,approve_time,approve_user_id,users.real_name as 'approve_real_name',tb.state from(select form_id,forms.user_id,users.real_name,title,content,forms.create_time,approve_time,approve_user_id,forms.state " +
            "from forms left join users " +
            "on forms.user_id = users.user_id) " +
            "as tb " +
            "left join users " +
            "on tb.approve_user_id = users.user_id " +
            "order by form_id desc limit #{limit} offset #{offset}")
    List<AdminForm> getForms(Integer limit,Integer offset);

    @Select("select count(*) from forms")
    Integer getFormsTotal();

    @Select("select form_id,tb.user_id,tb.real_name,title,content,tb.create_time,approve_time,approve_user_id,users.real_name as 'approve_real_name',tb.state from(select form_id,forms.user_id,users.real_name,title,content,forms.create_time,approve_time,approve_user_id,forms.state " +
            "from forms left join users " +
            "on forms.user_id = users.user_id where forms.state = #{state}) " +
            "as tb " +
            "left join users " +
            "on tb.approve_user_id = users.user_id " +
            "order by form_id desc limit #{limit} offset #{offset}")
    List<AdminForm> getFormsByState(Integer state,Integer limit,Integer offset);

    @Select("select count(*) from forms where state = #{state}")
    Integer getFormsByStateTotal(Integer state);
}
