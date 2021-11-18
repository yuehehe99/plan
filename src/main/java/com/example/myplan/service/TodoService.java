package com.example.myplan.service;

import com.example.myplan.entity.Todo;
import com.example.myplan.entity.Users;
import com.example.myplan.entity.dto.TodoDTO;
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

    public void deleteTodo(Long id, Long userId) {
        List<Todo> todosByUsersIdAndDeleted = todoRepository.findTodosByUsersIdAndDeleted(userId, false); //findTodosByUsersIdAndIdAndDeleted
        for (Todo todo : todosByUsersIdAndDeleted)
            if (todo.getId().equals(id)){
                todo.setDeleted(true);
                todoRepository.save(todo);
            }
    }

    public Todo UpdateTodo(TodoDTO dto) {

        List<Todo> todosByUsersIdAndDeleted = todoRepository.findTodosByUsersIdAndDeleted(dto.getUserId(), false); //findTodosByUsersIdAndIdAndDeleted
        for (Todo todo : todosByUsersIdAndDeleted)
            if (todo.getId().equals(dto.getId())){
                todo.setName(dto.getName());
                todo.setContent(dto.getContent());
                todo.setType(dto.getType());
                return todoRepository.save(todo);
            }
        return null;
//        try {
//            Todo byId = todoRepository.findByIdAndDeleted(dto.getId(), false).get();
//            byId.setName(dto.getName());
//            byId.setContent(dto.getContent());
//            byId.setType(dto.getType());
//            return todoRepository.save(byId);
//        } catch (Throwable exception) {
//            throw new TodoNotFoundException("This Todo Info Not Found!");
//        }

    }
}
