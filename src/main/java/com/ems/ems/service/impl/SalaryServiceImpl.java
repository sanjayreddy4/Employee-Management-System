package com.ems.ems.service.impl;

import com.ems.ems.entity.Salary;
import com.ems.ems.repository.SalaryRepository;
import com.ems.ems.service.SalaryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository repo;

    public SalaryServiceImpl(SalaryRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Salary> getAllSalary() {
        return repo.findAll();
    }

    @Override
    public Salary getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Salary not found"));
    }

    @Override
    public void saveSalary(Salary salary) {

        //  Calculate total salary
        double total = salary.getBasicSalary() + salary.getAllowances() - salary.getDeductions();
        salary.setTotalSalary(total);

        repo.save(salary);
    }

    @Override
    public void deleteSalary(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<Salary> search(String keyword) {
        return repo.findByEmpCodeContainingIgnoreCaseOrEmpNameContainingIgnoreCase(keyword, keyword);
    }

    public Page<Salary> getAllSalary(int page, int size){
        return repo.findAll(PageRequest.of(page,size));
    }


    @Override
    public long countSalary() {
        return repo.count();
    }

    @Override
    public long countSalaryByEmpId(Long empId) {
        return repo.findByEmpId(empId, PageRequest.of(0, 1)).getTotalElements();
    }

    @Override
    public Page<Salary> getSalaryByEmpId(Long empId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repo.findByEmpId(empId, pageable);
    }

    // Search by EmpId OR Name
    @Override
    public Page<Salary> search(String keyword, int page, int size) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllSalary(page, size);
        }

        keyword = keyword.trim();

        // numeric => empId
        if (keyword.matches("\\d+")) {
            Long empId = Long.parseLong(keyword);
            return repo.findByEmpId(empId, PageRequest.of(page, size));
        }

        // text => empName
        return repo.findByEmpNameContainingIgnoreCase(keyword, PageRequest.of(page, size));
    }


}
