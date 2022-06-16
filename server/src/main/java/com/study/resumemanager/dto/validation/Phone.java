package com.study.resumemanager.dto.validation;

import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Phone {
    String message() default "Phone number should be valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}