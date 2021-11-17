package com.example.myplan.controller;

import com.example.myplan.entity.Todo;
import com.example.myplan.entity.dto.TodoDTO;
import com.example.myplan.service.TodoService;
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
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED)
    public Todo addTodo(@RequestBody TodoDTO dto) {
        return todoService.save(dto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTodo(@PathVariable Long id) throws Exception {
        todoService.deleteTodo(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Todo UpdateTodo(@RequestBody TodoDTO dto) throws Exception {
        return todoService.UpdateTodo(dto);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Todo> getAllTodo(@PathVariable Long userId) {
        return todoService.getAllTodo(userId);
    }

    @GetMapping("/{userId}/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Todo getTodo(@PathVariable Long id,@PathVariable Long userId) throws Exception {
        return todoService.getById(id,userId);
    }
}

