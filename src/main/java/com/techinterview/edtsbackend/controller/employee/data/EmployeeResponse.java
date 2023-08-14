package com.techinterview.edtsbackend.controller.employee.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techinterview.edtsbackend.data.employee.EmployeeGrade;
import jakarta.annotation.Nonnull;

import java.math.BigDecimal;
import java.math.BigInteger;

public class EmployeeResponse {
    private final BigInteger id;

    private final String name;
    private final BigInteger salary;

    private final String gradeCode;

    private final BigDecimal totalBonus;

    public EmployeeResponse(
            @Nonnull
            BigInteger id,
            @Nonnull
            String name,
            @Nonnull
            BigInteger salary,
            @Nonnull
            Integer gradeId) {
        this.id = id;
        this.name = name;
        this.salary = salary;

        var employeeGrade = EmployeeGrade.getByGradeId(gradeId);
        this.gradeCode = employeeGrade.gradeId + ":" + EmployeeGrade.MANAGER.pretty();
        var bigDecSalary = new BigDecimal(this.salary);
        this.totalBonus = bigDecSalary.add(bigDecSalary.multiply(BigDecimal.valueOf(employeeGrade.bonus)));
    }

    public BigInteger getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigInteger getSalary() {
        return salary;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public BigDecimal getTotalBonus() {
        return totalBonus;
    }
}
