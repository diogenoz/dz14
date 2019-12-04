package com.gb.dz14.repositories;

import com.gb.dz14.entities.Employee;

import java.util.List;

public interface IEmployeeRepository {
    boolean addEmployee(Employee employee);

    List getEmployees();

    Employee updateEmployee(Employee udpatedEmployee);

    Employee findById(Long id);

    List findByName(String name);

    boolean isEmpty();
}
