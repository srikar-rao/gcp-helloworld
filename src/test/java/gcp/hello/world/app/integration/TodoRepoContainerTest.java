package gcp.hello.world.app.integration;

import gcp.hello.world.app.entity.Todo;
import gcp.hello.world.app.repostiory.TodoRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Testcontainers
@SpringJUnitConfig
public class TodoRepoContainerTest {

    //Require to run docker running in local

    @Autowired
    private TodoRepository todoRepository;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @BeforeAll
    static void initDb(){
        postgreSQLContainer.start();
    }

    @AfterAll
    static void tearDown(){
        postgreSQLContainer.stop();
    }

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

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

}
