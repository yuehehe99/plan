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
        User user = userRepository.findByIdAndDeleted(resource.getUserId(), false);
        if (null != user)
            return taskRepository.save(Task.builder()
                    .content(resource.getContent())
                    .name(resource.getName())
                    .type(resource.getType())
                    .user(user)
                    .build());

        throw new ResourceNotFoundException("User is not found!");
    }

    public void deleteTask(Long id, String name) {
        Optional<User> byName = userRepository.findByName(name);
        User user = byName.isEmpty() ? null : byName.get();
        Task task = taskRepository.findByIdAndDeleted(id, false);

        if (null != task && null != user) {
            boolean equals = user.getId().equals(task.getUser().getId());
            if (equals) {
                task.setDeleted(equals || task.isDeleted());
                taskRepository.save(task);
            } else
                throw new ResourceNotFoundException("You can not delete other's task!");
        } else
            throw new ResourceNotFoundException("Task is not found!");
    }

    public Task updateTask(TaskResource resource) {
        Task task = taskRepository.findByIdAndDeleted(resource.getTaskId(), false);
        if (null != task) {
            if (Objects.equals(task.getUser().getId(), resource.getUserId())) {
                task.setName(resource.getName());
                task.setContent(resource.getContent());
                task.setType(resource.getType());
                return taskRepository.save(task);
            } else
                throw new ResourceNotFoundException("User is not found!");
        }
        throw new ResourceNotFoundException("Task is not found!");
    }

    public Task getById(Long id, Long userId) {
        Task task = taskRepository.findByIdAndDeleted(id, false);
        if (null != task) {
            return userId.equals(task.getUser().getId()) ? task : null;
        }
        throw new ResourceNotFoundException("Task is not found!");
    }

    public List<Task> getAllTask(Long userId) {
        User user = userRepository.findByIdAndDeleted(userId, false);
        if (null != user)
            return taskRepository.findTasksByUserIdAndDeleted(userId, false);

        throw new ResourceNotFoundException("User is not found!");
    }

    public List<Task> getByName(String name, Long userId) {
        List<Task> allByNameContaining = taskRepository.findAllByNameContaining(name);
        if (!allByNameContaining.isEmpty())
            return allByNameContaining.stream()
                    .filter(task -> Objects.equals(task.getUser().getId(), userId))
                    .collect(Collectors.toList());

        throw new ResourceNotFoundException("Task is not found!");
    }

    public Page<Task> getByConditions(MultiConditonReSource resource) {
        User user = userRepository.findByIdAndDeleted(resource.getUserId(), false);
        if (null == user)
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

