package validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import utility.ItemForm;

@Component
public class ItemFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> c) {
        return c == ItemForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "value", "NotEmpty.itemForm");
    }
}
