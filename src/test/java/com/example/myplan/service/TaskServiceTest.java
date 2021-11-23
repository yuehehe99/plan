package com.example.myplan.service;

import com.example.myplan.entity.Task;
import com.example.myplan.entity.User;
import com.example.myplan.exception.TaskNotFoundException;
import com.example.myplan.repository.TaskRepository;
import com.example.myplan.repository.UserRepository;
import com.example.myplan.resource.MultiConditonReSource;
import com.example.myplan.resource.TaskResource;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
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
    @Mock
    private TaskNotFoundException taskNotFoundException;
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeEach
    public void setUp() {
        taskNotFoundException = new TaskNotFoundException("User is not found!");
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
        verify(userRepository).findByIdAndDeleted(1L, false);
        verify(taskRepository).save(task);
    }


    @Test
    public void should_return_task_when_update_task() {
        when(taskRepository.findByIdAndDeleted(1L, false)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        Task update = taskService.updateTask(taskResource);

        assertThat(update).isEqualTo(task);
        Mockito.verify(taskRepository).save(task);
    }

    @Test
    public void should_return_task_when_given_task_id_and_user_id() {
        when(taskRepository.findByIdAndDeleted(1L, false)).thenReturn(Optional.of(task));

        Task find = taskService.getById(1L, 1L);

        assertThat(find.getId()).isEqualTo(task.getId());
        Mockito.verify(taskRepository).findByIdAndDeleted(1L, false);
    }

    @Test
    public void should_return_task_when_given_task_name_and_user_id() {
        when(taskRepository.findAllByNameContaining("task")).thenReturn(List.of(task));

        List<Task> find = taskService.getByName("task", 1L);

        assertThat(find.contains(task));
        Mockito.verify(taskRepository).findAllByNameContaining("task");
    }

    @Test
    public void should_return_task_when_given_conditions() {
        when(userRepository.findByIdAndDeleted(1L, false)).thenReturn(Optional.of(user));
        when(taskRepository.findAll(specification, pageable)).thenReturn(page);

        Page<Task> find = taskService.getByConditions(multiConditonReSource);

//        assertThat(find.getContent().get(0).getId().equals(1));
        Mockito.verify(userRepository).findByIdAndDeleted(1L, false);
//        Mockito.verify(taskRepository).findAll(specification, pageable);
    }

    @Test
    public void should_return_all_task_when_given_user_id() {
        when(userRepository.findByIdAndDeleted(1L, false)).thenReturn(Optional.of(user));
        when(taskRepository.findTasksByUserIdAndDeleted(1L, false)).thenReturn(List.of(task));

        List<Task> find = taskService.getAllTask(1L);

        assertThat(find.contains(task));
        Mockito.verify(userRepository).findByIdAndDeleted(1L, false);
        Mockito.verify(taskRepository).findTasksByUserIdAndDeleted(1L, false);
    }

    @Test
    public void should_cancel_ask_successfully() throws Exception {
        when(taskRepository.findByIdAndDeleted(1L, false)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        taskService.deleteTask(1L, 1L);

        Mockito.verify(taskRepository).findByIdAndDeleted(1L, false);
        Mockito.verify(taskRepository).save(task);
    }

}
