package simdo.infra.common.auth;

import lombok.Getter;
import simdo.module.member.Member;

import java.io.Serializable;

//구글 로그인을 위한 세팅
@Getter
public class SessionMember implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionMember(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getPicture();
    }
}
