package com.example.myplan;

import com.example.myplan.entity.Task;
import com.example.myplan.entity.User;
import com.example.myplan.resource.TaskResource;
import com.example.myplan.service.TaskService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class TaskControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private Task task;
    @MockBean
    private User user;

    @MockBean
    private TaskResource taskResource;

//    @Rule
//    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        user = User.builder()
                .id(1L)
                .deleted(false)
                .gender(false)
                .build();

        task = Task.builder()
                .user(user)
                .name("task")
                .content("task list")
                .deleted(false)
                .build();
        taskResource = TaskResource.builder()
                .name("task")
                .content("task list")
                .userId(1L)
                .build();
    }

    //@After
    public void afterEach() {
        Mockito.reset(taskService);
    }

    @Test
    public void should_save_task_successfully() throws Exception {

        String jsonTask = "{\"name\":\"task\"," +
                "\"userId\":1L," +
                "\"content\":\"task list\"}";
        Mockito.when(taskService.save(taskResource)).thenReturn(task);

        mockMvc.perform(post("/task", jsonTask)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTask))
                .andExpect(status().isCreated());

        Mockito.verify(taskService).save(taskResource);
    }

    @Test
    public void should_return_todo_when_id_is_1L_and_userId_is_1L() throws Exception {
        Mockito.when(taskService.getById(1L, 1L)).thenReturn(task);

        mockMvc.perform(get("/task/1/1"))
                .andExpect(status().isOk());

        Mockito.verify(taskService).getById(1L, 1L);
    }

    @Test
    public void should_cancel_order_successfully() throws Exception {
        mockMvc.perform(patch("/task/1/1"))
                .andExpect(status().isOk());

        Mockito.verify(taskService).deleteTask(1L,1L);
    }

}
