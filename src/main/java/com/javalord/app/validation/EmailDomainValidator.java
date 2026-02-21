package com.javalord.app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmailDomainValidator implements ConstraintValidator<NonDisposableEmail, String> {

    private final Set<String> blocked;

    public EmailDomainValidator(@Value("{email.domains}") List<String> domains) {
        this.blocked = new HashSet<>(domains);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || !email.contains("@")) {
            return true;
        }

        final int atIndex = email.indexOf("@");
        final int dotIndex = email.indexOf(".");
        final String domain = email.substring(atIndex, dotIndex);


        return !this.blocked.contains(domain);
    }
}
