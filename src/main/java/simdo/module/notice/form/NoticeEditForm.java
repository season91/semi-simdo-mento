package simdo.module.notice.form;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Data
@Getter
public class NoticeEditForm {

    @NotBlank
    private String subject;

    @NotBlank
    private String content;

}
