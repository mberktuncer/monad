package com.mberktuncer.monad.constant.view.employee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmployeeSearchProperty {
    PLACEHOLDER_TEXT("İsim ile ara.."),
    FIELD_WIDTH("300px"),

    ;

    private final String data;
}
