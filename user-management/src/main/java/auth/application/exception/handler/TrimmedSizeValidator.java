
package auth.application.exception.handler;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TrimmedSizeValidator implements ConstraintValidator<TrimmedSize, String> {

    private int min;
    private int max;

    @Override
    public void initialize(TrimmedSize trimmedSize) {
        min = trimmedSize.min();
        max = trimmedSize.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.length() == 0) {
            return false;
        }
        value = value.trim();
        if(value.length() < min){
            System.out.println("trimmed was called");
            return false;
        }
        return value.length() < max;
    }
}
