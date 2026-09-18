package com.ems.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ems.ems.entity.Department;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findByNameContainingIgnoreCase(String name);

}




