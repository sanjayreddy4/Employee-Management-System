package com.ems.ems.controller;

import com.ems.ems.entity.Department;
import com.ems.ems.service.DepartmentService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ems.ems.entity.Employee;
import com.ems.ems.service.EmployeeService;

@Controller
@RequestMapping("/admin/employees")
public class AdminEmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public AdminEmployeeController(EmployeeService employeeService,
                                   DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }


    // LIST EMPLOYEES
//    @GetMapping
//    public String employeeList(Model model) {
//        model.addAttribute("list", employeeService.getAllEmployees());
//        return "employees";
//    }

    // LIST + PAGINATION
    @GetMapping
    public String employeeList(
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        int size = 5;

        Page<Employee> employeePage = employeeService.getEmployeesPaginated(page, size);

        model.addAttribute("list", employeePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute(
                "totalPages", employeePage.getTotalPages());
        model.addAttribute("role", "ADMIN");
        model.addAttribute("activePage", "employees");


        return "admin/employees";
    }


    // ADD FORM  →  /employees/add
    @GetMapping("/add")
    public String showAddEmployeeForm(Model model) {

        Employee emp = new Employee();
        emp.setDepartment(new Department());

        model.addAttribute("employee", emp);
        model.addAttribute("departments", departmentService.getAllDepartments());

        return "admin/employee-add";
    }

    // SAVE EMPLOYEE → /employees/save
    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/admin/employees";
    }


    // VIEW → /employees/view/{id}
    @GetMapping("/view/{id}")
    public String viewEmployee(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.getEmployeeById(id));
        return "admin/employee-view";
    }

    // EDIT → /employees/edit/{id}
    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable Long id, Model model) {

        Employee emp = employeeService.getEmployeeById(id);

        if (emp.getDepartment() == null) {
            emp.setDepartment(new Department());
        }

        model.addAttribute("employee", emp);
        model.addAttribute("departments", departmentService.getAllDepartments());

        return "admin/employee-edit";
    }



    // DELETE → /employees/delete/{id}
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/admin/employees";
    }

    // SEARCH → /employees/search
    @GetMapping("/search")
    public String searchEmployee(@RequestParam String keyword, Model model) {

        if (keyword == null || keyword.trim().isEmpty()) {
            model.addAttribute("list", employeeService.getAllEmployees());
        } else {
            model.addAttribute("list", employeeService.searchEmployees(keyword));
        }

        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);

        return "admin/employees";
    }

}
