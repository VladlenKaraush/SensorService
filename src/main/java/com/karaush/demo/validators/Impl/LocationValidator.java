package com.karaush.demo.validators.Impl;

import com.karaush.demo.validators.annotations.LocationConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Basically @Range, but since you asked
 */
public class LocationValidator implements ConstraintValidator<LocationConstraint, Double> {

    private Double upperLimit;
    private Double lowerLimit;

    @Override
    public void initialize(LocationConstraint contactNumber) {
        this.upperLimit = contactNumber.upperLimit();
        this.lowerLimit = contactNumber.lowerLimit();
    }

    @Override
    public boolean isValid(Double contactField,
                           ConstraintValidatorContext cxt) {

        return contactField <= upperLimit && contactField >= lowerLimit;
    }
}
