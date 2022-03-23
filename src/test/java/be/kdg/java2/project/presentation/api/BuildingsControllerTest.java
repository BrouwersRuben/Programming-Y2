package be.kdg.java2.project.presentation.api;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.repository.ArchitectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class BuildingsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArchitectRepository architectRepository;

    @BeforeEach
    void setUp() {
        Architect architect1 = new Architect("architect1", LocalDate.of(2000,1,1), 1);
        architectRepository.save(architect1);
    }

    // Commented out @CreaterOnly on postmapping REST API

    @Test
    void createBuildingShouldPassWhenAllFieldsAreEntered() throws Exception {
        // Arrange
        var buildingToSave = "{\"name\":\"testBuilding1\",\"location\":\"Antwerp\",\"height\":\"123\",\"architectsIDs\":[1],\"type\":\"SPECIAL\"}";

        // Assert & Act
        mockMvc.perform(
                post("/api/buildings")
                        .content(buildingToSave)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("testBuilding1"));

    }

    @Test
    void createBuildingShouldFailWhenNameIsLeftOut() throws Exception {
        // Arrange
        var buildingToSave = "{\"location\":\"Antwerp\",\"height\":\"123\",\"architectsIDs\":[1],\"type\":\"SPECIAL\"}";

        // Assert & Act
        mockMvc.perform(
                        post("/api/buildings")
                                .content(buildingToSave)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.ALL)
                )
                .andExpect(status().isBadRequest());

    }
}