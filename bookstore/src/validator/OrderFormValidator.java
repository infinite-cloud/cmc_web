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
import java.text.ParseException;
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
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        Date date = null;

        try {
            date = new Date(format.parse(deliveryDate).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assert date != null;

        if (deliveryDate == null) {
            errors.rejectValue("deliveryDate", "NotEmpty.orderForm");
        } else {
            if (date.getTime() <
                    Calendar.getInstance().getTime().getTime()) {
                errors.rejectValue("deliveryDate", "Date.orderForm");
            }
        }

        wrapper.setPropertyValue("deliveryDate", date.toString());
    }
}
