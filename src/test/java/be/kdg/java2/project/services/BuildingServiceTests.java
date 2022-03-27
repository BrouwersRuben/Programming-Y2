package be.kdg.java2.project.services;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.domain.Building;
import be.kdg.java2.project.domain.BuildingType;
import be.kdg.java2.project.domain.TypeOfBuilding;
import be.kdg.java2.project.exceptions.LocationNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BuildingServiceTests {

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private ArchitectService architectService;

    @BeforeAll
    void setUp() {
        var location = "Antwerp";

        var building = new Building("testBuilding1", location, 123, new TypeOfBuilding(BuildingType.SLUMS));
        var architect = new Architect("testArchitect1", LocalDate.of(2000,1,1), 123);

        building.addArchitect(architect);

        architect.addBuilding(building);

        buildingService.addBuilding(building);
        architectService.addArchitect(architect);
    }

    @Test
    public void findByLocationShouldFailWhenLocationNotFoundInDatabase() {
        // Assert
        assertThrows(LocationNotFoundException.class, () ->
            // Act
            buildingService.findByLocation("Brussel"));
    }

    @Test
    public void findByLocationShouldPassWhenLocationFoundInDatabase() {
        // Act
        var buildingFound = buildingService.findByLocation("Antwerp");

        // Assert
        assertNotNull(buildingFound);
    }
}