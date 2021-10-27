package tech.naive.anasystem.domain;

import lombok.Data;
import org.springframework.stereotype.Repository;

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
