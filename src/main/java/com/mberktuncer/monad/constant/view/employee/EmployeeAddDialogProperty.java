package com.mberktuncer.monad.constant.view.employee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmployeeAddDialogProperty {
    DIALOG_TITLE("Add New Employee"),

    DIALOG_SAVE("Save"),
    DIALOG_CANCEL("Cancel")
    ;
    private final String text;
}
