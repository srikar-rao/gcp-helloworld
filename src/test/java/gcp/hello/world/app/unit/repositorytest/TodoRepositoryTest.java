package gcp.hello.world.app.unit.repositorytest;

import gcp.hello.world.app.entity.Todo;
import gcp.hello.world.app.repostiory.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void whenSave_thenFindById() {
        // Arrange
        Todo todo = new Todo();
        todo.setTitle("Test Todo");
        todo.setDescription("Test Description");
        todo.setCompleted(false);

        // Act
        Todo savedTodo = todoRepository.save(todo);
        Optional<Todo> retrievedTodo = todoRepository.findById(savedTodo.getId());

        // Assert
        assertThat(retrievedTodo).isPresent();
        assertThat(retrievedTodo.get().getTitle()).isEqualTo("Test Todo");
    }

    @Test
    public void whenFindAll_thenReturnAllTodos() {
        // Arrange
        Todo todo1 = new Todo();
        todo1.setTitle("Test Todo 1");
        todo1.setDescription("Test Description 1");
        todo1.setCompleted(false);

        Todo todo2 = new Todo();
        todo2.setTitle("Test Todo 2");
        todo2.setDescription("Test Description 2");
        todo2.setCompleted(true);

        todoRepository.save(todo1);
        todoRepository.save(todo2);

        // Act
        List<Todo> todos = todoRepository.findAll();

        // Assert
        assertThat(todos).hasSize(2);
        assertThat(todos).extracting(Todo::getTitle).containsExactlyInAnyOrder("Test Todo 1", "Test Todo 2");
    }

    @Test
    public void whenDelete_thenRemovedFromDatabase() {
        // Arrange
        Todo todo = new Todo();
        todo.setTitle("Test Todo");
        todo.setDescription("Test Description");
        todo.setCompleted(false);

        Todo savedTodo = todoRepository.save(todo);

        // Act
        todoRepository.delete(savedTodo);
        Optional<Todo> retrievedTodo = todoRepository.findById(savedTodo.getId());

        // Assert
        assertThat(retrievedTodo).isNotPresent();
    }
}
