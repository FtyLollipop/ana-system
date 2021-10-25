package tech.naive.anasystem.entity;

import lombok.Data;
import org.springframework.stereotype.Repository;

/**
 * @author 2018034605 冯天阳
 * @version 1.0
 * @date 10/25/2021 9:26 AM
 */
@Data
@Repository
public class Form {
    private Long formId;
    private Long userId;
    private String title;
    private String content;
    private String createTime;
    private Integer state;
    private String approveTime;
    private Long approveUserId;
}
