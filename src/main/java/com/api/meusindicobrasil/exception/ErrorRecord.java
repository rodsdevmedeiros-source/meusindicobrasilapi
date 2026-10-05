package com.api.meusindicobrasil.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorRecord(
        int errorCode,
        String errorMessage,
        Map<String, String> errorsDetails
) {
}
