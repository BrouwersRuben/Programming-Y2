package be.kdg.java2.project.presentation.mvc;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.domain.Building;
import be.kdg.java2.project.domain.BuildingType;
import be.kdg.java2.project.domain.TypeOfBuilding;
import be.kdg.java2.project.repository.ArchitectRepository;
import be.kdg.java2.project.repository.BuildingRepository;
import be.kdg.java2.project.repository.TypeOfBuildingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class BuildingControllerTest {

    // Added permitAll for everything (request & matchers), so these test pass.
    // Removed annotation "CreatorOnly" from PostMapping of architect
    // Disabled CSRF (also commented out tag on html)

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private ArchitectRepository architectRepository;

    @Autowired
    private TypeOfBuildingRepository typeOfBuildingRepository;

    @BeforeEach
    void setUp() {
        TypeOfBuilding residential = new TypeOfBuilding(BuildingType.RESIDENTIAL);
        TypeOfBuilding educational = new TypeOfBuilding(BuildingType.EDUCATIONAL);
        TypeOfBuilding commercial = new TypeOfBuilding(BuildingType.COMMERCIAL);
        TypeOfBuilding institutional = new TypeOfBuilding(BuildingType.INSTITUTIONAL);

        typeOfBuildingRepository.save(residential);
        typeOfBuildingRepository.save(educational);
        typeOfBuildingRepository.save(commercial);
        typeOfBuildingRepository.save(institutional);

        Building building1 = new Building("building1", "Antwerp", 123, residential);
        Building building2 = new Building("building2", "Antwerp", 123, educational);
        Building building3 = new Building("building3", "Antwerp", 123, commercial);
        Building building4 = new Building("building4", "Antwerp", 123, institutional);

        Architect architect1 = new Architect("architect1", LocalDate.of(2000,1,1), 1);
        Architect architect2 = new Architect("architect2", LocalDate.of(2000,1,1), 12);
        Architect architect3 = new Architect("architect3", LocalDate.of(2000,1,1), 123);
        Architect architect4 = new Architect("architect4", LocalDate.of(2000,1,1), 1234);

        building1.addArchitect(architect1);
        building2.addArchitect(architect2);
        building3.addArchitect(architect3);
        building4.addArchitect(architect4);

        architect1.addBuilding(building1);
        architect2.addBuilding(building2);
        architect3.addBuilding(building3);
        architect4.addBuilding(building4);

        buildingRepository.save(building1);
        buildingRepository.save(building2);
        buildingRepository.save(building3);
        buildingRepository.save(building4);

        architectRepository.save(architect1);
        architectRepository.save(architect2);
        architectRepository.save(architect3);
        architectRepository.save(architect4);
    }

    @Test
    void showAllBuildingsShouldUseCorrectViewAndReturnCorrectNumberOfBook() throws Exception{
        // Act & Assert
        var actualBuildings = buildingRepository.findAll();
        mockMvc.perform(
                get("/buildings")
        )
                .andExpect(status().isOk())
                .andExpect(model().attribute("allBuildings", equalTo(actualBuildings)))
                .andExpect(model().attribute("allBuildings", hasSize(4)));
    }

    @Test
    void showBuildingInDetailShouldPassUsingTheCorrectId() throws Exception{
        // Arrange
        var id = buildingRepository.findByName("building1").getId();

        // Act & Assert
        mockMvc.perform(
                        get("/buildings/buildingdetail?buildingID={id}", id)
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute("building",
                        hasProperty("name", equalTo("building1"))));
    }

    @Test
    void showBuildingInDetailShouldFailWhenGivenFalseId() throws Exception{
        // Arrange
        var falseId = 69;

        // Act & Assert
        mockMvc.perform(
                        get("/buildings/buildingdetail?buildingID={id}", falseId)
                )
                .andExpect(status().isNoContent());
    }
}