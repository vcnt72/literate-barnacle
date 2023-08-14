package com.techinterview.edtsbackend.service.employee.data;

import com.techinterview.edtsbackend.entity.Employee;
import com.techinterview.edtsbackend.helper.ErrorCode;

public record CreateEmployeeResp(Employee data, ErrorCode errorCode) {}
