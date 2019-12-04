package com.gb.dz14.controllers;

import com.gb.dz14.entities.Employee;
import com.gb.dz14.entities.Task;
import com.gb.dz14.services.EmployeeService;
import com.gb.dz14.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    private TaskService taskService;
    private EmployeeService employeeService;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:tasks/show";
    }

    // http://localhost:8189/app/tasks/show
    @RequestMapping(path = "tasks/show", method = RequestMethod.GET)
    public String showAllTasks(Model model, @RequestParam Map<String, String> params) {
        ArrayList<Task> tasks = new ArrayList<>();
        if (params.get("id") != null) {
            tasks.add((Task) taskService.findById(Long.parseLong(params.get("id"))));
        } else if (params.get("status") != null) {
            tasks = (ArrayList<Task>) taskService.findByStatus(Task.TaskStatus.valueOf(params.get("status")));
        } else {
            tasks = (ArrayList<Task>) taskService.getTasks();
        }
        model.addAttribute("tasks", tasks);
        model.addAttribute("taskStatuses", Task.TaskStatus.values());
        return "all_tasks";
    }

    // http://localhost:8189/app/tasks/add
    @RequestMapping(path = "tasks/add", method = RequestMethod.GET)
    public String addTask(Model model) {
        List<Employee> employees = employeeService.getEmployees();

        Task task = new Task();
        task.setOwner(employees.get(0));
        task.setAssignee(employees.get(0));
        task.setStatus(Task.TaskStatus.Open);

        model.addAttribute("task", task);
        model.addAttribute("taskStatuses", Task.TaskStatus.values());
        model.addAttribute("employees", employees);
        return "add_task";
    }

    @PostMapping(path = "tasks/add")
    public String addTaskResult(@ModelAttribute("task") Task task, @RequestParam Map<String, String> params, Model model) {
        task.setOwner(employeeService.findById(Long.parseLong(params.get("ownerId"))));
        task.setAssignee(employeeService.findById(Long.parseLong(params.get("assigneeId"))));
        task.setStatus(Task.TaskStatus.valueOf(params.get("status")));
        taskService.addTask(task);
        return "add_task_result";
    }

    // http://localhost:8189/app/tasks/{id}
    @RequestMapping(path = "tasks/{id}", method = RequestMethod.GET)
    public String taskInfo(Model model, @PathVariable Long id) {
        Task task = taskService.findById(id);
        model.addAttribute("task", task);
        return "task_info";
    }

    // http://localhost:8189/app/tasks/remove/{id}
    @RequestMapping(path = "tasks/remove/{id}", method = RequestMethod.GET)
    public String removeTask(Model model, @PathVariable Long id) {
        taskService.deleteById(id);
        model.addAttribute("deletedTaskId", id);
        return "remove_task_result";
    }

    // http://localhost:8189/app/employees/add
    @RequestMapping(path = "employees/add", method = RequestMethod.GET)
    public String addEmployee(Model model) {
        Employee employee = new Employee();
        employee.setName("Medved");
        model.addAttribute("employee", employee);
        return "add_employee";
    }

    @PostMapping(path = "employees/add")
    public String add_employee_result(@ModelAttribute("employee") Employee employee) {
        employeeService.addEmployee(employee);
        return "add_employee_result";
    }

    // http://localhost:8189/app/employees/show
    @RequestMapping(path = "employees/show", method = RequestMethod.GET)
    public String showAllEmployees(Model model) {
        List<Employee> employees = (List<Employee>) employeeService.getEmployees();
        model.addAttribute("employees", employees);
        return "all_employees";
    }
}
