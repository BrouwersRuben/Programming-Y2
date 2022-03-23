package be.kdg.java2.project.presentation.api;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.presentation.api.dto.architect.ArchitectDTO;
import be.kdg.java2.project.presentation.api.dto.architect.ArchitectUpdateDTO;
import be.kdg.java2.project.security.CreaterOnly;
import be.kdg.java2.project.security.UpdaterOnly;
import be.kdg.java2.project.services.ArchitectService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/architects")
public class ArchitectsController {
    private final Logger logger = LoggerFactory.getLogger(ArchitectsController.class);
    private final ArchitectService architectService;
    private final ModelMapper modelMapper;

    public ArchitectsController(ArchitectService architectService, ModelMapper modelMapper) {
        this.architectService = architectService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("{architectId}")
    public ResponseEntity<ArchitectDTO> getArchitectById(@PathVariable int architectId) {
        var architect = architectService.findById(architectId);
        if (architect == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(modelMapper.map(architect, ArchitectDTO.class), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ArchitectDTO>> getFilteredArchitects(@RequestParam(value = "numbE", required = false) String numbE, @RequestParam(value = "name", required = false) String nameFirm) {
        if (numbE != null ){
            var architectsEmp = architectService.findArchitectsByNumberOfEmployeesIsGreaterThan(Integer.parseInt(numbE));
            if (architectsEmp.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(architectDTOMapping(architectsEmp), HttpStatus.OK);
            }
        } else if (nameFirm != null) {
            var architect = architectService.findArchitectByNameCompany(nameFirm);
            if (architect == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(List.of(modelMapper.map(architect, ArchitectDTO.class)), HttpStatus.OK);
        } else {
            var allArchitects = architectService.findAll();
            if (allArchitects.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(architectDTOMapping(allArchitects), HttpStatus.OK);
            }
        }
    }

    @DeleteMapping("{id}")
    @CreaterOnly
    public ResponseEntity<ArchitectDTO> removeArchitectByID(@PathVariable(name = "id") Integer id) {
        Architect foundArchitect = architectService.findById(id);
        if (foundArchitect != null) {
            architectService.delete(id);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{architectId}")
    @UpdaterOnly
    public ResponseEntity<Void> updateArchitect(@PathVariable(name = "architectId") Integer id, @RequestBody @Valid ArchitectUpdateDTO architectDTO) {
        if (id != architectDTO.getId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        var architectFound = architectService.findById(id);

        if (architectFound == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        architectFound.setNumberOfEmployees(architectDTO.getNumberOfEmployees());

        architectService.updateArchitect(architectFound);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private List<ArchitectDTO> architectDTOMapping(List<Architect> architects) {
        return architects
                .stream()
                .map(architect -> modelMapper.map(architect, ArchitectDTO.class))
                .collect(Collectors.toList());
    }
}

//if (higherOrLower.equals("higherThan")) {
//    architectsEmp = architectService.findArchitectsByNumberOfEmployeesIsGreaterThan(numbE);
//    } else if (higherOrLower.equals("lessThan")) {
//    architectsEmp = architectService.findArchitectsByNumberOfEmployeesIsLessThan(numbE);
//    } else {
//    architectsEmp = Collections.emptyList();
//    }
//
//    if (architectsEmp.isEmpty()) {
//    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//} else {
//    return new ResponseEntity<>(architectDTOMapping(architectsEmp), HttpStatus.OK);
//}