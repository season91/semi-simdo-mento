package simdo.module.member.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import simdo.module.member.MemberRepository;
import simdo.module.member.form.UpdateForm;

@Component
@RequiredArgsConstructor
public class UpdateFormValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(UpdateForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UpdateForm updateForm = (UpdateForm) target;

        if (memberRepository.existsByName(updateForm.getName())){
            errors.rejectValue("name","invalid.name",new Object[]{updateForm.getName()},"이미 사용중인 이름입니다.");
        }
    }
}
