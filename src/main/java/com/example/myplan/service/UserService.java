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
        Optional<User> byName = userRepository.findByName(resource.getName());
//        User byNameUser = byName.isEmpty() ? null : byName.get();
        if(!byName.equals(Optional.empty()))
            throw new RepeatRegisterException("Repeat register user");

        return userRepository.save(
                User.builder()
                        .name(resource.getName())
                        .password(passwordEncoder.encode(resource.getPassword()))
                        .gender(resource.isGender())
                        .authorities(Collections.singletonList(new Authority(resource.getName(), "ROLE_NORMAL")))
                        .build());
    }


    public void deleteUser(Long id) {
        User userAndJudge = getUserAndJudge(id);
        if (!userAndJudge.equals(null)) {
            userAndJudge.setDeleted(true);
            userRepository.save(userAndJudge);

            List<Task> allTask = taskService.getAllTask(userAndJudge.getId());
            allTask.forEach(task -> {
                task.setDeleted(true);
                taskRepository.save(task);
            });
        }
        throw new ResourceNotFoundException("User is not found!");
    }

    public User updateUser(UserResource resource) {
        User userAndJudge = getUserAndJudge(resource.getId());
        if (!userAndJudge.equals(null)) {
            userAndJudge.setName(resource.getName());
            userAndJudge.setPassword(resource.getPassword());
            return userRepository.save(userAndJudge);
        }
        throw new ResourceNotFoundException("User is not found!");
    }

    public User getUserAndJudge(Long id) {
        Optional<User> byId = userRepository.findById(id);
        return byId.isEmpty() ? null : byId.get();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
