package tech.naive.anasystem.domain;

import lombok.Data;
import org.springframework.stereotype.Repository;

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
