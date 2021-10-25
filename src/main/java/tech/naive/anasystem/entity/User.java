package tech.naive.anasystem.entity;

import lombok.Data;
import org.springframework.stereotype.Repository;

/**
 * @author 2018034605 冯天阳
 * @version 1.0
 * @date 10/25/2021 9:24 AM
 */
@Data
@Repository
public class User {
    private Long userId;
    private String userName;
    private String password;
    private String realName;
    private Integer role;   //0为普通用户，1为管理员
    private String createTime;
    private Integer state;  //0为正常，1为冻结
}
