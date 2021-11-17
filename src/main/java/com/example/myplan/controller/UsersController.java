package com.example.myplan.controller;

import com.example.myplan.entity.Todo;
import com.example.myplan.entity.Users;
import com.example.myplan.entity.dto.TodoDTO;
import com.example.myplan.entity.dto.UsersDTO;
import com.example.myplan.service.TodoService;
import com.example.myplan.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Users addUser(@RequestBody Users users) {
        return usersService.save(users);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id) {
        usersService.deleteUser(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Users UpdateTodo(@RequestBody UsersDTO dto) {
        return usersService.UpdateUser(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Users> getAllUsers() {
        return usersService.getAllUsers();
    }

}

