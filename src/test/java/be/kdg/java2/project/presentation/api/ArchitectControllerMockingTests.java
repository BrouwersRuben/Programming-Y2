package be.kdg.java2.project.presentation.api;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.domain.Role;
import be.kdg.java2.project.domain.User;
import be.kdg.java2.project.presentation.api.dto.architect.ArchitectUpdateDTO;
import be.kdg.java2.project.security.CostumUserDetails;
import be.kdg.java2.project.services.ArchitectService;
import be.kdg.java2.project.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ArchitectControllerMockingTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ArchitectService architectService;

    @MockBean
    private UserService userService;


    // Testing the PUT method
    @Test
    public void changingTheNumberOfEmployeesShouldReturnNoContent() throws Exception {
        // Arrange
        User user = new User("Updater", "updater@kdg.be", Role.U, "$2a$10$ng5ekeJ2KHTAlhRkQV1jeeDjElLC1SBcMnmyS.bNmD3zUZ6PnpzKK", null);
        user.setId(99);

        given(userService.findByEmail(user.getEmail())).willReturn(user);

        CostumUserDetails customUserDetails = new CostumUserDetails(user.getId(), user.getUsername(), user.getPassword(),List.of(new SimpleGrantedAuthority("ROLE_UPDATER")));

        Architect architect = new Architect("TestingStuff", LocalDate.of(2000, 1, 1), 50);
        architect.setId(1);

        given(architectService.findById(1)).willReturn(architect);

        ArchitectUpdateDTO update = new ArchitectUpdateDTO();
        update.setId(1);
        update.setNumberOfEmployees(100);

        // Act & Assert
        mockMvc.perform(
                put("/api/architects/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(user(customUserDetails))
                        .content(objectMapper.writeValueAsString(update))
        ).andExpect(status().isNoContent());

        ArgumentCaptor<Architect> captor = ArgumentCaptor.forClass(Architect.class);
        verify(architectService).updateArchitect(captor.capture());

        var capturedArchitect = captor.getValue();
        assertEquals(update.getId(), capturedArchitect.getId());
        assertEquals(update.getNumberOfEmployees(), capturedArchitect.getNumberOfEmployees());
    }


    @Test
    public void changingTheNumberOfEmployeesShouldThrow404() throws Exception {
        // Arrange
        User user = new User("Updater", "updater@kdg.be", Role.U, "$2a$10$ng5ekeJ2KHTAlhRkQV1jeeDjElLC1SBcMnmyS.bNmD3zUZ6PnpzKK", null);
        user.setId(99);

        given(userService.findByEmail(user.getEmail())).willReturn(user);

        CostumUserDetails customUserDetails = new CostumUserDetails(user.getId(), user.getUsername(), user.getPassword(),List.of(new SimpleGrantedAuthority("ROLE_UPDATER")));

        ArchitectUpdateDTO update = new ArchitectUpdateDTO();
        update.setId(420);
        update.setNumberOfEmployees(6969);

        // Act & Assert
        mockMvc.perform(
                put("/api/architects/{id}", 420)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(user(customUserDetails))
                        .content(objectMapper.writeValueAsString(update))
        ).andExpect(status().isNotFound());
    }

    @Test
    public void changingTheNumberOfEmployeesShouldThrow409() throws Exception {
        // Arrange
        User user = new User("Updater", "updater@kdg.be", Role.U, "$2a$10$ng5ekeJ2KHTAlhRkQV1jeeDjElLC1SBcMnmyS.bNmD3zUZ6PnpzKK", null);
        user.setId(99);

        given(userService.findByEmail(user.getEmail())).willReturn(user);

        CostumUserDetails customUserDetails = new CostumUserDetails(user.getId(), user.getUsername(), user.getPassword(),List.of(new SimpleGrantedAuthority("ROLE_UPDATER")));

        ArchitectUpdateDTO update = new ArchitectUpdateDTO();
        update.setId(420);
        update.setNumberOfEmployees(6969);

        // Act & Assert
        mockMvc.perform(
                put("/api/architects/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(user(customUserDetails))
                        .content(objectMapper.writeValueAsString(update))
        ).andExpect(status().isConflict());
    }
}
