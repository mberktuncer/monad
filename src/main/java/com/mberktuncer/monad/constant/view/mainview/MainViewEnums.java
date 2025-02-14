package com.mberktuncer.monad.constant.view.mainview;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MainViewEnums {
    HEADER_LAYOUT_ID("header"),

    DRAWER_THEME("spacing-s"),
    DRAWER_LAYOUT_ID("logo"),
    DRAWER_LAYOUT_H1("Monad"),

    MENU_ID("tabs"),

    TAB_HELLO_WORLD("Hello World"),
    TAB_EMPLOYEES("Employee"),
    TAB_ABOUT("About")
    ;
    private final String text;
}
