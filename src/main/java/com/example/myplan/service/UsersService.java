package com.example.myplan.service;

import com.example.myplan.entity.Todo;
import com.example.myplan.entity.Users;
import com.example.myplan.entity.dto.TodoDTO;
import com.example.myplan.entity.dto.UsersDTO;
import com.example.myplan.exception.TodoNotFoundException;
import com.example.myplan.repository.TodoRepository;
import com.example.myplan.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;



    public Users save(Users users) {
        return usersRepository.save(users);
    }

    public void deleteUser(Long id) {
        try {
            Users byId = usersRepository.findById(id).get();
            byId.setDeleted(true);
            usersRepository.save(byId);
        } catch (Throwable exception) {
            throw new TodoNotFoundException("This Todo Info Not Found!");
        }
    }

    public Users UpdateUser(UsersDTO dto) {
        try {
            Users byId = usersRepository.findByIdAndDeleted(dto.getId(), false).get();
            byId.setName(dto.getName());
            byId.setGender(dto.isGender());
            return usersRepository.save(byId);
        } catch (Throwable exception) {
            throw new TodoNotFoundException("This Todo Info Not Found!");
        }
    }

    public Users getById(Long id) {
        return usersRepository.findByIdAndDeleted(id, false).orElseThrow(() -> new TodoNotFoundException("This User Info Not Found!"));
    }


    public List<Users> getAllUsers() {
        return usersRepository.findAllByDeletedEquals(false).get();
    }

}
