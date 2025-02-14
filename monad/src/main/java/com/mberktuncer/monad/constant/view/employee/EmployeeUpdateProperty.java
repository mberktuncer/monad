package com.mberktuncer.monad.constant.view.employee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmployeeUpdateProperty {
    BUTTON_UPDATE("Update"),
    CANCEL_BUTTON("Cancel");
    
    private final String text;
} 