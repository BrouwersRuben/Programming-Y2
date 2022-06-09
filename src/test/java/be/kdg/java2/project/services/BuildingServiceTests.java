package be.kdg.java2.project.services;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.domain.Building;
import be.kdg.java2.project.domain.BuildingType;
import be.kdg.java2.project.domain.TypeOfBuilding;
import be.kdg.java2.project.repository.TypeOfBuildingRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BuildingServiceTests {

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private ArchitectService architectService;

    @Autowired
    private TypeOfBuildingRepository typeOfBuildingRepository;

    @BeforeAll
    void setUp() {
        var location = "Antwerp";

        var typeOfBuilding = new TypeOfBuilding(BuildingType.SLUMS);
        typeOfBuildingRepository.save(typeOfBuilding);

        var architect = new Architect("testArchitect1", LocalDate.of(2000,1,1), 123);

        architectService.addArchitect(architect);

        var building = new Building("testBuilding1", location, 123, typeOfBuilding);

        building.addArchitect(architect);

        buildingService.addBuilding(building);
    }

    @Test
    public void findByLocationShouldFailWhenLocationNotFoundInDatabase() {
        // Act
        var buildings = buildingService.findByLocation("Brussel");

        // Assert
        assertEquals(buildings, new ArrayList<>());
    }

    @Test
    public void findByLocationShouldPassWhenLocationFoundInDatabase() {
        // Act
        var buildingFound = buildingService.findByLocation("Antwerp");

        // Assert
        assertNotNull(buildingFound);
    }
}