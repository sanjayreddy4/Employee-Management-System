package com.ems.ems.controller;

import com.ems.ems.entity.Employee;
import com.ems.ems.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/employee")
public class EmployeeSettingController {

    private final EmployeeService employeeService;

    public EmployeeSettingController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Setting Page
    @GetMapping("/setting")
    public String settingPage(Model model, Principal principal) {

        String email = principal.getName();
        Employee emp = employeeService.getEmployeeByEmail(email);

        model.addAttribute("employeeName", emp.getName());
        model.addAttribute("activePage", "setting");

        return "employee/setting";
    }

    // Change Password
    @PostMapping("/change-password")
    public String changePassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            Principal principal,
            Model model
    ) {

        String email = principal.getName();
        Employee emp = employeeService.getEmployeeByEmail(email);

        model.addAttribute("employeeName", emp.getName());
        model.addAttribute("activePage", "setting");

        // confirm password check
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "New password and confirm password do not match!");
            return "employee/setting";
        }

        // strong password validation
        if (!isStrongPassword(newPassword)) {
            model.addAttribute("error",
                    "Password must be at least 8 characters and include 1 uppercase, 1 number, 1 special character!");
            return "employee/setting";
        }

        //  call service
        String result = employeeService.changePassword(email, oldPassword, newPassword);

        if (result.equals("OLD_PASSWORD_WRONG")) {
            model.addAttribute("error", "Old password is incorrect!");
            return "employee/setting";
        }

        model.addAttribute("success", "Password changed successfully ");
        return "employee/setting";
    }

    // Password rule:
    // min 8 chars, 1 uppercase, 1 digit, 1 special char
    private boolean isStrongPassword(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }
}
