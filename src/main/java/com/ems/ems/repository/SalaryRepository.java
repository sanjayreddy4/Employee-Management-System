package com.ems.ems.repository;

import com.ems.ems.entity.Salary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary, Long> {

    List<Salary> findByEmpCodeContainingIgnoreCaseOrEmpNameContainingIgnoreCase(String empCode, String empName);
    long countByEmpId(Long empId);

    //  Search by empCode (recommended)
    Page<Salary> findByEmpCodeContainingIgnoreCase(String empCode, Pageable pageable);

    Page<Salary> findByEmpNameContainingIgnoreCase(String keyword, Pageable pageable);

    //  Optional: search by empId
    Page<Salary> findByEmpId(Long empId, Pageable pageable);

    List<Salary> findByEmpNameContainingIgnoreCase(String name);

    List<Salary> findByEmpCodeContainingIgnoreCase(String empCode);

    List<Salary> findByEmpId(Long empId);


}

