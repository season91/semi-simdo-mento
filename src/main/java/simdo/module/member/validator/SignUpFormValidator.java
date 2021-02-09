package simdo.module.member.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import simdo.module.member.MemberRepository;
import simdo.module.member.form.SignUpForm;

@Component
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(SignUpForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpForm signUpForm = (SignUpForm) target;

        if (memberRepository.existsByName(signUpForm.getName())){
            errors.rejectValue("name","invalid.name",new Object[]{signUpForm.getName()},"이미 사용중인 이름입니다.");
        }

    }
}
