package com.example.myplan.controller;

import com.example.myplan.entity.Task;
import com.example.myplan.exception.TaskNotFoundException;
import com.example.myplan.resource.TaskResource;
import com.example.myplan.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/todo")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED)
    public Task addTask(@RequestBody TaskResource resource) {
        return taskService.save(resource);
    }

    @PatchMapping("/{userId}/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTask(@PathVariable Long id,@PathVariable Long userId) throws Exception {
        taskService.deleteTask(id,userId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Task UpdateTask(@RequestBody TaskResource dto) throws Exception {
        return taskService.UpdateTask(dto);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getAllTask(@PathVariable Long userId) {
        return taskService.getAllTask(userId);
    }

    @GetMapping("/{userId}/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task getTask(@PathVariable Long id, @PathVariable Long userId) throws TaskNotFoundException {
        return taskService.getById(id,userId);
    }
}

