package com.salary_service.client;

import java.util.List;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.salary_service.entity.EmployeeDTO;

//import com.employee_managemnet.employee_dto.EmployeeDTO;

//import com.employee_managemnet.employee_dto.Employee;

@FeignClient(name = "EMPLOYEE-SERVICE")
public interface EmployeeClient {

	   @GetMapping("/employee/{eid}")
	    EmployeeDTO getEmployeeById(@PathVariable("eid") long id);

	    @GetMapping("/employee/alle")
	    List<EmployeeDTO> getAllEmployees();  // Add this to fetch all employees

    // You can also create other methods to fetch multiple employees or other data
}
