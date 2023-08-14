package com.techinterview.edtsbackend.service.employee.data;

import java.math.BigInteger;

public record UpdateEmployeeSpec(BigInteger id, String name, Integer gradeId, BigInteger salary) {
}
