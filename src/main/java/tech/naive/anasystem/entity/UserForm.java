package tech.naive.anasystem.entity;

import lombok.Data;
import org.springframework.stereotype.Repository;

/**
 * @author 2018034605 冯天阳
 * @version 1.0
 * @date 10/27/2021 7:56 AM
 */
@Data
@Repository
public class UserForm {
    private Long formId;
    private String title;
    private String content;
    private String createTime;
    private Integer state;
    private String approveTime;
}
