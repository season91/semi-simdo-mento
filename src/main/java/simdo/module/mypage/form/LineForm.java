package simdo.module.mypage.form;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Data
@Getter
public class LineForm {


    @NotBlank
    private String content;

    @NotBlank
    private String mvNo;
}
