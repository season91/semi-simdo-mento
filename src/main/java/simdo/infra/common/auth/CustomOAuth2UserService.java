package simdo.infra.common.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import simdo.module.member.Member;
import simdo.module.member.MemberRepository;
import simdo.module.member.MemberService;
import simdo.module.member.Role;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;

//구글 로그인을 위한 세팅
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(userNameAttributeName, oAuth2User.getAttributes());

        Member member = saveOrUpdate(attributes);
        httpSession.setAttribute("member", new SessionMember(member));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    /*@Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = attributes.get("email").toString();
        String name = attributes.get("name").toString();
        String picture = attributes.get("picture").toString();
        Member member = memberRepository.save(Member.builder()
                .email(email)
                .name(name)
                .picture(picture)
                .build());
        httpSession.setAttribute("member", new SessionMember(member));
        return oAuth2User;
    }*/

    private Member saveOrUpdate(OAuthAttributes attributes) {
        try {
            Member member = memberService.getAccount(attributes.getEmail());
            member.setName(attributes.getName());
            member.setPicture(attributes.getPicture());
            return memberRepository.save(member);
        } catch (IllegalArgumentException e){
            Member member = Member.builder()
                    .email(attributes.getEmail())
                    .role(Role.USER)
                    .name(attributes.getName())
                    .picture(attributes.getPicture())
                    .build();
            return memberRepository.save(member);
        }
    }
}
