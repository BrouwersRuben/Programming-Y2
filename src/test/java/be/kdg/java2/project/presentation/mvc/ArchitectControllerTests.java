package be.kdg.java2.project.presentation.mvc;

import be.kdg.java2.project.domain.Role;
import be.kdg.java2.project.domain.User;
import be.kdg.java2.project.repository.ArchitectRepository;
import be.kdg.java2.project.security.CostumUserDetails;
import be.kdg.java2.project.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ArchitectControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ArchitectRepository architectRepository;


    // 302 is found redirect, so the stuff makes the new architect, but then it redirects to the architects page, that's why it gives this code
    @Test
    void addArchitectShouldPass() throws Exception {
        // Arrange
        var architectToMake = "name=architectTest1&establishmentDate=2000-01-01&_buildingsIDs=on&_buildingsIDs=on&_buildingsIDs=on&_buildingsIDs=on&numberOfEmployees=123";
        User user = new User("CREATOR", "creator@kdg.be", Role.CD, "$2a$10$ng5ekeJ2KHTAlhRkQV1jeeDjElLC1SBcMnmyS.bNmD3zUZ6PnpzKK", null);
        user.setId(99);

        given(userService.findByEmail(user.getEmail())).willReturn(user);

        CostumUserDetails customUserDetails = new CostumUserDetails(user.getId(), user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_CREATOR")));

        // Act & Assert
        mockMvc.perform(
                        post("/architects/add")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                            .accept(MediaType.TEXT_HTML, MediaType.APPLICATION_XHTML_XML, MediaType.APPLICATION_XML)
                            .with(csrf())
                            .with(user(customUserDetails))
                            .content(architectToMake))
                .andExpect(status().isMovedTemporarily());

        assertNotNull(architectRepository.getById(1));
    }

    @Test
    void addArchitectShouldFailWhenGivenNoName() throws Exception {
        // Arrange
        var architectToMake = "establishmentDate=2000-01-01&_buildingsIDs=on&_buildingsIDs=on&_buildingsIDs=on&_buildingsIDs=on&numberOfEmployees=123";
        User user = new User("CREATOR", "creator@kdg.be", Role.CD, "$2a$10$ng5ekeJ2KHTAlhRkQV1jeeDjElLC1SBcMnmyS.bNmD3zUZ6PnpzKK", null);
        user.setId(99);

        given(userService.findByEmail(user.getEmail())).willReturn(user);

        CostumUserDetails customUserDetails = new CostumUserDetails(user.getId(), user.getUsername(), user.getPassword(),List.of(new SimpleGrantedAuthority("ROLE_CREATOR")));


        // Act & Assert
        mockMvc.perform(
                        post("/architects/add")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                .accept(MediaType.TEXT_HTML, MediaType.APPLICATION_XHTML_XML, MediaType.APPLICATION_XML)
                                .with(csrf())
                                .with(user(customUserDetails))
                                .content(architectToMake)
                )
                .andExpect(status().isBadRequest());

    }

    @Test
    void addArchitectShouldFailWhenNotSignedIn() throws Exception {
        // Arrange
        var architectToMake = "establishmentDate=2000-01-01&_buildingsIDs=on&_buildingsIDs=on&_buildingsIDs=on&_buildingsIDs=on&numberOfEmployees=123";

        // Act & Assert
        mockMvc.perform(
                        post("/architects/add")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                .accept(MediaType.TEXT_HTML, MediaType.APPLICATION_XHTML_XML, MediaType.APPLICATION_XML)
                                .content(architectToMake)
                )
                .andExpect(status().isForbidden());

    }
}