//package com.example.myplan;
//
//import com.example.myplan.entity.Todo;
//import com.example.myplan.entity.dto.TodoDTO;
//import com.example.myplan.repository.TodoRepository;
//import com.example.myplan.service.TodoService;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class TodoServiceTests {
//
//    @InjectMocks
//    private TodoService todoService;
//    @Mock
//    private TodoRepository todoRepository;
//
//    private Todo todo;
//    private Todo todo1;
//    private TodoDTO todoDTO;
//
//
//    @Before
//    public void setUp() {
//
//        todo = Todo.builder()
//                .id(1L)
//                .uuid(1L)
//                .name("todo")
//                .content("todo list")
//                .deleted(false)
//                .build();
//        todoDTO = TodoDTO.builder()
//                .name("todo")
//                .content("todo list")
//                .uuid(1L)
//                .build();
//    }
//
//    @Test
//    public void should_return_todo_when_save_todoDTO() {
//        Mockito.when(todoService.save(todoDTO)).thenReturn(todo);
//
//        Todo save = todoService.save(todoDTO);
//
//        assertThat(save).isEqualTo(todo);
//    }
//
//    @Test
//    public void should_return_todo_when_given_todo_id() {
//        Mockito.when(todoRepository.getById(1L)).thenReturn(todo);
//
//        Todo save = todoRepository.getById(1L);
//
//        assertThatThrownBy(() -> todoService.getById(5L)).isInstanceOf(Throwable.class).hasMessage("This Todo Info Not Found!");
//        assertThat(save).isEqualTo(todo);
//        Mockito.verify(todoRepository).getById(1L);
//    }
//
//    @Test
//    public void should_return_exception_when_todo_id_is_null() {
//        Mockito.when(todoRepository.getById(999L)).thenReturn(null);
//
//        Todo save = todoRepository.getById(999L);
//
//        assertThat(save).isEqualTo(null);
//    }
//
//
//}
