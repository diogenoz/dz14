package com.gb.dz14.repositories;

import com.gb.dz14.entities.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
@Transactional
public class PostgreEmployeeRepository implements IEmployeeRepository {
    @PersistenceContext
    EntityManager entityManager;

    public PostgreEmployeeRepository() {
    }

    @Override
    public boolean addEmployee(Employee employee) {
        try {
            this.entityManager.persist(employee);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getEmployees() {
        ArrayList<Employee> employees;
        try {
            employees = (ArrayList<Employee>) entityManager
                    .createNamedQuery("Employee.findAll", Employee.class)
                    .getResultList();
            return employees;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public Employee updateEmployee(Employee udpatedEmployee) {
        try {
            entityManager.persist(udpatedEmployee);
            return udpatedEmployee;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Employee findById(Long id) {
        Employee employee;
        try {
            employee = entityManager.createNamedQuery("Employee.findById", Employee.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return employee;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List findByName(String name) {
        ArrayList<Employee> employees;
        try {
            employees = (ArrayList<Employee>) entityManager.createNamedQuery("Employee.findByName", Employee.class)
                    .setParameter("name", name)
                    .getResultList();
            return employees;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public boolean isEmpty() {
        boolean isEmpty;
        try {
            isEmpty = entityManager.createNamedQuery("Employee.findAll", Employee.class)
                    .getResultList().size() == 0;
            return isEmpty;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }
}
