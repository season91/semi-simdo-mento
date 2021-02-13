package simdo.module.member.form;

import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Getter
public class UpdateForm {

    @NotBlank
    @Length(min = 2, max = 8)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣A-Za-z0-9_-]{2,8}$", message = "이름을 공백없이 문자와 숫자로만 2자 이상 8자 이내로 입력하세요.")
    private String name;

    @Length(min = 8, max = 50)
    private String password;

    private String phone;

    private String gender;

    private String birthday;

}
