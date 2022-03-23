package be.kdg.java2.project.presentation.api;

import be.kdg.java2.project.domain.User;
import be.kdg.java2.project.presentation.api.dto.UserDTO;
import be.kdg.java2.project.security.CreaterOnly;
import be.kdg.java2.project.services.ArchitectService;
import be.kdg.java2.project.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final Logger logger = LoggerFactory.getLogger(BuildingsController.class);

    private final UserService userService;
    private final ArchitectService architectService;
    private final ModelMapper modelMapper;

    public UsersController(UserService userService, ArchitectService architectService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.architectService = architectService;
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int userId) {
        var user = userService.findById(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(modelMapper.map(user, UserDTO.class), HttpStatus.OK);
    }

    @PostMapping()
    @CreaterOnly
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO, BindingResult errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            var encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.getPassword());
            var workingFirm = architectService.findById(userDTO.getArchitectFirmId());
            var newUser = new User(userDTO.getUsername(), userDTO.getEmail(), userDTO.getRole(), encryptedPassword, workingFirm);
            userService.saveUser(newUser);
            return new ResponseEntity<>(modelMapper.map(newUser, UserDTO.class), HttpStatus.CREATED);
        }
    }
}
