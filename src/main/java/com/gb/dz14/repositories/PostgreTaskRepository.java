package com.gb.dz14.repositories;

import com.gb.dz14.entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
@Transactional
public class PostgreTaskRepository implements ITaskRepository {
    private EntityManager entityManager;

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean addTask(Task task) {
        try {
            entityManager.persist(task);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getTasks() {
        ArrayList<Task> tasks;
        try {
            if (!isEmpty()) {
                tasks = (ArrayList<Task>) entityManager.createNamedQuery("Task.findAll", Task.class).getResultList();
            } else {
                tasks = new ArrayList<Task>();
            }
            return tasks;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private Boolean isTaskExists(Task task) {
        try {
            Task findedTask = findById(task.getId());
            return findedTask == null;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Task updateTask(Task udpatedTask) {
        if (!isTaskExists(udpatedTask)) {
            throw new RuntimeException("Task not in repository");
        }
        try {
            entityManager.persist(udpatedTask);
            return udpatedTask;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteTask(Task task) {
        if (!isTaskExists(task)) {
            throw new RuntimeException("Task not in repository");
        }

        try {
            entityManager.remove(task);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            Task task = findById(id);
            entityManager.remove(task);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Task findById(Long id) {
        Task task;
        try {
            task = entityManager.createNamedQuery("Task.findById", Task.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return task;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List findByName(String name) {
        ArrayList<Task> tasks;
        try {
            tasks = (ArrayList<Task>) entityManager.createNamedQuery("Task.findByName", Task.class)
                    .setParameter("name", name)
                    .getResultList();
            return tasks;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List findByStatus(Task.TaskStatus taskStatus) {
        ArrayList<Task> tasks;
        try {
            tasks = (ArrayList<Task>) entityManager.createNamedQuery("Task.findByName", Task.class)
                    .setParameter("status", taskStatus)
                    .getResultList();
            return tasks;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean isEmpty() {
        try {
            return entityManager.createNamedQuery("Task.findAll").getResultList().size() == 0;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }
}