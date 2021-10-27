package tech.naive.anasystem.domain;

import lombok.Data;
import org.springframework.stereotype.Repository;

@Data
@Repository
public class AdminForm {
    private Long formId;
    private Long userId;
    private String realName;
    private String title;
    private String content;
    private String createTime;
    private Integer state;
    private String approveTime;
    private Long approveUserId;
    private String approveRealName;
}
