package com.example.myplan.service;

import com.example.myplan.entity.Task;
import com.example.myplan.entity.User;
import com.example.myplan.exception.ResourceNotFoundException;
import com.example.myplan.repository.TaskRepository;
import com.example.myplan.repository.UserRepository;
import com.example.myplan.resource.MultiConditonReSource;
import com.example.myplan.resource.TaskResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserRepository userRepository;

    private Task task;
    private User user;

    private TaskResource taskResource;

    private MultiConditonReSource multiConditonReSource;

    @BeforeEach
    public void setUp() {
        multiConditonReSource = MultiConditonReSource.builder()
                .name("task")
                .content("task")
                .type("life")
                .userId(1L)
                .taskId(1L)
                .pageNumber(0)
                .pageSize(2)
                .build();

        user = User.builder()
                .id(1L)
                .gender(false)
                .name("admin")
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
                .taskId(1L)
                .build();

    }

    @Test
    public void should_return_task_when_save_task() {
        when(userRepository.findByName("admin")).thenReturn(Optional.ofNullable(user));
        when(taskRepository.save(task)).thenReturn(task);

        List<Task> save = taskService.save(taskResource,"admin");

        assertThat(save.get(0)).isEqualTo(task);
        assertNotNull(save);
        verify(userRepository).findByName("admin");
        verify(taskRepository).save(task);
    }

    @Test
    public void should_cancel_task_successfully() {
        when(userRepository.findByName("admin")).thenReturn(Optional.ofNullable(user));
        when(taskRepository.findByIdAndDeleted(1L, false)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);

        taskService.deleteTask(1L, "admin");

        verify(userRepository).findByName("admin");
        verify(taskRepository).findByIdAndDeleted(1L, false);
        verify(taskRepository).save(task);
    }

    @Test
    public void should_return_task_when_update_task() {
        when(taskRepository.findByIdAndDeleted(1L, false)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);

        List<Task> update = taskService.updateTask(taskResource);

        assertThat(update.get(0)).isEqualTo(task);
        verify(taskRepository).save(task);
    }

    @Test
    public void should_return_task_when_given_task_id() {
        when(userRepository.findByName("admin")).thenReturn(Optional.ofNullable(user));
        when(taskRepository.findByIdAndDeleted(1L, false)).thenReturn(task);

        Task find = taskService.getById(1L, "admin");

        assertThat(find.getId()).isEqualTo(task.getId());
        verify(userRepository).findByName("admin");
        verify(taskRepository).findByIdAndDeleted(1L, false);
    }

    @Test
    public void should_return_all_task_when_given_userId() throws Exception {
//        when(userRepository.findByIdAndDeleted(1L, false)).thenReturn(user);
        when(taskRepository.findAll()).thenReturn(List.of(task));

        List<Task> find = taskService.getAllTask();

        assertThat(find.contains(task));
        assertThat(find.get(0).getId()).isEqualTo(task.getId());
//        verify(userRepository).findByIdAndDeleted(1L, false);
        verify(taskRepository).findAll();
    }

    @Test
    public void should_return_task_when_given_task_name_and_user_id() {
        when(taskRepository.findAllByNameContaining("task")).thenReturn(List.of(task));

        List<Task> find = taskService.getByName("task");

        assertThat(find.contains(task));
        verify(taskRepository).findAllByNameContaining("task");
    }

    @Test
    public void should_return_task_when_given_conditions() {
        when(userRepository.findByIdAndDeleted(1L, false)).thenReturn(user);
//        when(taskRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(any(Page.class));

        Page<Task> find = taskService.getByConditions(multiConditonReSource);

        verify(userRepository).findByIdAndDeleted(1L, false);
        verify(taskRepository).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    public void should_return_exception_when_update_and_given_wrong_taskId() {
        when(taskRepository.findByIdAndDeleted(123456L,false)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
                () -> taskService.updateTask(TaskResource.builder().taskId(123456L).userId(1L).build()));

        verify(taskRepository).findByIdAndDeleted(123456L,false);
    }

    @Test
    public void should_return_exception_when_delete_and_given_wrong_taskId() {
        when(taskRepository.findByIdAndDeleted(123456L,false)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
                () -> taskService.deleteTask(123456L, "admin"));

        verify(taskRepository).findByIdAndDeleted(123456L,false);
    }

    @Test
    public void should_return_exception_when_delete_and_given_wrong_user() {
        when(taskRepository.findByIdAndDeleted(1L,false)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
                () -> taskService.deleteTask(1L, "haha"));

        verify(taskRepository).findByIdAndDeleted(1L,false);
    }

    @Test
    public void should_return_exception_when_get_and_given_wrong_taskId() {
        when(taskRepository.findByIdAndDeleted(123456L,false)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
                () -> taskService.getById(123456L, "admin"));

        verify(taskRepository).findByIdAndDeleted(123456L,false);
    }

    @Test
    public void should_return_exception_when_get_and_given_wrong_user() {
        when(taskRepository.findByIdAndDeleted(1L,false)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
                () -> taskService.getById(1L, "haha"));

        verify(taskRepository).findByIdAndDeleted(1L,false);
    }

}
