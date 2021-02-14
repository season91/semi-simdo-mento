package simdo.module.mypage.form;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Data
@Getter
public class ReviewUpdateForm {

    @NotBlank
    private double score;

    @NotBlank
    private String content;

    @NotBlank
    private String watchDate;

    @NotBlank
    private String rvNo;
}
