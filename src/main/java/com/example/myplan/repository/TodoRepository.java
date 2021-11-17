package com.example.myplan.repository;

import com.example.myplan.entity.Todo;
import com.example.myplan.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    Optional<Todo> findByIdAndDeleted(Long todoId, boolean deleted);

    Optional<List<Todo>> findAllByDeletedEquals(boolean deleted);

    List<Todo> findTodosByUsersIdAndDeleted(Long userId, boolean deleted);

    Optional<Todo> findByUsersIdAndDeleted(Long userId, boolean deleted);
}