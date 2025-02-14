package com.mberktuncer.monad.constant.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ConfirmMessages {
    SAVED_EMPLOYEE("Employee successfully saved."),
    DELETED_EMPLOYEE("Employee successfully deleted."),
    UPDATED_EMPLOYEE("Employee successfully updated.");
    
    private final String text;
}
