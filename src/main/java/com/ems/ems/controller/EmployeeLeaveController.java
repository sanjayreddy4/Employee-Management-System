package com.ems.ems.controller;

import com.ems.ems.entity.Employee;
import com.ems.ems.entity.Leave;
import com.ems.ems.entity.LeaveRequest;
import com.ems.ems.service.EmployeeService;
import com.ems.ems.service.LeaveRequestService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeLeaveController {

    private final LeaveRequestService leaveService;
    private final EmployeeService employeeService;

    public EmployeeLeaveController(LeaveRequestService leaveService, EmployeeService employeeService) {
        this.leaveService = leaveService;
        this.employeeService = employeeService;
    }

    @GetMapping("/leaves")
    public String leavesPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String status,
            Model model,
            Principal principal) {

        String email = principal.getName();
        Employee emp = employeeService.getEmployeeByEmail(email);

        int size = 5;

        Page<LeaveRequest> leavePage;

        if (status == null || status.isEmpty() || status.equalsIgnoreCase("All")) {
            leavePage = leaveService.getLeavesByEmpId(emp.getId(), page, size);
        } else {
            leavePage = leaveService.getLeavesByEmpIdAndStatus(emp.getId(), status, page, size);
        }

        model.addAttribute("employeeName", emp.getName());
        model.addAttribute("activePage", "leave");

        model.addAttribute("leaves", leavePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", leavePage.getTotalPages());

        // send selected status back to html
        model.addAttribute("selectedStatus", status);

        return "employee/leaves";
    }


    @GetMapping("/apply-new-leave")
    public String addLeavePage(Model model, Principal principal) {

        String email = principal.getName();
        Employee emp = employeeService.getEmployeeByEmail(email);

        model.addAttribute("employeeName", emp.getName());
        model.addAttribute("activePage", "leave");

        model.addAttribute("leaveRequest", new LeaveRequest());

        return "employee/leave-add";
    }

    @PostMapping("/apply-leave")
    public String applyLeave(@ModelAttribute LeaveRequest leaveRequest, Principal principal) {

        String email = principal.getName();
        Employee emp = employeeService.getEmployeeByEmail(email);

        leaveRequest.setEmployee(emp);
        leaveRequest.setStatus("Pending");

        leaveService.saveLeave(leaveRequest);

        return "redirect:/employee/leaves";
    }





}
