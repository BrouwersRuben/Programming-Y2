package be.kdg.java2.project.services;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.repository.ArchitectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
class ArchitectServiceTests {

    @Autowired
    private ArchitectService architectService;

    @Autowired
    private ArchitectRepository architectRepository;

    @Test
    public void deleteQuestionShouldPassIfArchitectExists() {
        // Arrange
        var architect = new Architect("architectTest", LocalDate.of(2000, 1,1), 1);

        architectService.addArchitect(architect);

        // Assert
        assertTrue(architectRepository.findById(architect.getId()).isPresent());

        // Act
        architectService.delete(1);

        // Assert
        assertTrue(architectRepository.findById(architect.getId()).isEmpty());
    }

    @Test
    public void deleteQuestionShouldFailIfArchitectDoesNotExist() {
        // Arrange
        var falseID = 69;
        var architect = new Architect("architectTest", LocalDate.of(2000, 1,1), 1);

        architectService.addArchitect(architect);

        // Assert
        assertThrows(NoSuchElementException.class, () -> {
            // Act
            architectService.delete(falseID);
        });
    }
}