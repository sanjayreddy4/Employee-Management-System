package com.ems.ems.controller;

import com.ems.ems.entity.Department;
import com.ems.ems.entity.Employee;
import com.ems.ems.entity.Salary;
import com.ems.ems.service.DepartmentService;
import com.ems.ems.service.EmployeeService;
import com.ems.ems.service.SalaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin/salary")
public class AdminSalaryController {

    private final SalaryService salaryService;
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    public AdminSalaryController(SalaryService salaryService,
                                 DepartmentService departmentService,
                                 EmployeeService employeeService) {
        this.salaryService = salaryService;
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    // Show Salary list page
    @GetMapping
    public String salaryList(@RequestParam(required = false) String keyword, Model model) {

        if (keyword != null && !keyword.trim().isEmpty()) {
            model.addAttribute("list", salaryService.search(keyword));
            model.addAttribute("keyword", keyword);
        } else {
            model.addAttribute("list", salaryService.getAllSalary());
            model.addAttribute("keyword", "");
        }

        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        model.addAttribute("activePage", "salary");

        return "admin/salary";
    }


    // Open Add Salary form
    @GetMapping("/add")
    public String addSalary(Model model) {
        model.addAttribute("salary", new Salary());
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "admin/salary-add";
    }



    //  Save Salary
    @PostMapping("/save")
    public String saveSalary(@ModelAttribute Salary salary) {

        salary.setTotalSalary(
                salary.getBasicSalary() + salary.getAllowances() - salary.getDeductions()
        );

        Employee emp = employeeService.getEmployeeById(salary.getEmpId());
        Department dept = departmentService.getById(salary.getDepartmentId());

        salary.setEmpCode(emp.getEmployeeCode());
        salary.setEmpName(emp.getName());
        salary.setDeptName(dept.getName());

        salaryService.saveSalary(salary);
        return "redirect:/admin/salary";
    }


    // Delete Salary
    @GetMapping("/delete/{id}")
    public String deleteSalary(@PathVariable Long id) {
        salaryService.deleteSalary(id);
        return "redirect:/admin/salary";
    }

    // Search Salary
    @GetMapping("/search")
    public String searchSalary(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute("list", salaryService.search(keyword));
        return "admin/salary";
    }

    @GetMapping("/employees/{deptId}")
    @ResponseBody
    public List<Map<String, Object>> getEmployeesByDepartment(@PathVariable Long deptId) {

        return employeeService.getEmployeesByDepartment(deptId)
                .stream()
                .map(emp -> {
                    Map<String, Object> map = new java.util.HashMap<>();
                    map.put("id", emp.getId());
                    map.put("name", emp.getName());
                    return map;
                })
                .toList();
    }



}
