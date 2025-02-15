package com.mberktuncer.monad.util;

import com.mberktuncer.monad.constant.exception.ErrorMessages;
import com.mberktuncer.monad.model.api.CreateEmployeeRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class EmployeeFileReader {
    
    public static CreateEmployeeRequest readEmployeeFromFile(String filePath, int lineIndex) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            if (lines.isEmpty()) {
                throw new RuntimeException(ErrorMessages.ADDITIONAL_FILE_EMPTY.getText());
            }

            if (lineIndex >= lines.size()) {
                throw new RuntimeException(ErrorMessages.NO_MORE_EMPLOYEES.getText());
            }

            String line = lines.get(lineIndex);
            return parseEmployeeLine(line);
        } catch (IOException e) {
            throw new RuntimeException(String.format(
                ErrorMessages.FILE_READ_ERROR.getText(), 
                e.getMessage()
            ));
        }
    }

    private static CreateEmployeeRequest parseEmployeeLine(String line) {
        String[] parts = line.split(",");
        if (parts.length != 3) {
            throw new RuntimeException(ErrorMessages.INVALID_FILE_FORMAT.getText());
        }

        return new CreateEmployeeRequest(
            parts[0].trim(),
            parts[1].trim(),
            parts[2].trim()
        );
    }
} 