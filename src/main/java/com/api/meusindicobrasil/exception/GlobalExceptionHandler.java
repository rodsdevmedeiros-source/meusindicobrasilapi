package com.api.meusindicobrasil.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tools.jackson.databind.exc.InvalidFormatException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorRecord> handleNotFoundException(NotFoundException ex) {
        var errorRecord = new ErrorRecord(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorRecord);
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ErrorRecord> handleRegraException(RegraDeNegocioException ex) {
        var errorRecord = new ErrorRecord(HttpStatus.CONFLICT.value(), ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorRecord);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorRecord> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                }
        );
        var errorRecordResponse = new ErrorRecord(HttpStatus.BAD_REQUEST.value(), "Error: Validation failed", errors);
        //logger.error("MethodArgumentNotValidException message: {} ", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRecordResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorRecord> handleInvalidFormatException(
            HttpMessageNotReadableException ex) {

        Map<String, String> errors = new HashMap<>();

        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException ifx) {

            Class<?> targetType = ifx.getTargetType();

            if (targetType != null && targetType.isEnum()) {

                String fieldName = "campo";

                if (!ifx.getPath().isEmpty()) {
                    fieldName = ifx.getPath()
                            .get(ifx.getPath().size() - 1)
                            .getPropertyName();
                }

                String errorMessage =
                        "Valor '" + ifx.getValue() +
                                "' inválido para o campo " + fieldName;

                errors.put(fieldName, errorMessage);
            }
        }

        var errorRecordResponse = new ErrorRecord(
                HttpStatus.BAD_REQUEST.value(),
                "Error: Invalid enum value",
                errors
        );

        logger.error(
                "HttpMessageNotReadableException message: {}",
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorRecordResponse);
    }
}
