package validator;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import utility.OrderForm;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Component
public class OrderFormValidator implements Validator {
    @Override
    public boolean supports(Class <?> c) {
        return c == OrderForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "deliveryAddress", "NotEmpty.orderForm");

        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(target);

        String deliveryDate = (String) wrapper.getPropertyValue("deliveryDate");

        if (deliveryDate == null) {
            errors.rejectValue("deliveryDate", "NotEmpty.orderForm");
        } else {
            boolean valid = true;

            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateFormat.setLenient(false);
                dateFormat.parse(deliveryDate);
            } catch (Exception e) {
                errors.rejectValue("deliveryDate", "Date.orderForm");
                valid = false;
            }

            if (valid && Date.valueOf(deliveryDate).getTime() <
                    Calendar.getInstance().getTime().getTime()) {
                errors.rejectValue("deliveryDate", "Date.orderForm");
            }
        }
    }
}
