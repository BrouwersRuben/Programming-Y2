package be.kdg.java2.project.presentation.api;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.domain.Role;
import be.kdg.java2.project.domain.User;
import be.kdg.java2.project.repository.ArchitectRepository;
import be.kdg.java2.project.security.CostumUserDetails;
import be.kdg.java2.project.services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArchitectsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArchitectRepository architectRepository;

    @MockBean
    private UserService userService;

    @BeforeAll
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

        architectRepository.save(architect1);
        architectRepository.save(architect2);
        architectRepository.save(architect3);
        architectRepository.save(architect4);
    }

    @Test
    void filteredArchitectsShouldGiveArchitectByNameWhenAskedForIt() throws Exception {
        // Arrange
        User user = new User("User", "user@kdg.be", Role.N, "$2a$10$ng5ekeJ2KHTAlhRkQV1jeeDjElLC1SBcMnmyS.bNmD3zUZ6PnpzKK", null);
        user.setId(99);

        given(userService.findByEmail(user.getEmail())).willReturn(user);

        CostumUserDetails customUserDetails = new CostumUserDetails(user.getId(), user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_UPDATER")));

        // Act & Assert
        mockMvc.perform(
                get("/api/architects?name={name}", "architect2")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(user(customUserDetails))
        )
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$[0].nameCompany").value("architect2"));

    }

    @Test
    void filteredArchitectsShouldGiveArchitectsWhenFilteredOnEmployees() throws Exception {
        // Arrange
        User user = new User("User", "user@kdg.be", Role.N, "$2a$10$ng5ekeJ2KHTAlhRkQV1jeeDjElLC1SBcMnmyS.bNmD3zUZ6PnpzKK", null);
        user.setId(99);

        given(userService.findByEmail(user.getEmail())).willReturn(user);

        CostumUserDetails customUserDetails = new CostumUserDetails(user.getId(), user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_UPDATER")));

        // Act & Assert
        mockMvc.perform(
                        get("/api/architects?numbE={number}", "120")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf())
                                .with(user(customUserDetails))
                )
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.size()").value(2));

    }

    @Test
    void filteredArchitectsShouldFailWhenGivenUnknownName() throws Exception {
        // Arrange
        User user = new User("User", "user@kdg.be", Role.N, "$2a$10$ng5ekeJ2KHTAlhRkQV1jeeDjElLC1SBcMnmyS.bNmD3zUZ6PnpzKK", null);
        user.setId(99);

        given(userService.findByEmail(user.getEmail())).willReturn(user);

        CostumUserDetails customUserDetails = new CostumUserDetails(user.getId(), user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_UPDATER")));

        // Act & Assert
        mockMvc.perform(
                        get("/api/architects?name={name}", "blablabla")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf())
                                .with(user(customUserDetails))
                )
                .andExpect(status().isNoContent());
    }
}