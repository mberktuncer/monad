package com.mberktuncer.monad.constant.view.employee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmployeeGridProperty {
    GRID_HEIGHT("400px"),

    IDENTITY_COLUMN_HEADER("Identity Number"),
    IDENTITY_COLUMN_WIDTH("150px"),

    NAME_COLUMN_HEADER("First Name"),
    LASTNAME_COLUMN_HEADER("Last Name")
    ;

    private final String data;

}
