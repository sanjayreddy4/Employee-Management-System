package com.ems.ems.controller;

import com.ems.ems.entity.LeaveRequest;
import com.ems.ems.service.LeaveRequestService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/leaves")
public class AdminLeaveController {

    private final LeaveRequestService leaveService;

    public AdminLeaveController(LeaveRequestService leaveService) {
        this.leaveService = leaveService;
    }

    @GetMapping
    public String leavesPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String keyword,
            Model model) {

        int size = 5;
        Page<LeaveRequest> leavePage;

        // Search by empId
        if (keyword != null && !keyword.trim().isEmpty()) {
            Long empId = Long.parseLong(keyword);
            leavePage = leaveService.getLeavesByEmpId(empId, page, size);
            model.addAttribute("keyword", keyword);
        }
        // Filter status
        else if (status != null && !status.trim().isEmpty()) {
            leavePage = leaveService.getLeavesByStatus(status, page, size);
            model.addAttribute("status", status);
        }
        // list all
        else {
            leavePage = leaveService.getAllLeaves(page, size);
        }

        model.addAttribute("list", leavePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", leavePage.getTotalPages());
        model.addAttribute("activePage", "leaves");

        return "admin/leaves";
    }

    @GetMapping("/view/{id}")
    public String viewLeave(@PathVariable Long id, Model model) {

        LeaveRequest leave = leaveService.getLeaveById(id);
        model.addAttribute("leave", leave);
        model.addAttribute("activePage", "leaves");

        return "admin/leave-view";
    }

    @PostMapping("/approve/{id}")
    public String approveLeave(@PathVariable Long id) {
        leaveService.updateStatus(id, "Approved");
        return "redirect:/admin/leaves";
    }

    @PostMapping("/reject/{id}")
    public String rejectLeave(@PathVariable Long id) {
        leaveService.updateStatus(id, "Rejected");
        return "redirect:/admin/leaves";
    }


}
