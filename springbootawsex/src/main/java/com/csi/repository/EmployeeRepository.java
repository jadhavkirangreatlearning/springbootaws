package com.csi.repository;

import com.csi.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByEmpEmailIdAndEmpPassword(String empEmailId, String empPassword);

    List<Employee> findByEmpName(String empName);


}
