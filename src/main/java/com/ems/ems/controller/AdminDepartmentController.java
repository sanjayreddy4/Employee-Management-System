package com.ems.ems.controller;

import com.ems.ems.entity.Department;
import com.ems.ems.service.DepartmentService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/departments")
public class AdminDepartmentController {

    private final DepartmentService service;

    public AdminDepartmentController(DepartmentService service) {
        this.service = service;
    }

    @GetMapping
    public String list(
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        Page<Department> departments = service.getDepartmentsPage(page);

        model.addAttribute("list", departments.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", departments.getTotalPages());
        model.addAttribute("role", "ADMIN");
        model.addAttribute("activePage", "departments");


        return "admin/departments";
    }


    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("department", new Department());
        return "admin/department-add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Department department) {
        service.saveDepartment(department);
        return "redirect:/admin/departments";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("department", service.getById(id));
        return "admin/department-edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/admin/departments";
    }

    @GetMapping("/search")
    public String searchDepartment(
            @RequestParam String keyword,
            Model model) {

        if (keyword == null || keyword.trim().isEmpty()) {
            model.addAttribute("list", service.getAllDepartments());
        } else {
            model.addAttribute("list", service.searchDepartments(keyword));
        }

        // reset pagination display
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);

        return "admin/departments";
    }

}
