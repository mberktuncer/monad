package com.mberktuncer.monad.core.validator;

import com.mberktuncer.monad.constant.exception.ErrorMessages;
import com.mberktuncer.monad.exception.EmployeeValidationException;
import com.mberktuncer.monad.model.api.CreateEmployeeRequest;

public class EmployeeValidator {
    
    public static void validateEmployee(CreateEmployeeRequest employee) {
        validateIdentityNumber(employee.getIdentityNumber());
        validateName(employee.getFirstName(), "First name");
        validateName(employee.getLastName(), "Last name");
    }

    private static void validateIdentityNumber(String identityNumber) {
        if (identityNumber == null || identityNumber.trim().isEmpty()) {
            throw new EmployeeValidationException(ErrorMessages.EMPTY_IDENTITY.getText());
        }
        if (identityNumber.length() != 11) {
            throw new EmployeeValidationException(ErrorMessages.MISSING_IDENTITY_DIGIT.getText());
        }
        if (!identityNumber.matches("\\d+")) {
            throw new EmployeeValidationException(ErrorMessages.ONLY_DIGIT.getText());
        }
    }

    private static void validateName(String name, String fieldName) {
        if (name == null || name.trim().isEmpty()) {
            throw new EmployeeValidationException(fieldName + ErrorMessages.EMPTY_NAME);
        }
        if (name.length() < 2) {
            throw new EmployeeValidationException(fieldName + ErrorMessages.SHORT_NAME);
        }
    }
} 