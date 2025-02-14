package com.mberktuncer.monad.constant.view.employee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmployeeSaveProperty {
    IDENTITY_PLACEHOLDER("Enter identity number"),
    NAME_PLACEHOLDER("Enter first name"),
    LASTNAME_PLACEHOLDER("Enter last name"),

    BUTTON_ADD("Add New Employee")
    ;
    private final String text;
}
