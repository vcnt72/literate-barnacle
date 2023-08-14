package com.techinterview.edtsbackend.service.employee;

import com.techinterview.edtsbackend.entity.Employee;
import com.techinterview.edtsbackend.service.employee.data.CreateEmployeeResp;
import com.techinterview.edtsbackend.service.employee.data.CreateEmployeeSpec;
import com.techinterview.edtsbackend.service.employee.data.UpdateEmployeeResp;
import com.techinterview.edtsbackend.service.employee.data.UpdateEmployeeSpec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    CreateEmployeeResp create(CreateEmployeeSpec spec);

    UpdateEmployeeResp update(BigInteger id, UpdateEmployeeSpec spec);

    Optional<Employee> getById(BigInteger id);

    Page<Employee> paginate(Pageable page);
}
