package com.example.myplan.repository;

import com.example.myplan.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends
        JpaRepository<Task, Long>,
        JpaSpecificationExecutor<Task> {
    Optional<Task> findByIdAndDeleted(Long taskId, boolean deleted);

//    Optional<List<Task>> findAllByDeleted(boolean deleted);

    List<Task> findAllByNameContaining(String name);


    List<Task> findTasksByUserIdAndDeleted(Long userId, boolean deleted);

}