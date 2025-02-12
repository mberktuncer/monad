package com.mberktuncer.monad.constant.views.employee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchField {

    SEARCH_FIELD_TEXT("İsim ile ara.."),
    SEARCH_FIELD_WIDTH("300px"),

    ;

    private final String data;
}
