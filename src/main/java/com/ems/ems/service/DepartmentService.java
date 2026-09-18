package com.ems.ems.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.ems.ems.entity.Department;
import com.ems.ems.repository.DepartmentRepository;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public void saveDepartment(Department dept) {
        departmentRepository.save(dept);
    }

    public Department getById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        departmentRepository.deleteById(id);
    }

    public Page<Department> getDepartmentsPage(int page) {
        return departmentRepository.findAll(PageRequest.of(page, 5));
    }

    public List<Department> searchDepartments(String keyword) {
        return departmentRepository
                .findByNameContainingIgnoreCase(keyword);
    }


}

