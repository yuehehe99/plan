package com.example.myplan.service;

import com.example.myplan.entity.Task;
import com.example.myplan.entity.User;
import com.example.myplan.resource.TaskResource;
import com.example.myplan.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private UserService userService;

    public TaskService(TaskRepository taskRepository, @Lazy UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public Task save(TaskResource resource) {
        User userAndJudge = userService.getUserAndJudge(resource.getUserId());
        if(null != userAndJudge){
            return taskRepository.save(Task.builder()
                    .content(resource.getContent())
                    .name(resource.getName())
                    .type(resource.getType())
                    .user(userAndJudge)
                    .build());
        }
        return null;
    }

    private Task getTaskAndJudge(Long id) {
        if(null == id)
            return null;
        Optional<Task> byId = taskRepository.findById(id);
        return byId.isEmpty() ? null : byId.get();
    }

    public void deleteTask(Long id, Long userId) {
        Task taskAndJudge = getTaskAndJudge(id);
        if (null != taskAndJudge) {
            taskAndJudge.setDeleted(userId.equals(taskAndJudge.getUser().getId()) || taskAndJudge.isDeleted());
            taskRepository.save(taskAndJudge);
        }
    }

    public Task UpdateTask(TaskResource resource) {
        Task taskAndJudge = getTaskAndJudge(resource.getTaskId());
        if (null != taskAndJudge) {
            Task task = taskAndJudge;
            if (task.getUser().getId() == resource.getUserId()) {
                task.setName(resource.getName());
                task.setContent(resource.getContent());
                task.setType(resource.getType());
                return taskRepository.save(task);
            }
        }
        return null;
    }

    public Task getById(Long id, Long userId) {
        Task taskAndJudge = getTaskAndJudge(id);
        if (null != taskAndJudge) {
            return userId.equals(taskAndJudge.getUser().getId()) ? taskAndJudge : null;
        }
        return null;
    }

    public List<Task> getAllTask(Long userId) {
        return taskRepository.findTasksByUserIdAndDeleted(userId, false);
    }

    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }
}
