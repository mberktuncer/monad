package com.mberktuncer.monad.constant.view.employee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmployeeDeleteProperty {

    CONFIRM_DIALOG_TITLE("Confirm Delete"),

    DELETE_BUTTON("Delete"),
    CANCEL_BUTTON("Cancel"),

    BUTTON_DELETE("Delete Selected"),

    ;
    private final String text;
}
