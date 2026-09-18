package com.ems.ems.controller;

import com.ems.ems.entity.Employee;
import com.ems.ems.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/employee")
public class EmployeeProfileController {

    private final EmployeeService employeeService;

    public EmployeeProfileController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {

        if (principal == null) {
            return "redirect:/employee/login";
        }

        String email = principal.getName();
        Employee emp = employeeService.getEmployeeByEmail(email);

        if (emp == null) {
            model.addAttribute("error", "Employee not found for email: " + email);
            return "error";   // or redirect to login
        }

        model.addAttribute("employee", emp);
        model.addAttribute("employeeName", emp.getName());
        model.addAttribute("activePage", "profile");

        return "employee/profile";
    }

}
