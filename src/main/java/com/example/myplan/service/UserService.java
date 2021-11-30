package com.example.myplan.service;

import com.example.myplan.entity.Authority;
import com.example.myplan.entity.Task;
import com.example.myplan.entity.User;
import com.example.myplan.exception.RepeatRegisterException;
import com.example.myplan.exception.ResourceNotFoundException;
import com.example.myplan.repository.TaskRepository;
import com.example.myplan.resource.UserResource;
import com.example.myplan.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskService taskService;
    private final PasswordEncoder passwordEncoder;

    public User save(UserResource resource) {
        User user = getUserAndJudge(resource.getName());
        if (null != user)
            throw new RepeatRegisterException("Repeat register user");

        return userRepository.save(
                User.builder()
                        .name(resource.getName())
                        .password(passwordEncoder.encode(resource.getPassword()))
                        .gender(resource.isGender())
                        .authorities(Collections.singletonList(new Authority(resource.getName(), "ROLE_NORMAL")))
                        .build());
    }

    public User getUserAndJudge(String name) {
        Optional<User> byId = userRepository.findByName(name);
        return byId.isEmpty() ? null : byId.get();
    }

    public void deleteUser(Long id) {
        User user = userRepository.findByIdAndDeleted(id, false);
        if (null != user) {
            List<Task> allTask = taskService.getAllTask(user.getId());
            allTask.forEach(task -> {
                task.setDeleted(true);
                taskRepository.save(task);
            });

            user.setDeleted(true);
            userRepository.save(user);
        } else
            throw new ResourceNotFoundException("User is not found!");
    }

    public User updateUser(UserResource resource) {
        User user = userRepository.findByIdAndDeleted(resource.getUserId(), false);
        if (null != user) {
//            user.setName(resource.getName());
            user.setPassword(passwordEncoder.encode(resource.getPassword()));
            return userRepository.save(user);
        }
        throw new ResourceNotFoundException("User is not found!");
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
