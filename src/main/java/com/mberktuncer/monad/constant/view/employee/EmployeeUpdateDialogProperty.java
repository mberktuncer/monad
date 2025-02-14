package com.mberktuncer.monad.constant.view.employee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmployeeUpdateDialogProperty {
    DIALOG_TITLE("Update Employee");
    
    private final String text;
} 