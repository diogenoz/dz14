package com.gb.dz14.repositories;


import com.gb.dz14.entities.Task;

import java.util.List;

public interface ITaskRepository {

    boolean addTask(Task task);

    List getTasks();

    Task updateTask(Task udpatedTask);

    Task findById(Long id);

    List findByName(String name);

    List findByStatus(Task.TaskStatus taskStatus);

    boolean deleteTask(Task task);

    boolean deleteById(Long id);

    boolean isEmpty();
}