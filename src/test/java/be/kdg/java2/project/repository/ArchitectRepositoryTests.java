package be.kdg.java2.project.repository;

import be.kdg.java2.project.domain.Architect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ArchitectRepositoryTests {

    @Autowired
    private ArchitectRepository architectRepository;

    @Test
    public void saveArchitectShouldFailWhenNameIsNotUnique() {

        // Arrange
        var architect1 = new Architect("architect1", LocalDate.of(2001, 9, 10), 15);
        architectRepository.save(architect1);

        // Assert
        assertEquals(architectRepository.findArchitectByNameCompany("architect1"), architect1);
        assertThrows(DataIntegrityViolationException.class, () -> {
            // Act
            architectRepository.save(new Architect("architect1", LocalDate.of(2018, 3, 25), 70));
        });
    }

    @Test
    @Transactional
    public void saveArchitectShouldPassWhenNameUnique() {

        // Arrange
        var architect2 = new Architect("architect2", LocalDate.of(2001, 9, 10), 15);
        var architect3 = new Architect("architect3", LocalDate.of(2018, 3, 25), 70);
        architectRepository.save(architect2);

        // Act
        architectRepository.save(architect3);

        // Assert
        assertEquals(architectRepository.findArchitectByNameCompany("architect3"), architect3);
        var architect3Found = architectRepository.findArchitectByNameCompany("architect3");
        assertEquals("architect3", architect3Found.getNameCompany());
        assertEquals(70, architect3Found.getNumberOfEmployees());
    }
}