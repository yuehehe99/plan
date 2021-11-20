package com.example.myplan.service;

import com.example.myplan.entity.Task;
import com.example.myplan.entity.User;
import com.example.myplan.repository.TaskRepository;
import com.example.myplan.resource.UserResource;
import com.example.myplan.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    public User save(UserResource resource) {
        return userRepository.save(User.builder()
                .name(resource.getName())
                .gender(resource.isGender())
                .build());
    }



    public void deleteUser(Long id) {
        User userAndJudge = getUserAndJudge(id);
        if (null != userAndJudge){
            userAndJudge.setDeleted(true);
            userRepository.save(userAndJudge);

            List<Task> allTask = taskService.getAllTask(userAndJudge.getId());
            allTask.forEach(task -> {
                task.setDeleted(true);
                taskRepository.save(task);
            });
        }
    }

    public User UpdateUser(UserResource resource) {
        User userAndJudge = getUserAndJudge(resource.getId());
        if (null != userAndJudge){
            userAndJudge.setName(resource.getName());
            userAndJudge.setGender(resource.isGender());
            return userRepository.save(userAndJudge);
        }
        return null;
    }

    public User getUserAndJudge(Long id) {
        Optional<User> byId = userRepository.findById(id);
        return byId.isEmpty() ? null : byId.get();
    }

}
