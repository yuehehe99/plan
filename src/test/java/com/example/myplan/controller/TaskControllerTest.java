package com.example.myplan.controller;

import com.example.myplan.entity.Task;
import com.example.myplan.entity.User;
import com.example.myplan.resource.MultiConditonReSource;
import com.example.myplan.service.TaskService;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "1234", authorities = {"ROLE_ADMIN"})
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private MultiConditonReSource multiConditonReSource;

    private Task task;
    private User user;

    @Before
    public void setUp() {
        multiConditonReSource = MultiConditonReSource.builder()
                .name("task")
                .content("task")
                .type("life")
                .userId(1L)
                .taskId(1L)
                .build();
        user = User.builder()
                .id(1L)
                .name("xiao")
                .deleted(false)
                .password("1234")
                .gender(false)
                .build();

        task = Task.builder()
                .user(user)
                .name("task")
                .content("task list")
                .type("life")
                .build();
    }

    @After
    public void afterEach() {
        Mockito.reset(taskService);
    }

    @Test
    public void should_save_task_successfully() throws Exception {
        String jsonTask = "{\"name\":\"task\"," +
                "\"content\":\"task list\"," +
                "\"type\":\"life\"," +
                "\"userId\":1}";

        mockMvc.perform(post("/task", jsonTask)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTask))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_update_task_successfully() throws Exception {
        String jsonTask = "{\"name\":\"task\"," +
                "\"content\":\"task list\"," +
                "\"type\":\"life\"," +
                "\"taskId\":1," +
                "\"userId\":1}";

        mockMvc.perform(put("/task", jsonTask)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTask))
                .andExpect(status().isOk());

//        verify(taskService).updateTask(taskResource);
    }

    @Test
    public void should_return_task_when_id_is_1L_and_user_is_admin() throws Exception {
        when(taskService.getById(1L, "admin")).thenReturn(task);

        mockMvc.perform(get("/task/one/{id}", 1L))
                .andExpect(status().isOk());

        verify(taskService).getById(1L, "admin");
    }

    @Test
    public void should_cancel_order_successfully() throws Exception {
        mockMvc.perform(patch("/task/{id}", 1L))
                .andExpect(status().isOk());

        verify(taskService).deleteTask(1L,"admin");
    }

    @Test
    public void should_return_all_task_when_given_userId() throws Exception {

        mockMvc.perform(get("/task", 1L))
                .andExpect(status().isOk());

        verify(taskService).getAllTask();
    }

    @Test
    public void should_return_all_task_when_given_userId_and_name() throws Exception {
        mockMvc.perform(get("/task/name/{name}", "task"))
                .andExpect(status().isOk());

        verify(taskService).getByName("task");
    }

    @Test
    public void should_return_all_task_when_given_conditions() throws Exception {
        String jsonTask = "{\"name\":\"task\"," +
                "\"content\":\"task\"," +
                "\"type\":\"life\"," +
                "\"taskId\":1," +
                "\"userId\":1}";
        mockMvc.perform(get("/task/conditions", jsonTask)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTask))
                .andExpect(status().isOk());
    }
}
