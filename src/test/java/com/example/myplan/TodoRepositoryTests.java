//package com.example.myplan;
//
//import com.example.myplan.entity.Todo;
//import com.example.myplan.entity.dto.TodoDTO;
//import com.example.myplan.repository.TodoRepository;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.persistence.EntityManager;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class TodoRepositoryTests {
//
//    @Autowired
//    private TodoRepository orderRepository;
//    @MockBean
//    private EntityManager entityManager;
//
//    private Todo todo;
//    private Todo todo1;
//    private TodoDTO todoDTO;
//
//    @Before
//    public void setUp() {
//
//        todo = Todo.builder()
//                .id(1L)
//                .name("todo")
//                .content("todo list")
//                .type("life")
//                .deleted(false)
//                .build();
//
//        todo1 = Todo.builder()
//                .name("todo")
//                .content("todo list")
//                .deleted(false)
//                .build();
//
//        todoDTO = TodoDTO.builder()
//                .name("todo")
//                .content("todo list")
//                .type("life")
//                .userId(1L)
//                .build();
//    }
//
//    @Test
//    public void should_return_order_when_save_successfully() {
//        entityManager.persist(todoDTO);
//        entityManager.flush();
//        Todo savedOrder = orderRepository.save(todo1);
//        assertThat(savedOrder.getName()).isEqualTo(todo1.getName());
//        assertThat(savedOrder.getContent()).isEqualTo(todo1.getContent());
//    }
//}
