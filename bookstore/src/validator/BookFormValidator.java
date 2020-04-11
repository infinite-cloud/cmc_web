package validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import utility.BookForm;

import java.util.Calendar;

@Component
public class BookFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> c) {
        return c == BookForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        BookForm bookForm = (BookForm) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "bookName", "NotEmpty.bookForm");
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "description", "NotEmpty.bookForm");
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "genreId", "NotEmpty.bookForm");
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "coverTypeId", "NotEmpty.bookForm");
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "publisherId", "NotEmpty.bookForm");

        boolean hasAuthor = false;

        for (Long authorId : bookForm.getBookAuthors()) {
            if (authorId != null) {
                hasAuthor = true;
                break;
            }
        }

        if (!hasAuthor) {
            errors.rejectValue("bookAuthors", "Author.bookForm");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "pageCount", "NotEmpty.bookForm");
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "publicationDate", "NotEmpty.bookForm");
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "availableCount", "NotEmpty.bookForm");
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "imageName", "NotEmpty.bookForm");
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "bookPrice", "NotEmpty.bookForm");

        if (bookForm.getPageCount() != null && bookForm.getPageCount() <= 0) {
            errors.rejectValue("pageCount", "Positive.bookForm");
        }

        if (bookForm.getPublicationDate() != null &&
                bookForm.getPublicationDate().getTime() >=
                        Calendar.getInstance().getTime().getTime()) {
            errors.rejectValue("publicationDate", "Date.bookForm");
        }

        if (bookForm.getAvailableCount() != null && bookForm.getAvailableCount() < 0) {
            errors.rejectValue("availableCount", "NotNegative.bookForm");
        }

        if (bookForm.getBookPrice() != null && bookForm.getBookPrice() <= 0.0) {
            errors.rejectValue("bookPrice", "Positive.bookForm");
        }
    }
}
