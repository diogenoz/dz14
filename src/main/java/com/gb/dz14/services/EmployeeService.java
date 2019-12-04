package com.gb.dz14.services;


import com.gb.dz14.entities.Employee;
import com.gb.dz14.repositories.IEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private IEmployeeRepository repository;

    public EmployeeService() {
    }

    public boolean addEmployee(Employee employee) {
        return repository.addEmployee(employee);
    }

    public Employee updateEmployee(Employee employee) {
        return repository.updateEmployee(employee);
    }

    public List getEmployees() {
        return repository.getEmployees();
    }

    public Employee findById(Long id) {
        return repository.findById(id);
    }

    public List findByName(String name) {
        return repository.findByName(name);
    }

    @Autowired
    public void setRepository(IEmployeeRepository repository) {
        this.repository = repository;
    }

}
