package com.example.myplan.service;

import com.example.myplan.entity.Task;
import com.example.myplan.entity.User;
import com.example.myplan.exception.ResourceNotFoundException;
import com.example.myplan.repository.UserRepository;
import com.example.myplan.resource.MultiConditonReSource;
import com.example.myplan.resource.TaskResource;
import com.example.myplan.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public Task save(TaskResource resource) {
        Optional<User> byIdAndDeleted = userRepository.findByIdAndDeleted(resource.getUserId(), false);
        User userAndJudge = byIdAndDeleted.isEmpty() ? null : byIdAndDeleted.get();

        if (userAndJudge != null)
            return taskRepository.save(Task.builder()
                    .content(resource.getContent())
                    .name(resource.getName())
                    .type(resource.getType())
                    .user(userAndJudge)
                    .build());

        throw new ResourceNotFoundException("User is not found!");
    }

    public Task getTaskAndJudge(Long id) {
        if (null == id)
            return null;
        Optional<Task> byId = taskRepository.findByIdAndDeleted(id, false);
        return byId.isEmpty() ? null : byId.get();
    }

    public void deleteTask(Long id, Long userId) {
        Task taskAndJudge = getTaskAndJudge(id);
        if (null != taskAndJudge) {
            taskAndJudge.setDeleted(userId.equals(taskAndJudge.getUser().getId()) || taskAndJudge.isDeleted());
            taskRepository.save(taskAndJudge);
        } else
            throw new ResourceNotFoundException("Task is not found!");

    }

    public Task updateTask(TaskResource resource) {
        Task taskAndJudge = getTaskAndJudge(resource.getTaskId());
        if (null != taskAndJudge) {
            if (Objects.equals(taskAndJudge.getUser().getId(), resource.getUserId())) {
                taskAndJudge.setName(resource.getName());
                taskAndJudge.setContent(resource.getContent());
                taskAndJudge.setType(resource.getType());
                return taskRepository.save(taskAndJudge);
            }else
                throw new ResourceNotFoundException("User is not found!");
        }
        throw new ResourceNotFoundException("Task is not found!");
    }

    public Task getById(Long id, Long userId) {
        Task taskAndJudge = getTaskAndJudge(id);
        if (null != taskAndJudge) {
            return userId.equals(taskAndJudge.getUser().getId()) ? taskAndJudge : null;
        }
        throw new ResourceNotFoundException("Task is not found!");
    }

    public List<Task> getAllTask(Long userId) {
        Optional<User> byIdAndDeleted = userRepository.findByIdAndDeleted(userId, false);
        if (!byIdAndDeleted.isEmpty()) {
            return taskRepository.findTasksByUserIdAndDeleted(userId, false);
        }

        throw new ResourceNotFoundException("User is not found!");
    }

    public List<Task> getByName(String name, Long userId) {
        List<Task> taskAndJudge = taskRepository.findAllByNameContaining(name);
        if (!taskAndJudge.isEmpty())
            return taskAndJudge.stream()
                    .filter(task -> Objects.equals(task.getUser().getId(), userId))
                    .collect(Collectors.toList());

        throw new ResourceNotFoundException("Task is not found!");
    }

    public Page<Task> getByConditions(MultiConditonReSource resource) {
        Optional<User> byIdAndDeleted = userRepository.findByIdAndDeleted(resource.getUserId(), false);
        if (byIdAndDeleted.isEmpty())
            throw new ResourceNotFoundException("User is not found!");

        Specification<Task> specification = (root, query, builder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (resource.getTaskId() != null && !"".equals(resource.getTaskId())) {
                predicateList.add(builder.equal(root.get("taskId").as(Long.class), resource.getTaskId()));
            }

            if (resource.getName() != null && !"".equals(resource.getName())) {
                predicateList.add(builder.like(root.get("name").as(String.class), "%" + resource.getName() + "%"));
            }
            if (resource.getContent() != null && !"".equals(resource.getContent())) {
                predicateList.add(builder.like(root.get("content").as(String.class), "%" + resource.getContent() + "%"));
            }
            if (resource.getType() != null && !"".equals(resource.getType())) {
                predicateList.add(builder.like(root.get("type").as(String.class), "%" + resource.getType() + "%"));
            }
            predicateList.add(builder.equal(root.get("deleted").as(Boolean.class), false));

            if (resource.getUserId() != null && !"".equals(resource.getUserId())) {
                predicateList.add(builder.equal(root.get("user").get("id").as(Long.class), resource.getUserId()));
            }
            Predicate[] pre = new Predicate[predicateList.size()];
            pre = predicateList.toArray(pre);
//            return builder.and(pre);
            return query.where(pre).getRestriction();
        };

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(resource.getPageNumber(), resource.getPageSize(), sort);
        return taskRepository.findAll(specification, pageable);
    }

}

