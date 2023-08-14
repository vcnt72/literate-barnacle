package com.techinterview.edtsbackend.controller.employee.data;

import jakarta.validation.constraints.*;

import java.math.BigInteger;

public record UpdateEmployeeRequest(
        @NotNull
        @Positive
        BigInteger id,

        @Size(min = 1, max = 40)
        @NotNull
        @NotBlank
        String name,
        @Min(1)
        @Max(3)
        @NotNull
        Integer gradeId,
        @NotNull
        @Positive
        BigInteger salary
) {
}
