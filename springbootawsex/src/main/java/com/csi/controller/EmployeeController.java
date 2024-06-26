package com.csi.controller;

import com.csi.entity.Employee;
import com.csi.exception.RecordNotFoundException;
import com.csi.service.IEmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
@Slf4j
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @PostMapping("/signup")
    public ResponseEntity<Employee> signUp(@Valid @RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.signUp(employee), HttpStatus.CREATED);
    }

    @GetMapping("/signin/{empEmailId}/{empPassword}")
    public ResponseEntity<Boolean> signIn(@PathVariable String empEmailId, @PathVariable String empPassword) {
        return new ResponseEntity<>(employeeService.signIn(empEmailId, empPassword), HttpStatus.OK);
    }

    @GetMapping("/findbyemailid")
    public ResponseEntity<Employee> findByEmpEmailId(@RequestParam String empEmailId){
        return new ResponseEntity<>(employeeService.findAll().stream().filter(emp-> emp.getEmpEmailId().equals(empEmailId)).toList().get(0), HttpStatus.OK);
    }

    @GetMapping("/findbyid/{empId}")
    public ResponseEntity<Optional<Employee>> findById(@PathVariable int empId) {
        return new ResponseEntity<>(employeeService.findById(empId), HttpStatus.OK);
    }

    @GetMapping("/findbyname/{empName}")
    public ResponseEntity<List<Employee>> findByName(@PathVariable String empName) {
        return new ResponseEntity<>(employeeService.findByName(empName), HttpStatus.OK);
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Employee>> findAll() {
        return new ResponseEntity<>(employeeService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/sortbyid")
    public ResponseEntity<List<Employee>> sortById() {
        return new ResponseEntity<>(employeeService.findAll().stream().sorted(Comparator.comparing(Employee::getEmpId)).toList(), HttpStatus.OK);
    }

    @GetMapping("/sortbyname")
    public ResponseEntity<List<Employee>> sortByName() {
        return new ResponseEntity<>(employeeService.findAll().stream().sorted(Comparator.comparing(Employee::getEmpName)).toList(), HttpStatus.OK);
    }

    @PutMapping("/update/{empId}")
    public ResponseEntity<Employee> update(@PathVariable int empId, @Valid @RequestBody Employee employee) {
        Employee employee1 = employeeService.findById(empId).orElseThrow(() -> new RecordNotFoundException("Employee ID Does Not Exist"));

        employee1.setEmpName(employee.getEmpName());
        employee1.setEmpDOB(employee.getEmpDOB());
        employee1.setEmpContactNumber(employee.getEmpContactNumber());
        employee1.setEmpPassword(employee.getEmpPassword());
        employee1.setEmpAddress(employee.getEmpAddress());
        employee1.setEmpSalary(employee.getEmpSalary());
        employee1.setEmpEmailId(employee.getEmpEmailId());

        return new ResponseEntity<>(employeeService.update(employee1), HttpStatus.CREATED);
    }

    @PatchMapping("/changeaddress/{empId}/{empAddress}")
    public ResponseEntity<Employee> changeAddress(@PathVariable int empId, @PathVariable String empAddress) {
        Employee employee1 = employeeService.findById(empId).orElseThrow(() -> new RecordNotFoundException("Employee ID Does Not Exist"));

        employee1.setEmpAddress(empAddress);

        return new ResponseEntity<>(employeeService.update(employee1), HttpStatus.CREATED);
    }

    @DeleteMapping("/deletebyid/{empId}")
    public ResponseEntity<String> deleteById(@PathVariable int empId) {
        employeeService.deleteById(empId);
        return new ResponseEntity<>("Data Deleted Successfully", HttpStatus.OK);
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<String> deleteAll() {
        employeeService.deleteAll();
        return new ResponseEntity<>("All Data Deleted Successfully", HttpStatus.OK);
    }


}
