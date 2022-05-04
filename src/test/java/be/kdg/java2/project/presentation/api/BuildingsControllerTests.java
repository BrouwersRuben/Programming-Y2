package be.kdg.java2.project.presentation.api;

import be.kdg.java2.project.configuration.GeneralExceptionHandler;
import be.kdg.java2.project.domain.*;
import be.kdg.java2.project.presentation.api.dto.building.BuildingAddDTO;
import be.kdg.java2.project.repository.ArchitectRepository;
import be.kdg.java2.project.repository.BuildingRepository;
import be.kdg.java2.project.security.CostumUserDetails;
import be.kdg.java2.project.services.BuildingService;
import be.kdg.java2.project.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class BuildingsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArchitectRepository architectRepository;

    @MockBean
    private BuildingRepository buildingRepository;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        Architect architect1 = new Architect("architect1", LocalDate.of(2000,1,1), 1);
        architect1.setId(1);
        architectRepository.save(architect1);
    }

    @Test
    public void createBuildingShouldPassWhenAllFieldsAreEntered() throws Exception {
        // Arrange
        User user = new User("CREATOR", "creator@kdg.be", Role.CD, "$2a$10$ng5ekeJ2KHTAlhRkQV1jeeDjElLC1SBcMnmyS.bNmD3zUZ6PnpzKK", null);
        user.setId(99);

        given(userService.findByEmail(user.getEmail())).willReturn(user);

        CostumUserDetails customUserDetails = new CostumUserDetails(user.getId(), user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_CREATOR")));

        BuildingAddDTO buildingToSave = new BuildingAddDTO();
        buildingToSave.setName("testBuilding1");
        buildingToSave.setLocation("Antwerp");
        buildingToSave.setHeight(123);
        buildingToSave.setArchitectsIDs(List.of(1));
        buildingToSave.setType(BuildingType.SPECIAL);

        // Assert & Act
        mockMvc.perform(
                post("/api/buildings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(user(customUserDetails))
                        .content(objectMapper.writeValueAsString(buildingToSave))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("testBuilding1"));

    }

    @Test
    public void createBuildingShouldFailWhenNameIsLeftOut() throws Exception {
        // Arrange
        User user = new User("CREATOR", "creator@kdg.be", Role.CD, "$2a$10$ng5ekeJ2KHTAlhRkQV1jeeDjElLC1SBcMnmyS.bNmD3zUZ6PnpzKK", null);
        user.setId(99);

        given(userService.findByEmail(user.getEmail())).willReturn(user);

        CostumUserDetails customUserDetails = new CostumUserDetails(user.getId(), user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_CREATOR")));

        BuildingAddDTO buildingToSave = new BuildingAddDTO();
        buildingToSave.setLocation("Antwerp");
        buildingToSave.setHeight(123);
        buildingToSave.setArchitectsIDs(List.of(1));
        buildingToSave.setType(BuildingType.SPECIAL);

//        var buildingToSave = "{\"location\":\"Antwerp\",\"height\":\"123\",\"architectsIDs\":[1],\"type\":\"SPECIAL\"}";

        // Assert & Act
        mockMvc.perform(
                        post("/api/buildings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf())
                                .with(user(customUserDetails))
                                .content(objectMapper.writeValueAsString(buildingToSave))
                )
                .andExpect(status().isBadRequest());

        verify(buildingRepository, times(0)).save(any(Building.class));
    }

    @Test
    public void createBuildingShouldFailWhenNotTheCorrectRole() throws Exception {
        // Arrange
        User user = new User("User", "user@kdg.be", Role.N, "$2a$10$ng5ekeJ2KHTAlhRkQV1jeeDjElLC1SBcMnmyS.bNmD3zUZ6PnpzKK", null);
        user.setId(99);

        given(userService.findByEmail(user.getEmail())).willReturn(user);

        CostumUserDetails customUserDetails = new CostumUserDetails(user.getId(), user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));

        BuildingAddDTO buildingToSave = new BuildingAddDTO();
        buildingToSave.setName("testBuilding1");
        buildingToSave.setLocation("Antwerp");
        buildingToSave.setHeight(123);
        buildingToSave.setArchitectsIDs(List.of(1));
        buildingToSave.setType(BuildingType.SPECIAL);

        // Assert & Act
        mockMvc.perform(
                        post("/api/buildings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf())
                                .with(user(customUserDetails))
                                .content(objectMapper.writeValueAsString(buildingToSave))
                )
                .andExpect(status().isForbidden());
    }
}