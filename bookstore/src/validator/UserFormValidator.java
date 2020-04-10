package validator;

import daoimpl.AccountDAOImpl;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import utility.UserForm;

@Component
@Transactional
public class UserFormValidator implements Validator {
    @Autowired
    private AccountDAOImpl accountDAO;

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

        if (userForm.getNeedsInitialValidation()) {
            ValidationUtils.rejectIfEmptyOrWhitespace(
                    errors, "password", "NotEmpty.userForm");
            ValidationUtils.rejectIfEmptyOrWhitespace(
                    errors, "repeatedPassword", "NotEmpty.userForm");
        }

        if (!userForm.geteMail().trim().isEmpty() && !emailValidator.isValid(userForm.geteMail())) {
            errors.rejectValue("eMail", "Pattern.userForm.eMail");
        } else if (!userForm.geteMail().trim().isEmpty()) {
            accountDAO.setSession();

            if (accountDAO.getByEMail(userForm.geteMail()) != null) {
                errors.rejectValue("eMail", "Exists.userForm.eMail");
            }
        }

        if (!userForm.getPhoneNumber().trim().isEmpty()) {
            if (!userForm.getPhoneNumber().matches("\\+\\d{11}")) {
                errors.rejectValue("phoneNumber", "Pattern.userForm.phoneNumber");
            }
        }

        if (!userForm.getPassword().equals(userForm.getRepeatedPassword())) {
            errors.rejectValue("repeatedPassword", "Equals.userForm.password");
        }
    }
}
