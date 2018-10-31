package com.karaush.demo.validators.annotations;

import com.karaush.demo.validators.Impl.LocationValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LocationValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LocationConstraint
{

    String message() default "{com.karaush.demo.validators.annotations.LocationConstraint}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    double upperLimit();

    double lowerLimit();
}
