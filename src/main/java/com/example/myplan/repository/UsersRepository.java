package com.example.myplan.repository;

import com.example.myplan.entity.Todo;
import com.example.myplan.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByIdAndDeleted(Long todoId, boolean deleted);

    Optional<List<Users>> findAllByDeletedEquals(boolean deleted);

}