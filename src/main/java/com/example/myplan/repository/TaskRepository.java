package com.example.myplan.repository;

import com.example.myplan.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    Task findByIdAndDeleted(Long taskId, boolean deleted);

    List<Task> findAllByNameContaining(String name);

    List<Task> findTasksByUserIdAndDeleted(Long userId, boolean deleted);

}