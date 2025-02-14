package com.mberktuncer.monad.constant.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ConfirmMessages {
    SAVED_EMPLOYEE("Employee added successfully!"),
    DELETED_EMPLOYEE("Selected employees were successfully deleted");
    private final String text;
}
