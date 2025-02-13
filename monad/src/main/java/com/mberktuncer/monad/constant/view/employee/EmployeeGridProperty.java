package com.mberktuncer.monad.constant.view.employee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmployeeGridProperty {
    GRID_HEIGHT("400px"),

    IDENTITY_COLUMN_HEADER("TC No"),
    IDENTITY_COLUMN_WIDTH("150px"),

    NAME_COLUMN_HEADER("Ad"),
    LASTNAME_COLUMN_HEADER("Soyad")
    ;

    private final String data;

}
