package simdo.movie.member.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class MemberForm {
    private String username;
    private String email;
    private String password;
}
