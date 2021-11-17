package com.example.myplan.service;

import com.example.myplan.entity.Todo;
import com.example.myplan.entity.Users;
import com.example.myplan.entity.dto.TodoDTO;
import com.example.myplan.exception.TodoNotFoundException;
import com.example.myplan.repository.TodoRepository;
import com.example.myplan.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UsersRepository usersRepository;

    public Todo getById(Long id, Long userId) {
        List<Todo> todosByUsersIdAndDeleted = todoRepository.findTodosByUsersIdAndDeleted(userId, false); //findTodosByUsersIdAndIdAndDeleted
        for (Todo todo : todosByUsersIdAndDeleted)
            if (todo.getId().equals(id))
                return todo;

        return null;
    }

    public List<Todo> getAllTodo(Long userId) {
        return todoRepository.findTodosByUsersIdAndDeleted(userId, false);
    }

    public Todo save(TodoDTO dto) {
        Users users = usersRepository.findById(dto.getUserId()).get();
        Todo todo = Todo.builder()
                .content(dto.getContent())
                .name(dto.getName())
                .type(dto.getType())
                .users(users)
                .build();
        return todoRepository.save(todo);
    }

    public void deleteTodo(Long todoId) {
        try {
            Todo byId = todoRepository.findById(todoId).get();
            byId.setDeleted(true);
            todoRepository.save(byId);
        } catch (Throwable exception) {
            throw new TodoNotFoundException("This Todo Info Not Found!");
        }
    }

    public Todo UpdateTodo(TodoDTO dto) {
        try {
            Todo byId = todoRepository.findByIdAndDeleted(dto.getId(), false).get();
            byId.setName(dto.getName());
            byId.setContent(dto.getContent());
            byId.setType(dto.getType());
            return todoRepository.save(byId);
        } catch (Throwable exception) {
            throw new TodoNotFoundException("This Todo Info Not Found!");
        }

    }
}
