package com.ems.ems.controller;

import com.ems.ems.entity.Employee;
import com.ems.ems.entity.Salary;
import com.ems.ems.service.EmployeeService;
import com.ems.ems.service.SalaryService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/employee")
public class EmployeeSalaryController {

    private final SalaryService salaryService;
    private final EmployeeService employeeService;

    public EmployeeSalaryController(SalaryService salaryService, EmployeeService employeeService) {
        this.salaryService = salaryService;
        this.employeeService = employeeService;
    }

    @GetMapping("/salary")
    public String salaryHistory(
            @RequestParam(defaultValue = "0") int page,
            Model model,
            Principal principal
    ) {
        String email = principal.getName();
        Employee emp = employeeService.getEmployeeByEmail(email);

        int size = 5;

        Page<Salary> salaryPage = salaryService.getSalaryByEmpId(emp.getId(), page, size);

        model.addAttribute("employeeName", emp.getName());
        model.addAttribute("activePage", "salary");

        model.addAttribute("salaries", salaryPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", salaryPage.getTotalPages());

        return "employee/salary";
    }
}
