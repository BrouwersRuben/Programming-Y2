package be.kdg.java2.project.services;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.domain.Building;
import be.kdg.java2.project.domain.BuildingType;
import be.kdg.java2.project.domain.TypeOfBuilding;
import be.kdg.java2.project.exceptions.LocationNotFoundException;
import be.kdg.java2.project.repository.ArchitectRepository;
import be.kdg.java2.project.repository.BuildingRepository;
import be.kdg.java2.project.utils.LocationCheckerUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class BuildingServiceMockingTests {

    @Autowired
    private BuildingService buildingService;

    @MockBean
    private BuildingRepository buildingRepository;

    @Test
    public void findByLocationShouldFailWhenLocationNotFoundInDatabase() {

        given(buildingRepository.findBuildingByLocation("Brussel")).willReturn(new ArrayList<>());

        // Assert
        assertEquals(buildingService.findByLocation("Brussel"), new ArrayList<>());
    }

    @Test
    public void findByLocationShouldPassWhenLocationFoundInDatabase() {
        final String location = "Antwerp";

        var building = new Building("testBuilding1", location, 123, new TypeOfBuilding(BuildingType.SLUMS));

        given(buildingRepository.findBuildingByLocation(location)).willReturn(List.of(building));

        // Act
        var buildingFound = buildingService.findByLocation(location);

        // Assert
        assertNotNull(buildingFound);
    }
}
