package simdo.module.member.form;

import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@Getter
public class UpdateForm {

    @Length(min = 2, max = 8)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣A-Za-z0-9_-]{2,8}$")
    private String name;

    @Length(min = 8, max = 50)
    private String password;

    private String phone;

    private String gender;

    private LocalDate birthday;

}
