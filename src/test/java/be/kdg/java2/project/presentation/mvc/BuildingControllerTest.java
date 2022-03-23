package be.kdg.java2.project.presentation.mvc;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.domain.Building;
import be.kdg.java2.project.domain.BuildingType;
import be.kdg.java2.project.domain.TypeOfBuilding;
import be.kdg.java2.project.repository.ArchitectRepository;
import be.kdg.java2.project.repository.BuildingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BuildingControllerTest {

    // Added permitAll for everything, so these test pass.

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private ArchitectRepository architectRepository;

    @BeforeEach
    @Transactional
    void setUp() {
        Architect architect1 = new Architect("architect1", LocalDate.of(2000,1,1), 1);
        architect1.setId(1);
        Architect architect2 = new Architect("architect2", LocalDate.of(2000,1,1), 12);
        architect2.setId(2);
        Architect architect3 = new Architect("architect3", LocalDate.of(2000,1,1), 123);
        architect3.setId(3);
        Architect architect4 = new Architect("architect4", LocalDate.of(2000,1,1), 1234);
        architect4.setId(4);

        Building building1 = new Building("building1", "Antwerp", 123, new TypeOfBuilding(BuildingType.EDUCATIONAL));
        building1.setId(1);
        Building building2 = new Building("building2", "Antwerp", 123, new TypeOfBuilding(BuildingType.EDUCATIONAL));
        building2.setId(2);
        Building building3 = new Building("building3", "Antwerp", 123, new TypeOfBuilding(BuildingType.EDUCATIONAL));
        building3.setId(3);
        Building building4 = new Building("building4", "Antwerp", 123, new TypeOfBuilding(BuildingType.EDUCATIONAL));
        building4.setId(4);

        architect1.addBuilding(building1);
        architect2.addBuilding(building2);
        architect3.addBuilding(building3);
        architect4.addBuilding(building4);

        building1.addArchitect(architect1);
        building2.addArchitect(architect2);
        building3.addArchitect(architect3);
        building4.addArchitect(architect4);

        architectRepository.save(architect1);
        architectRepository.save(architect2);
        architectRepository.save(architect3);
        architectRepository.save(architect4);

        buildingRepository.save(building1);
        buildingRepository.save(building2);
        buildingRepository.save(building3);
        buildingRepository.save(building4);
    }

    @Test
    void showAllBuildingsShouldUseCorrectView() throws Exception{
        mockMvc.perform(
                get("/buildings")
        )
                .andExpect(status().isOk())
                .andExpect(model().attribute("allBuildings", equalTo(buildingRepository.findAll())));
        // TODO: What else can I test here?
        // Size of expectedBooks
    }
}