package com.gb.dz14.services;

import com.gb.dz14.entities.Task;
import com.gb.dz14.repositories.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class TaskService {
    private ITaskRepository repository;

    public TaskService() {
    }

    public boolean addTask(Task task) {
        return repository.addTask(task);
    }

    public void print() {
        if (!this.repository.isEmpty()) {
            List tasks = this.repository.getTasks();

            System.out.println("Список задач:");
            Iterator iterator = tasks.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        } else {
            System.out.println("Список задач пуст");
        }
    }

    public boolean deleteTask(Task task) {
        return repository.deleteTask(task);
    }

    public boolean deleteById(Long id) {
        return repository.deleteById(id);
    }

    public Task updateTask(Task updTask) {
        return repository.updateTask(updTask);
    }

    public List getTasks() {
        return repository.getTasks();
    }

    public Task findById(Long id) {
        return repository.findById(id);
    }

    public List findByName(String name) {
        return repository.findByName(name);
    }

    public List findByStatus(Task.TaskStatus status) {
        return repository.findByStatus(status);
    }

    @Autowired
    public void setRepository(ITaskRepository repository) {
        this.repository = repository;
    }
}
