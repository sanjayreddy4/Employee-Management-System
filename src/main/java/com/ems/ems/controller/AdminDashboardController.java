package com.ems.ems.controller;

import com.ems.ems.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final SalaryService salaryService;
    private final LeaveRequestService leaveRequestService;

    public AdminDashboardController(EmployeeService employeeService,
                                    DepartmentService departmentService,
                                    SalaryService salaryService,
                                    LeaveRequestService leaveRequestService) {

        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.salaryService = salaryService;
        this.leaveRequestService = leaveRequestService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        long totalEmployees = employeeService.getAllEmployees().size();
        long totalDepartments = departmentService.getAllDepartments().size();

        //  LEAVE COUNTS
        long leaveApplied = leaveRequestService.countAllLeaves();
        long leavePending = leaveRequestService.countLeavesByStatus("Pending");
        long leaveApproved = leaveRequestService.countLeavesByStatus("Approved");
        long leaveRejected = leaveRequestService.countLeavesByStatus("Rejected");

        model.addAttribute("totalEmployees", totalEmployees);
        model.addAttribute("totalDepartments", totalDepartments);

        model.addAttribute("leaveApplied", leaveApplied);
        model.addAttribute("leavePending", leavePending);
        model.addAttribute("leaveApproved", leaveApproved);
        model.addAttribute("leaveRejected", leaveRejected);

        model.addAttribute("activePage", "dashboard");

        return "admin/dashboard";
    }
}
