package com.salary_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;

import com.salary_service.client.EmployeeClient;
import com.salary_service.entity.EmployeeDTO;
//import com.employee_managemnet.employee_dto.EmployeeDTO;
//import com.employee_managemnet.employee_dto.Employee;
import com.salary_service.entity.Salary;
//import com.salary_service.entity.SalaryDTO;
import com.salary_service.entity.SalaryListWrapper;
import com.salary_service.repo.SalaryRepo;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;

import com.salary_service.service.SalaryServiceImpl;

//@RestController
@Controller
@RequestMapping("/salary")
public class SalaryController {
	
	@Autowired
	SalaryServiceImpl salimpl;
	@Autowired
	EmployeeClient employeeClient;
	@Autowired
	SalaryRepo salaryRepository;
//	@GetMapping("/addsalary")
//	public String addSalary(Model model)
//	{ 
//		model.addAttribute("salary",new Salary());
//		return "viewsalarys";
//	}
	
//	@PostMapping("/savesalary")
//	public String saveSalary(@ModelAttribute Salary sal)
//	{
//		salimpl.addSalary(sal);
//		return "redirect:/salary/all";
//	}

	@GetMapping("/all")
	public String getAllEmployeeSalaries(Model model) {

	    List<EmployeeDTO> employees = employeeClient.getAllEmployees();
	    List<Salary> salaryList = new ArrayList<>();

	    for (EmployeeDTO employee : employees) {
	        Salary salary = salaryRepository.findByEid(employee.getEid());

	        if (salary == null) {
	            salary = new Salary();
	            salary.setEid(employee.getEid());
	            salary.setEname(employee.getEname());
	            salary.setJan("Na");
	            salary.setFeb("Na");
	            salary.setMar("Na");
	            salary.setApr("Na");
	            salary.setMay("Na");
	            salary.setJune("Na");
	            salary.setJuly("Na");
	            salary.setAug("Na");
	            salary.setSep("Na");
	            salary.setOct("Na");
	            salary.setNov("Na");
	            salary.setDec("Na");
	            salary.setAdvancc("Na"); 
	        }

	        salaryList.add(salary);
	    }

	    SalaryListWrapper wrapper = new SalaryListWrapper();
	    wrapper.setSalaryList(salaryList);
	    model.addAttribute("salaryListWrapper", wrapper);
	    model.addAttribute("activePage", "salarys");
	    return "viewsalarys";
	}

//	
//	@GetMapping("/view")
//    public String showSalaryPage(Model model) {
//        SalaryListWrapper wrapper = new SalaryListWrapper();
//        wrapper.setSalaryList(salaryRepository.findAll());
//        model.addAttribute("salaryListWrapper", wrapper);
//        return "viewsalarys"; // Thymeleaf HTML file name
//    }

    @PostMapping("/savesalary")
    public String saveSalary(@ModelAttribute SalaryListWrapper salaryListWrapper) {
        salaryRepository.saveAll(salaryListWrapper.getSalaryList());
        return "redirect:/salary/all"; // Redirect to avoid form resubmission
    }
   
//    @DeleteMapping("/deleteslary/{id}")
//    public void deleteSalary(@PathVariable("id") int id)
//    {
//    	
//     salaryRepository.deleteById(id);
//     }
//    
//    @DeleteMapping("/deleteslary/{id}")
//    public ResponseEntity<String> deleteSalary(@PathVariable("id") int id) {
//        if (salaryRepository.existsById(id)) {
//            salaryRepository.deleteById(id);
//            return ResponseEntity.ok("Deleted");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Salary ID not found");
//        }
//    }

    
//    @DeleteMapping("/deleteslary/{id}")
//    public ResponseEntity<String> deleteSalary(@PathVariable("id") int id) {
//        if (salaryRepository.existsById(id)) {
//            try {
//                salaryRepository.deleteById(id);
//                return ResponseEntity.ok("Salary deleted");
//            } catch (Exception e) {
//                e.printStackTrace();
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                     .body("Failed to delete salary: " + e.getMessage());
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                                 .body("Salary not found");
//        }
//    }
    @DeleteMapping("/deleteSalary/{id}")
    public ResponseEntity<Void> deleteSalary(@PathVariable int id) {
        if (!salaryRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        salaryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
	
