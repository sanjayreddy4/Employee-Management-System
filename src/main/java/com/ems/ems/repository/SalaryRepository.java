package com.ems.ems.repository;

import com.ems.ems.entity.Salary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.time.LocalDate;
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

    @Query("SELECT COALESCE(SUM(s.totalSalary), 0) FROM Salary s " +
    	       "WHERE s.payDate >= :startDate AND s.payDate < :endDate")
    	double getMonthlyPay(
    	        @Param("startDate") LocalDate startDate,
    	        @Param("endDate") LocalDate endDate
    	);
}

