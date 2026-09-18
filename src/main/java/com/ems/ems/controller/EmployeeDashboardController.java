package com.ems.ems.controller;

import com.ems.ems.entity.Employee;
import com.ems.ems.service.EmployeeService;
import com.ems.ems.service.LeaveRequestService;
import com.ems.ems.service.LeaveService;
import com.ems.ems.service.SalaryService;
import org.hibernate.event.spi.PreInsertEvent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/employee")
public class EmployeeDashboardController {


    private final LeaveRequestService leaveRequestService;

    private final LeaveService leaveService;
    private final SalaryService salaryService;
    private final EmployeeService employeeService;

    public EmployeeDashboardController(LeaveRequestService leaveRequestService, EmployeeService employeeService,
                                       LeaveService leaveService,
                                       SalaryService salaryService) {
        this.leaveRequestService = leaveRequestService;
        this.employeeService = employeeService;
        this.leaveService = leaveService;
        this.salaryService = salaryService;
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {

        String email = principal.getName();
        Employee emp = employeeService.getEmployeeByEmail(email);

        long totalLeaves = leaveRequestService.countLeavesByEmpId(emp.getId());
        long totalSalaryRecords = salaryService.countSalaryByEmpId(emp.getId());

        model.addAttribute("employeeName", emp.getName());
        model.addAttribute("activePage", "dashboard");

        model.addAttribute("totalLeaves", totalLeaves);
        model.addAttribute("totalSalaryRecords", totalSalaryRecords);

        return "employee/dashboard";
    }



}
