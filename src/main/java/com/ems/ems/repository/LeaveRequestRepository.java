package com.ems.ems.repository;

import com.ems.ems.entity.LeaveRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    //  Fetch leaves by employee id
    Page<LeaveRequest> findByEmployee_Id(Long empId, Pageable pageable);

    // Fetch leaves by employee id + status
    Page<LeaveRequest> findByEmployee_IdAndStatus(Long empId, String status, Pageable pageable);

    //  Count leaves by employee id
    long countByEmployee_Id(Long empId);

    //  Optional: admin filter only by status
    Page<LeaveRequest> findByStatus(String status, Pageable pageable);

    Page<LeaveRequest> findAll(Pageable pageable);


    long countByStatus(String status);
}
