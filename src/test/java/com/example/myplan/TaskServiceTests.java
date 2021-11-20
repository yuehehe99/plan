package com.example.myplan;

import com.example.myplan.entity.Task;
import com.example.myplan.entity.User;
import com.example.myplan.repository.TaskRepository;
import com.example.myplan.resource.TaskResource;
import com.example.myplan.service.TaskService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTests {

    @InjectMocks
    private TaskService taskService;
    @Mock
    private TaskRepository taskRepository;

    private Task task;
    private User user;
    private TaskResource taskResource;

    @Before
    public void setUp() {
        user = User.builder()
                .id(1L)
                .gender(false)
                .name("ming")
                .gender(false)
                .build();

        task = Task.builder()
                .id(1L)
                .user(user)
                .name("todo")
                .content("todo list")
                .deleted(false)
                .build();
        taskResource = TaskResource.builder()
                .name("todo")
                .content("todo list")
                .userId(1L)
                .taskId(1L)
                .build();

    }

    //@Test
    public void should_return_todo_when_save_todoDTO() {
        Task build = Task.builder()
                .id(1L)
                .user(user)
                .name("todo")
                .content("todo list")
                .deleted(false)
                .build();
        TaskResource taskResource = TaskResource.builder()
                .name("todo")
                .content("todo list")
                .userId(1L)
                .taskId(1L)
                .build();
        Mockito.when(taskRepository.save(build)).thenReturn(task);

        Task save = taskService.save(taskResource);

        assertThat(save).isEqualTo(build);
        Mockito.verify(taskRepository).save(build);
//        Mockito.verify(userService).getUserAndJudge(1L);
    }

    @Test
    public void should_return_todo_when_given_todo_id_and_user_id() {
        Mockito.when(taskRepository.findByIdAndDeleted(1L, false)).thenReturn(Optional.of(task));

        Task find = taskService.getById(1L, 1L);

        assertThat(find.getId()).isEqualTo(task.getId());
        Mockito.verify(taskRepository).findByIdAndDeleted(1L, false);
    }

    @Test
    public void should_cancel_order_successfully() throws Exception {
        Mockito.when(taskRepository.findByIdAndDeleted(1L,false)).thenReturn(Optional.of(task));
//        Mockito.when(taskRepository.save().thenReturn(Optional.of(task));

        taskService.deleteTask(1L,1L);

        Mockito.verify(taskRepository).findByIdAndDeleted(1L,false);
    }


}
