package hillelee.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by JavaEE on 30.12.2017.
 */
public class LatinNameValidator implements ConstraintValidator<LatinName, String> {

    @Override
    public void initialize(LatinName constraintAnnotation) {
        // здесь можно вытащить параметр аннотации
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null || value.isEmpty()) return true;

        return value.contains("um") || value.contains("us");
    }
}
