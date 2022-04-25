package be.kdg.java2.project.presentation.api;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.presentation.api.dto.architect.ArchitectUpdateDTO;
import be.kdg.java2.project.services.ArchitectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ArchitectControllerMockingTests {

    @Autowired
    private ArchitectsController architectsController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ArchitectService architectService;

    // Testing the PUT method

    @Test
    @WithUserDetails("Updater")
    public void changingTheNumberOfEmployeesShouldReturnNoContent() throws Exception{
        // TODO: Mock the user

        // Arrange
        Architect architect = new Architect("TestingStuff", LocalDate.of(2000, 1, 1), 50);
        architect.setId(1);

        given(architectService.findById(1)).willReturn(architect);

        // Act & Assert
        ArchitectUpdateDTO update = new ArchitectUpdateDTO();
        update.setId(1);
        update.setNumberOfEmployees(100);

        mockMvc.perform(
                put("/api/architects/{id}", 1)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update))
        ).andExpect(status().isNoContent());
    }
}
