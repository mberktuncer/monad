package com.mberktuncer.monad.constant.views.employee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GridData {

    GRID_HEIGHT_SIZE("400px"),

    COLUMN_IDENTITY_NO_TEXT("TC No"),
    COLUMN_IDENTITY_NO_WIDTH_SIZE("150px"),

    COLUMN_NAME_TEXT("Ad"),
    COLUMN_LAST_NAME_TEXT("Soyad")
    ;

    private final String data;

}
