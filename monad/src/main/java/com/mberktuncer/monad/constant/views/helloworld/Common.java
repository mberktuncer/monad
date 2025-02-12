package com.mberktuncer.monad.constant.views.helloworld;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Common {

    TEXT_FIELD("Your Name"),
    SAY_HELLO_BUTTON_TEXT("Say Hello"),
    NOTIFICATION_TEXT("Hello ")
    ;
    private final String text;
}
