package com.techinterview.edtsbackend.controller.employee;

import com.techinterview.edtsbackend.controller.employee.data.*;
import com.techinterview.edtsbackend.helper.ErrorCode;
import com.techinterview.edtsbackend.helper.apiResponse.Response;
import com.techinterview.edtsbackend.service.employee.EmployeeService;
import com.techinterview.edtsbackend.service.employee.data.CreateEmployeeSpec;
import com.techinterview.edtsbackend.service.employee.data.UpdateEmployeeSpec;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Response<CreateEmployeeResp>> create(@Valid @RequestBody CreateEmployeeRequest spec) {
        var createResp = employeeService.create(
                new CreateEmployeeSpec(
                        spec.id(),
                        spec.name(),
                        spec.gradeId(),
                        spec.salary()
                )
        );

        if (createResp.errorCode() != null) {
            if (createResp.errorCode() == ErrorCode.EMPLOYEE_DUPLICATE) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Response.error(ErrorCode.EMPLOYEE_DUPLICATE, "User duplicate"));
            }
        }

        var resp = new CreateEmployeeResp(BigInteger.valueOf(createResp.data().getId().longValue()));

        return ResponseEntity.ok(Response.success(resp));
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<UpdateEmployeeResp>> update(@PathVariable BigInteger id, @Valid @RequestBody UpdateEmployeeRequest spec) {
        var updateResp = employeeService.update(id, new UpdateEmployeeSpec(
                spec.id(),
                spec.name(),
                spec.gradeId(),
                spec.salary()
        ));

        if (updateResp.errorCode() != null) {
            switch (updateResp.errorCode()) {
                case EMPLOYEE_DUPLICATE -> {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(
                            Response.error(
                                    updateResp.errorCode(), "Employee Duplicate"
                            )
                    );
                }
                case EMPLOYEE_NOT_FOUND -> {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                            Response.error(
                                    updateResp.errorCode(), "Employee not found"
                            )
                    );
                }
            }
        }


        var resp = new UpdateEmployeeResp(updateResp.data().getId());

        return ResponseEntity.ok(Response.success(resp));
    }


    @GetMapping("{id}")
    public ResponseEntity<Response<EmployeeResponse>> getById(@PathVariable BigInteger id) {
        final var employee = employeeService.getById(id).orElse(null);

        if (employee == null) {
            Response<EmployeeResponse> resp = Response.error(ErrorCode.EMPLOYEE_NOT_FOUND, "Employee not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        }

        var resp = new EmployeeResponse(
                employee.getId(),
                employee.getUser().getName(),
                employee.getSalary(),
                employee.getGradeId()
        );

        return ResponseEntity.ok(Response.success(resp));
    }

    @GetMapping
    public ResponseEntity<Response<List<EmployeeResponse>>> paginate(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int limit) {


        var employees = employeeService.paginate(PageRequest.of(page-1, limit));

        return ResponseEntity.ok(
                Response.success(
                        employees.stream().map((value) -> new EmployeeResponse(value.getId(), value.getUser().getName(), value.getSalary(), value.getGradeId())).toList()
                )
        );
    }
}
