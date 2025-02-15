package com.mberktuncer.monad.constant.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessages {
    DUPLICATE_EMPLOYEE("An employee with this identity number already exists"),

    EMPTY_IDENTITY("Identity number cannot be empty"),
    MISSING_IDENTITY_DIGIT("Identity number must be 11 digits"),
    ONLY_DIGIT("Identity number must contain only digits"),

    EMPTY_NAME(" cannot be empty"),
    SHORT_NAME(" must be at least 2 characters"),

    UNEXPECTED("An unexpected error occurred"),

    EMPLOYEE_NOT_FOUND("Employee not found."),

    ADDITIONAL_FILE_EMPTY("Additional employees file is empty"),
    NO_MORE_EMPLOYEES("No more employees to add"),
    INVALID_FILE_FORMAT("Invalid file format"),
    FILE_READ_ERROR("File reading error: %s");

    private final String text;
}
