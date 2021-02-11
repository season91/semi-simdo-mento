package simdo.module.qna.form;


import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

//사용자가 입력해야하는 정보들
@Data
@Getter
public class QnaForm {
    @NotBlank
    private String subject;

    @NotBlank
    private String content;
}
