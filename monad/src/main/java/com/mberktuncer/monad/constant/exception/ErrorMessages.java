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
    ;
    private final String text;
}
