package com.ems.ems.service;

import com.ems.ems.entity.Salary;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SalaryService {

    List<Salary> getAllSalary();

    Salary getById(Long id);

    void saveSalary(Salary salary);

    void deleteSalary(Long id);

    List<Salary> search(String keyword);

    long countSalary();

    long countSalaryByEmpId(Long empId);

    Page<Salary> getSalaryByEmpId(Long empId, int page, int size);

    // ✅ search common (empId or name)
    Page<Salary> search(String keyword, int page, int size);



}
