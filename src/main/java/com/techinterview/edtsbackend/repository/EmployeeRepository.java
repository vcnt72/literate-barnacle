package com.techinterview.edtsbackend.repository;

import com.techinterview.edtsbackend.entity.Employee;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, BigInteger> {
    @Override
    @Nonnull
    Optional<Employee> findById(@Nonnull BigInteger id);
}