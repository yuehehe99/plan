package com.example.myplan.repository;

import com.example.myplan.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdAndDeleted(Long userId, boolean deleted);

//    List<User> findAllAndDeleted(boolean deleted);

    Optional<User> findByName(String username);
//    User findById(Long id);
}