package br.ce.wcaquino.taskbackend.controller;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class TaskControllerTest {

    @Mock
    private TaskRepo taskRepo;

    @InjectMocks
    private TaskController controller;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void naoDeveSalvarTaskSemDescricao() {
        Task toDo = new Task();
        toDo.setDueDate(LocalDate.now());
        try {
            controller.save(toDo);
            fail();
        } catch (ValidationException e) {
            assertEquals("Fill the task description", e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarTaskSemData() {
        Task toDo = new Task();
        toDo.setTask("Descricao");
        try {
            controller.save(toDo);
            fail();
        } catch (ValidationException e) {
            assertEquals("Fill the due date", e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarTaskComDataPassada() {
        Task toDo = new Task();
        toDo.setTask("Descricao");
        toDo.setDueDate(LocalDate.of(2010,01,01));
        try {
            controller.save(toDo);
            fail();
        } catch (ValidationException e) {
            assertEquals("Due date must not be in past", e.getMessage());
        }
    }

    @Test
    public void deveSalvarTaskComSucesso() throws ValidationException {
        Task toDo = new Task();
        toDo.setTask("Descricao");
        toDo.setDueDate(LocalDate.now());
        controller.save(toDo);
        Mockito.verify(taskRepo).save(toDo);
    }
}
