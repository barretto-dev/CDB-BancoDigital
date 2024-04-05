package br.com.cdb.bancodigital.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { IsMaiorDeIdade.Validator.class } )
public @interface IsMaiorDeIdade {

    String message() default "pessoa com a data de nascimento informada é menor de idade";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<IsMaiorDeIdade, LocalDate>{

        private String message;

        @Override
        public void initialize(IsMaiorDeIdade constraintAnnotation) {
            this.message = constraintAnnotation.message();
        }
        @Override
        public boolean isValid(LocalDate dataNascimento, ConstraintValidatorContext constraintValidatorContext) {
            long anos = dataNascimento.until(LocalDate.now(), ChronoUnit.YEARS);
            return anos >= 18;
        }
    }

}
