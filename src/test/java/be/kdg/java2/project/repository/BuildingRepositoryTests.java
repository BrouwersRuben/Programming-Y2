package be.kdg.java2.project.repository;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.domain.Building;
import be.kdg.java2.project.domain.BuildingType;
import be.kdg.java2.project.domain.TypeOfBuilding;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BuildingRepositoryTests {

    @Autowired
    private ArchitectRepository architectRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @BeforeEach
    void setUp() {
        var architect1 = new Architect("architect1", LocalDate.of(2001, 9, 10), 15);

        var building1 = new Building("building1", "Antwerp, BE", 123, new TypeOfBuilding(BuildingType.HIGHRISE));
        var building2 = new Building("building2", "Brussels, BE", 9, new TypeOfBuilding(BuildingType.SLUMS));

        architect1.addBuildings(List.of(building1, building2));

        building1.addArchitect(architect1);
        building2.addArchitect(architect1);

        architectRepository.save(architect1);

        buildingRepository.save(building1);
        buildingRepository.save(building2);
    }

    @Test
    public void removingABuildingShouldNotRemoveItsArchitect() {
        // Arrange
        var building = buildingRepository.findById(1).orElse(null);
        var architect = building.getArchitects().stream().findFirst();

        // Act
        buildingRepository.delete(building);

        // Assert
        assertTrue(architectRepository.findById(building.getArchitects().stream().findFirst().get().getId()).isPresent());
        assertTrue(buildingRepository.findById(building.getId()).isEmpty());

    }
}