package com.example.myplan.controller;

import com.example.myplan.entity.Task;
import com.example.myplan.exception.ResourceNotFoundException;
import com.example.myplan.resource.MultiConditonReSource;
import com.example.myplan.resource.TaskResource;
import com.example.myplan.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/task")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'NORMAL')")
    public List<Task> addTask(@RequestBody TaskResource resource) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if ("" != name && !name.isEmpty())
            return taskService.save(resource, name);
        else
            throw new ResourceNotFoundException("invalid user");

    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTask(@PathVariable Long id) throws Exception {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if ("" != name && !name.isEmpty())
            taskService.deleteTask(id, name);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @PostFilter("filterObject.user.name == authentication.name or hasRole('ADMIN')")
    public List<Task> updateTask(@RequestBody TaskResource resource) throws Exception {
        return taskService.updateTask(resource);
    }

    /**
     * 查询所有
     * @return
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN', 'NORMAL')")
    @PostFilter("filterObject.user.name == authentication.name or hasRole('ADMIN')")
    public List<Task> getAllTask() {
        return taskService.getAllTask();
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @GetMapping("/one/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task getTask(@PathVariable Long id) throws ResourceNotFoundException {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if ("" != name && !name.isEmpty())
            return taskService.getById(id, name);
        else
            throw new ResourceNotFoundException("invalid user");
    }

    /**
     * 多条件查询
     *
     * @param resource
     * @return
     */
    @GetMapping(value = "/conditions")
    public Page<Task> getTasksByConditions(@RequestBody MultiConditonReSource resource) {
        return taskService.getByConditions(resource);
    }

    /**
     * 根据name单条件模糊查询
     *
     * @param name
     * @return
     */
    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    @PostFilter("filterObject.user.name == authentication.name or hasRole('ADMIN')")
    public List<Task> getTaskByName(@PathVariable String name) {
        return taskService.getByName(name);
    }
}

