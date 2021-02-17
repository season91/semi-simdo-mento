package simdo.module.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author minhee
 */
//구글 로그인을 위한 세팅
@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자"),
    ADMIN("ROLE_ADMIN","관리자"); //아영추가

    private final String key;
    private final String title;
}
