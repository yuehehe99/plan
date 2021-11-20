package com.example.myplan.controller;

import com.example.myplan.entity.User;
import com.example.myplan.resource.UserResource;
import com.example.myplan.service.UserService;
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
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService usersService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody UserResource resource) {
        return usersService.save(resource);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id) {
        usersService.deleteUser(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User UpdateTodo(@RequestBody UserResource dto) {
        return usersService.UpdateUser(dto);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable Long id) {
        return usersService.getUserAndJudge(id);
    }

}

