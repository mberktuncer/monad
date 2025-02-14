package com.mberktuncer.monad.constant.view.about;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AboutMessage {
    APPLICATION_ABOUT("This is an application using Vaadin and Spring Boot. Developed as Monad Yazılım Interview Project.");

    private final String message;
}
