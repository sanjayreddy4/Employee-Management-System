package com.ems.ems.service;

import com.ems.ems.entity.Employee;
import com.ems.ems.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepo;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepository employeeRepo, PasswordEncoder passwordEncoder) {
        this.employeeRepo = employeeRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    // IMPORTANT: encode password before saving
    public void saveEmployee(Employee employee) {

        // if password is coming empty from form → keep old password (during update)
        if (employee.getId() != null) {
            Employee dbEmp = employeeRepo.findById(employee.getId()).orElse(null);

            if (employee.getPassword() == null || employee.getPassword().trim().isEmpty()) {
                if (dbEmp != null) employee.setPassword(dbEmp.getPassword());
            }
        }

        // encode if not already encoded
        if (employee.getPassword() != null &&
                !employee.getPassword().startsWith("$2a$") &&
                !employee.getPassword().startsWith("$2b$") &&
                !employee.getPassword().startsWith("$2y$")) {
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        }

        // set role default (optional)
        if (employee.getRole() == null || employee.getRole().isEmpty()) {
            employee.setRole("EMPLOYEE");
        }

        employeeRepo.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepo.deleteById(id);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public List<Employee> searchEmployees(String keyword) {

        if (keyword.matches("\\d+")) {
            Optional<Employee> emp = employeeRepo.findById(Long.parseLong(keyword));
            return emp.map(List::of).orElse(List.of());
        }

        return employeeRepo.findByNameContainingIgnoreCase(keyword);
    }

    public Page<Employee> getEmployeesPaginated(int page, int size) {
        return employeeRepo.findAll(PageRequest.of(page, size));
    }

    public Employee getEmployeeByEmail(String email) {
        return employeeRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    // Update employee
    public void updateEmployee(Employee employee) {
        employeeRepo.save(employee);
    }

    // CHANGE PASSWORD METHOD
    public String changePassword(String email, String oldPassword, String newPassword) {

        Employee emp = getEmployeeByEmail(email);

        // verify old password
        if (!passwordEncoder.matches(oldPassword, emp.getPassword())) {
            return "OLD_PASSWORD_WRONG";
        }

        // set and encode new password
        emp.setPassword(passwordEncoder.encode(newPassword));
        employeeRepo.save(emp);

        return "SUCCESS";
    }
    public List<Employee> getEmployeesByDepartment(Long deptId) {
        return employeeRepo.findByDepartment_Id(deptId);
    }

}
