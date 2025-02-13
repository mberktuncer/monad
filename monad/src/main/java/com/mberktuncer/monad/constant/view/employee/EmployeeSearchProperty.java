package com.mberktuncer.monad.constant.view.employee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmployeeSearchProperty {
    PLACEHOLDER_TEXT("Search by name.."),
    FIELD_WIDTH("300px"),

    ;

    private final String data;
}
