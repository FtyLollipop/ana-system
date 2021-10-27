package tech.naive.anasystem.domain;

import lombok.Data;
import org.springframework.stereotype.Repository;

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
    private Integer deleted; //0为正常，1为已删除
}
