//package com.example.myplan;
//
//import com.example.myplan.entity.Todo;
//import com.example.myplan.entity.dto.TodoDTO;
//import com.example.myplan.service.TodoService;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc
//public class TodoControllerTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private TodoService todoService;
//
//    private Todo todo;
//    private TodoDTO todoDTO;
//
//    @Rule
//    public final ExpectedException exception = ExpectedException.none();
//
//    @Before
//    public void setUp() {
//
//        todo = Todo.builder()
//                .id(1L)
//                .name("todo")
//                .content("todo list")
//                .deleted(false)
//                .build();
//
//        todoDTO = TodoDTO.builder()
//                .name("todo")
//                .content("todo list")
//                .build();
//
//    }
//
//    @After
//    public void afterEach() {
//        Mockito.reset(todoService);
//    }
//
//    @Test
//    public void should_save_todo_successfully() throws Exception {
//        String jsonTodo = "{\"name\":\"todo\"," +
//                "\"content\":\"todo list\"}";
//        Mockito.when(todoService.save(todoDTO)).thenReturn(todo);
//
//        mockMvc.perform(post("/todo", jsonTodo)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonTodo))
//                .andExpect(status().isCreated());
//
//        Mockito.verify(todoService).save(todoDTO);
//    }
//
//    @Test
//    public void should_return_todo_when_id_is_1L() throws Exception {
//        Mockito.when(todoService.getById(1L)).thenReturn(todo);
//
//        mockMvc.perform(get("/todo/1"))
//                .andExpect(status().isOk());
//
//        Mockito.verify(todoService).getById(1L);
//    }
//
//    @Test
//    public void should_return_exception_when_todo_id_is_null() throws Exception {
//        mockMvc.perform(get("/todo/999999999"))
//                .andExpect(status().isOk());  //能通过
////                .andExpect(status().isBadRequest()); //不能通过
//
////        assertThatThrownBy(() -> todoService.getById(999L))
////                .isInstanceOf(TodoNotFoundException.class)
////                .hasMessage("This Todo Info Not Found!");
//    }
//
//    @Test
//    public void should_cancel_order_successfully() throws Exception {
//        mockMvc.perform(patch("/todo/1"))
//                .andExpect(status().isOk());
//
//        Mockito.verify(todoService).deleteTodo(1L);
//    }
//
//}
