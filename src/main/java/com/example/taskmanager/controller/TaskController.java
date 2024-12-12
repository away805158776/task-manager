package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

import java.util.List;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

//    @GetMapping
//    public String getAllTasks(Model model) {
//        List<Task> tasks = taskService.getAllTasks();
//        model.addAttribute("tasks", tasks);
//        return "tasks";
//    }
    @GetMapping("/tasks")
    public String getTasks(Model model, Principal principal) {
        if (principal != null) {
            System.out.println("Logged-in user: " + principal.getName());
            model.addAttribute("username", principal.getName());
        } else {
            System.out.println("No user is currently logged in.");
        }

        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "tasks";
    }


    @PostMapping("/tasks")
    public String createTask(@ModelAttribute Task task, Principal principal) {
        taskService.saveTask(task, principal.getName());
        return "redirect:/tasks";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }
}
