package validator;

import daoimpl.BookDAOImpl;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import utility.CartInfo;

import java.util.Objects;

@Component
@Transactional
public class CartInfoValidator implements Validator {
    @Autowired
    BookDAOImpl bookDAO;

    @Override
    public boolean supports(Class<?> c) {
        return c == CartInfo.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        CartInfo cartInfo = (CartInfo) target;
        bookDAO.setSession();

        for (int i = 0; i < cartInfo.getBookList().size(); ++i) {
            if (PropertyAccessorFactory
                    .forBeanPropertyAccess(target)
                    .getPropertyValue("bookList[" + i + "].quantity") == null) {
                errors.rejectValue("bookList[" + i + "].quantity", "Number.cartInfo");
            } else if (cartInfo.getBookList().get(i).getQuantity() >
                    bookDAO.getById(cartInfo.getBookList().get(i).getId()).getAvailableCount()) {
                errors.rejectValue("bookList[" + i + "].quantity", "Available.cartInfo");
            }
        }
    }
}
