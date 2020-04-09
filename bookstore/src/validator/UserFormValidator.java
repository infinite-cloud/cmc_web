package validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import utility.UserForm;

@Component
public class UserFormValidator implements Validator {
    private EmailValidator emailValidator = EmailValidator.getInstance();

    @Override
    public boolean supports(Class<?> c) {
        return c == UserForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserForm userForm = (UserForm) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "userName", "NotEmpty.userForm");
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "eMail", "NotEmpty.userForm");
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "password", "NotEmpty.userForm");
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "repeatedPassword", "NotEmpty.userForm");

        if (!userForm.geteMail().equals("") && !emailValidator.isValid(userForm.geteMail())) {
            errors.rejectValue("eMail", "Pattern.userForm.eMail");
        }

        if (!userForm.getPhoneNumber().equals("")) {
            if (!userForm.getPhoneNumber().matches("\\+\\d{11}")) {
                errors.rejectValue("phoneNumber", "Pattern.userForm.phoneNumber");
            }
        }

        if (!userForm.getPassword().equals(userForm.getRepeatedPassword())) {
            errors.rejectValue("repeatedPassword", "Equals.userForm.password");
        }
    }
}
