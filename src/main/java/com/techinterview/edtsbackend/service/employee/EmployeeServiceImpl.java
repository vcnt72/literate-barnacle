package com.techinterview.edtsbackend.service.employee;

import com.techinterview.edtsbackend.entity.Employee;
import com.techinterview.edtsbackend.entity.User;
import com.techinterview.edtsbackend.helper.ErrorCode;
import com.techinterview.edtsbackend.repository.EmployeeRepository;
import com.techinterview.edtsbackend.service.employee.data.CreateEmployeeResp;
import com.techinterview.edtsbackend.service.employee.data.CreateEmployeeSpec;
import com.techinterview.edtsbackend.service.employee.data.UpdateEmployeeResp;
import com.techinterview.edtsbackend.service.employee.data.UpdateEmployeeSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    @Override
    public CreateEmployeeResp create(CreateEmployeeSpec spec) {

        var checkEmployee = employeeRepository.findById(spec.id()).orElse(null);

        if (checkEmployee != null) {
            return new CreateEmployeeResp(null, ErrorCode.EMPLOYEE_DUPLICATE);
        }

        var user = new User(spec.name());

        var employee = new Employee(
                spec.id(),
                spec.salary(),
                spec.gradeId(),
                user
        );

        var createResp = employeeRepository.save(employee);

        return new CreateEmployeeResp(createResp, null);
    }

    @Override
    public UpdateEmployeeResp update(BigInteger id, UpdateEmployeeSpec spec) {
        var employee = employeeRepository.findById(id).orElse(null);

        if (employee == null) {
            return new UpdateEmployeeResp(null, ErrorCode.EMPLOYEE_NOT_FOUND);
        }

        var checkEmployee = employeeRepository.findById(spec.id());

        if (checkEmployee.isPresent() && !id.equals(spec.id())) {
            return new UpdateEmployeeResp(null, ErrorCode.EMPLOYEE_DUPLICATE);
        }

        employee.setGradeId(spec.gradeId());
        employee.setId(spec.id());
        employee.setSalary(spec.salary());
        employee.getUser().setName(spec.name());

        return new UpdateEmployeeResp(employeeRepository.save(employee), null);
    }

    @Override
    public Optional<Employee> getById(BigInteger id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Page<Employee> paginate(Pageable page) {
        return employeeRepository.findAll(page);
    }
}
