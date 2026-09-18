package com.ems.ems.service.impl;

import com.ems.ems.repository.DepartmentRepository;
import com.ems.ems.repository.EmployeeRepository;
import com.ems.ems.repository.LeaveRepository;
import com.ems.ems.repository.SalaryRepository;
import com.ems.ems.service.DashboardService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final EmployeeRepository employeeRepo;
    private final DepartmentRepository departmentRepo;
    private final SalaryRepository salaryRepo;
    private final LeaveRepository leaveRepo;

    public DashboardServiceImpl(EmployeeRepository employeeRepo,
                                DepartmentRepository departmentRepo,
                                SalaryRepository salaryRepo,
                                LeaveRepository leaveRepo) {
        this.employeeRepo = employeeRepo;
        this.departmentRepo = departmentRepo;
        this.salaryRepo = salaryRepo;
        this.leaveRepo = leaveRepo;
    }

    @Override
    public Map<String, Object> getDashboardCounts() {

        Map<String, Object> map = new HashMap<>();

        // ✅ total counts
        map.put("totalEmployees", employeeRepo.count());
        map.put("totalDepartments", departmentRepo.count());
        map.put("totalSalaryRecords", salaryRepo.count());

        // ✅ leave counts
        map.put("leaveApplied", leaveRepo.count());
        map.put("leavePending", leaveRepo.countByStatus("Pending"));
        map.put("leaveApproved", leaveRepo.countByStatus("Approved"));
        map.put("leaveRejected", leaveRepo.countByStatus("Rejected"));

        return map;
    }
}
