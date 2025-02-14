package com.mberktuncer.monad.constant.view.helloworld;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HelloWorldText {
    NAME_PLACEHOLDER("Your Name"),
    BUTTON_TEXT("Say Hello"),
    GREETING_PREFIX("Hello "),
    ;
    private final String text;
}
