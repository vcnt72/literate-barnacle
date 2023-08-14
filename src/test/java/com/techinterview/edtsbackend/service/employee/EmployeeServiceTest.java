package com.techinterview.edtsbackend.service.employee;


import com.techinterview.edtsbackend.entity.Employee;
import com.techinterview.edtsbackend.entity.User;
import com.techinterview.edtsbackend.helper.ErrorCode;
import com.techinterview.edtsbackend.repository.EmployeeRepository;
import com.techinterview.edtsbackend.service.employee.data.CreateEmployeeSpec;
import com.techinterview.edtsbackend.service.employee.data.UpdateEmployeeSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;


    @Test
    void create_shouldSuccessOnHappyPath_successAndErrorNull() {
        var create = new CreateEmployeeSpec(
                BigInteger.valueOf(10),
                "test",
                1,
                BigInteger.valueOf(753)
        );

        var employee = new Employee(
                BigInteger.valueOf(10),
                BigInteger.valueOf(753),
                1,
                new User("test")
        );

        Mockito.when(employeeRepository.findById(create.id())).thenReturn(Optional.empty());

        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenAnswer((value) -> value.getArguments()[0]);

        var resp = employeeService.create(create);

        Assertions.assertNull(resp.errorCode());
        Assertions.assertEquals(employee, resp.data());
    }


    @Test
    void create_shouldErrorWhenEmployeeAlreadyPresentInDB_errorCodeEmployeeDuplicate() {
        var create = new CreateEmployeeSpec(
                BigInteger.valueOf(10),
                "test",
                1,
                BigInteger.valueOf(753)
        );

        var employee = new Employee(
                BigInteger.valueOf(10),
                BigInteger.valueOf(753),
                1,
                new User("test")
        );

        Mockito.when(employeeRepository.findById(create.id())).thenReturn(Optional.of(employee));

        var resp = employeeService.create(create);

        Assertions.assertNull(resp.data());
        Assertions.assertEquals(ErrorCode.EMPLOYEE_DUPLICATE, resp.errorCode());
    }


    @Test
    void update_shouldErrorWhenEmployeeAlreadyPresentInDB_errorCodeEmployeeDuplicate() {
        var employeeId = BigInteger.valueOf(11);
        var employeeSpecId = BigInteger.valueOf(10);
        var update = new UpdateEmployeeSpec(
                employeeSpecId,
                "test",
                1,
                BigInteger.valueOf(753)
        );

        var employee = new Employee(
                employeeId,
                BigInteger.valueOf(753),
                1,
                new User("test")
        );

        var checkEmployeeRet = new Employee(
                employeeSpecId,
                BigInteger.valueOf(753),
                1,
                new User("test")
        );


        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        Mockito.when(employeeRepository.findById(employeeSpecId)).thenReturn(Optional.of(checkEmployeeRet));

        var resp = employeeService.update(employeeId, update);

        Assertions.assertNull(resp.data());
        Assertions.assertEquals(ErrorCode.EMPLOYEE_DUPLICATE, resp.errorCode());
    }


    @Test
    void update_shouldErrorWhenEmployeeNotFound_errorCodeEmployeeNotFound() {
        var employeeId = BigInteger.valueOf(11);
        var employeeSpecId = BigInteger.valueOf(10);

        var update = new UpdateEmployeeSpec(
                employeeSpecId,
                "test",
                1,
                BigInteger.valueOf(753)
        );

        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        var resp = employeeService.update(employeeId, update);

        Assertions.assertNull(resp.data());
        Assertions.assertEquals(ErrorCode.EMPLOYEE_NOT_FOUND, resp.errorCode());
    }

    @Test
    void update_shouldSuccessOnHappyPath_errorNullAndSuccess() {
        var employeeId = BigInteger.valueOf(11);
        var employeeSpecId = BigInteger.valueOf(10);
        var update = new UpdateEmployeeSpec(
                employeeSpecId,
                "test",
                1,
                BigInteger.valueOf(753)
        );

        var employee = new Employee(
                employeeId,
                BigInteger.valueOf(753),
                1,
                new User("test")
        );

        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        Mockito.when(employeeRepository.findById(employeeSpecId)).thenReturn(Optional.empty());
        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenAnswer((value) -> value.getArguments()[0]);

        var resp = employeeService.update(employeeId, update);

        Assertions.assertNull(resp.errorCode());
        Assertions.assertEquals(
                new Employee(
                        employeeSpecId,
                        BigInteger.valueOf(753),
                        1, new User("test")
                ), resp.data()
        );
    }

    @Test
    void getById_errorCodeEmployeeNotFound_emptyOptional() {
        var employeeId = BigInteger.valueOf(11);

        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        var resp = employeeService.getById(employeeId);

        Assertions.assertEquals(Optional.empty(), resp);
    }

    @Test
    void getById_successOnHappyPath_filledOptional() {
        var employeeId = BigInteger.valueOf(11);

        var employee = new Employee(
                employeeId,
                BigInteger.valueOf(753),
                1,
                new User("test")
        );

        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));


        var resp = employeeService.getById(employeeId);

        Assertions.assertEquals(Optional.of(employee), resp);
    }

    @Test
    void paginate_successOnHappyPath_returnEmployees() {
        var page = PageRequest.of(0, 10);
        ArrayList<Employee> employeeList = new ArrayList<>();

        var employee = new Employee(
                BigInteger.valueOf(1),
                BigInteger.valueOf(753),
                1,
                new User("test")
        );

        employeeList.add(employee);
        Mockito.when(employeeRepository.findAll(page)).thenReturn(new PageImpl<>(employeeList));

        var resp = employeeService.paginate(page);

        Assertions.assertEquals(new PageImpl<>(employeeList), resp);
    }
}
