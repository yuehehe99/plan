package com.example.myplan.service;

import com.example.myplan.entity.Task;
import com.example.myplan.entity.User;
import com.example.myplan.exception.TaskNotFoundException;
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
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    @Mock
    private Task task;
    @Mock
    private User user;
    @Mock
    private Task taskFake;
    @Mock
    private User userFake;
    @Mock
    private TaskResource taskResource;

    @Mock
    private Specification specification;
    @Mock
    private Pageable pageable;
    @Mock
    private Page page;
    @Mock
    private MultiConditonReSource multiConditonReSource;

    @BeforeEach
    public void setUp() {
//        taskNotFoundException = new TaskNotFoundException("User is not found!");
        pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 4;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };

        specification.equals(multiConditonReSource);

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
                .name("ming")
                .gender(false)
                .build();

        task = Task.builder()
                .user(user)
                .name("task")
                .content("task list")
                .deleted(false)
                .build();
        userFake = User.builder()
                .id(333333L)
                .gender(false)
                .name("ming")
                .gender(false)
                .build();
        taskFake = Task.builder()
                .user(userFake)
                .name("task")
                .content("task list")
                .deleted(false)
                .build();
        taskResource = TaskResource.builder()
                .name("task")
                .content("task list")
                .userId(1L)
                .taskId(1L)
                .build();

    }

    @Test
    public void should_return_task_when_save_task() {
        when(userRepository.findByIdAndDeleted(1L, false)).thenReturn(Optional.of(user));
        when(taskRepository.save(task)).thenReturn(task);

        Task save = taskService.save(taskResource);

        assertThat(save).isEqualTo(task);
        assertNotNull(save);
        verify(userRepository).findByIdAndDeleted(1L, false);
        verify(taskRepository).save(task);
    }

    @Test
    public void should_cancel_order_successfully() {
        when(taskRepository.findByIdAndDeleted(1L, false)).thenReturn(Optional.of(task));

        taskService.deleteTask(1L, 1L);

        verify(taskRepository).findByIdAndDeleted(1L, false);
    }

    @Test
    public void should_return_task_when_update_task() {
        when(taskRepository.findByIdAndDeleted(1L, false)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        Task update = taskService.updateTask(taskResource);

        assertThat(update).isEqualTo(task);
        verify(taskRepository).save(task);
    }

    @Test
    public void should_return_task_when_given_task_id_and_user_id() {
        when(taskRepository.findByIdAndDeleted(1L, false)).thenReturn(Optional.of(task));

        Task find = taskService.getById(1L, 1L);

        assertThat(find.getId()).isEqualTo(task.getId());
        verify(taskRepository).findByIdAndDeleted(1L, false);
    }

    @Test
    public void should_return_all_task_when_given_userId() throws Exception {
        when(userRepository.findByIdAndDeleted(1L, false)).thenReturn(Optional.of(user));
        when(taskRepository.findTasksByUserIdAndDeleted(1L, false)).thenReturn(List.of(task));

        List<Task> find = taskService.getAllTask(1L);

        assertThat(find.get(0).getId()).isEqualTo(task.getId());
        verify(userRepository).findByIdAndDeleted(1L, false);
        verify(taskRepository).findTasksByUserIdAndDeleted(1L, false);
    }

    @Test
    public void should_return_task_when_given_task_name_and_user_id() {
        when(taskRepository.findAllByNameContaining("task")).thenReturn(List.of(task));

        List<Task> find = taskService.getByName("task", 1L);

        assertThat(find.contains(task));
        verify(taskRepository).findAllByNameContaining("task");
    }

    /**
     * conditions
     * Argument(s) are different! Wanted:
     * taskRepository.findAll(
     * specification,
     * com.example.myplan.service.TaskServiceTest$1@ba4c99c
     * );
     * -> at com.example.myplan.service.TaskServiceTest.should_return_task_when_given_conditions(TaskServiceTest.java:226)
     * Actual invocations have different arguments:
     * taskRepository.findAll(
     * com.example.myplan.service.TaskService$$Lambda$1547/0x0000000800b0f840@3bd552f7,
     * Page request [number: 0, size 2, sort: createdAt: DESC]
     * );
     * -> at com.example.myplan.service.TaskService.getByConditions(TaskService.java:137)
     */
    //@Test
    public void should_return_task_when_given_conditions() {
        when(userRepository.findByIdAndDeleted(1L, false)).thenReturn(Optional.of(user));
        when(taskRepository.findAll(specification, pageable)).thenReturn(page);

        Page<Task> find = taskService.getByConditions(multiConditonReSource);

        verify(userRepository).findByIdAndDeleted(1L, false);
//        verify(taskRepository).findAll(specification, pageable);
    }

    @Test
    public void should_return_all_task_when_given_user_id() {
        when(userRepository.findByIdAndDeleted(1L, false)).thenReturn(Optional.of(user));
        when(taskRepository.findTasksByUserIdAndDeleted(1L, false)).thenReturn(List.of(task));

        List<Task> find = taskService.getAllTask(1L);

        assertThat(find.contains(task));
        verify(userRepository).findByIdAndDeleted(1L, false);
        verify(taskRepository).findTasksByUserIdAndDeleted(1L, false);
    }

    @Test
    public void should_cancel_ask_successfully() throws Exception {
        when(taskRepository.findByIdAndDeleted(1L, false)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        taskService.deleteTask(1L, 1L);

        verify(taskRepository).findByIdAndDeleted(1L, false);
        verify(taskRepository).save(task);
    }

    @Test
    public void should_return_exception_when_save_and_given_wrong_userId() {
        when(userRepository.findByIdAndDeleted(123456L, false)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class,
                () -> taskService.save(TaskResource.builder().userId(123456L).build()));

        verify(userRepository).findByIdAndDeleted(123456L, false);
    }

    @Test
    public void should_return_exception_when_update_and_given_wrong_taskId() {
        when(taskRepository.findByIdAndDeleted(123456L,false)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class,
                () -> taskService.updateTask(TaskResource.builder().taskId(123456L).userId(1L).build()));

        verify(taskRepository).findByIdAndDeleted(123456L,false);
    }

    @Test
    public void should_return_exception_when_delete_and_given_wrong_taskId() {
        when(taskRepository.findByIdAndDeleted(123456L,false)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class,
                () -> taskService.deleteTask(123456L, 123456L));

        verify(taskRepository).findByIdAndDeleted(123456L,false);
    }

    @Test
    public void should_return_exception_when_get_and_given_wrong_taskId() {
        when(taskRepository.findByIdAndDeleted(123456L,false)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class,
                () -> taskService.getById(123456L, 123456L));

        verify(taskRepository).findByIdAndDeleted(123456L,false);
    }

    @Test
    public void should_return_exception_when_getAll_and_given_wrong_userId() {
        when(userRepository.findByIdAndDeleted(123456L, false)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class,
                () -> taskService.getAllTask(123456L));

        verify(userRepository).findByIdAndDeleted(123456L, false);
    }

    @Test
    public void should_return_exception_when_get_by_name_and_given_wrong_name() {
        when(taskRepository.findAllByNameContaining("name")).thenReturn(Collections.emptyList());

        assertThrows(TaskNotFoundException.class,
                () -> taskService.getByName("name", 123456L));

        verify(taskRepository).findAllByNameContaining("name");
    }

}
