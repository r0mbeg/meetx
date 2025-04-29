package com.proffen.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Error response model")
public record ErrorResponse(
        @Schema(description = "List of errors")
        List<FieldError> errors,

        @Schema(description = "Timestamp of the error in milliseconds since epoch", example = "1745947401462")
        long timestamp
) {
    @Schema(description = "Single field validation error")
    public record FieldError(
            @Schema(description = "Name of the field with error", example = "username")
            String fieldName,

            @Schema(description = "Error message describing the problem", example = "Username must be 4-32 characters")
            String error
    ) {}
}


