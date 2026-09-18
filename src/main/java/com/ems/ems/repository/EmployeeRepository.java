package com.ems.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ems.ems.entity.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByNameContainingIgnoreCase(String name);

    Optional<Employee> findByEmail(String email);

    List<Employee> findByDepartment_Id(Long deptId);




}