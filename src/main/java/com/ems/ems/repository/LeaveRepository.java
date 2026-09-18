package com.ems.ems.repository;

import com.ems.ems.entity.Leave;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Long> {

    // Admin
    long countByStatus(String status);

    // Employee leaves list
    List<Leave> findByEmpId(Long empId);

    // Admin search by empId + pagination
    Page<Leave> findByEmpId(Long empId, Pageable pageable);

    // Filter by status + pagination
    Page<Leave> findByStatus(String status, Pageable pageable);

    // Count leaves for employee dashboard
    long countByEmpId(Long empId);

    long countByEmpIdAndStatus(Long empId, String status);
}
