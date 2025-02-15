package com.mberktuncer.monad.constant.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileNames {
    EMPLOYEES_FILE("src/main/resources/employees.txt"),
    ADDITIONAL_EMPLOYEES_FILE("src/main/resources/additional_employees.txt");

    private final String filePath;
}
