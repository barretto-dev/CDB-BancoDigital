package br.com.cdb.bancodigital.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ValoresPermitidos.Validator.class })
public @interface ValoresPermitidos {

    String message() default "Valores permitidos são: ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String propName();

    String[] values();

    class Validator implements ConstraintValidator<ValoresPermitidos, String> {
        private String propName;
        private String message;
        private List<String> allowable;

        @Override
        public void initialize(ValoresPermitidos constraintAnnotation) {
            this.propName = constraintAnnotation.propName();
            this.message = constraintAnnotation.message();
            this.allowable = Arrays.asList(constraintAnnotation.values());
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            Boolean valid = value == null || this.allowable.contains(value);

            if (!valid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message.concat(this.allowable.toString()))
                        .addPropertyNode(this.propName).addConstraintViolation();
            }
            return valid;
        }
    }
}
