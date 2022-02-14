package be.kdg.java2.project.presentation.api;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.presentation.api.dto.architect.ArchitectDTO;
import be.kdg.java2.project.presentation.api.dto.architect.ArchitectUpdateDTO;
import be.kdg.java2.project.presentation.api.dto.architect.BuildingDTO;
import be.kdg.java2.project.presentation.api.dto.BuildingTypeDTO;
import be.kdg.java2.project.presentation.mvc.viewmodels.ArchitectViewModel;
import be.kdg.java2.project.services.ArchitectService;
import org.apache.commons.lang3.arch.Processor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ArchitectsController {
    private final Logger logger = LoggerFactory.getLogger(ArchitectsController.class);
    private final ArchitectService architectService;
    private final ModelMapper modelMapper;

    public ArchitectsController(ArchitectService architectService, ModelMapper modelMapper) {
        this.architectService = architectService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value = {"/architects", "/architects/{amount}"})
    public ResponseEntity<List<ArchitectDTO>> getArchitects(@PathVariable(required = false, name = "amount") Integer numberOfEmployees){
        if (numberOfEmployees == null){
            var allArchitects = architectService.findAll();
            if (!allArchitects.isEmpty()){
                return ResponseEntity.ok(architectDTOMapping(allArchitects));
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } else {
            var architectsByEmpoyees = architectService.findArchitectsByNumberOfEmployeesIsGreaterThan(numberOfEmployees);
            if (!architectsByEmpoyees.isEmpty()){
                return ResponseEntity.ok(architectDTOMapping(architectsByEmpoyees));
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
    }

    @DeleteMapping("/architects/{id}")
    public ResponseEntity<ArchitectDTO> removeArchitectByID(@PathVariable(name = "id") Integer id){
        Architect foundArchitect = architectService.findById(id);
        if (foundArchitect != null){
            architectService.delete(id);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/architects/{id}")
    public ResponseEntity<Void> updateArchitect(@PathVariable(name = "id") Integer id, @RequestBody ArchitectUpdateDTO architect){
        if (id != architect.getId()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        var architectFound = architectService.findById(id);

        if (architectFound == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        architectFound.setNumberOfEmployees(architect.getNumberOfEmployees());

        architectService.updateArchitect(architectFound);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PostMapping("/architects")
//    public Architect createArchitect(){
//
//    }

    private List<ArchitectDTO> architectDTOMapping(List<Architect> architects){
        return architects
                .stream()
                .map(architect -> modelMapper.map(architect, ArchitectDTO.class))
                .collect(Collectors.toList());
    }
}

/*    private List<ArchitectDTO> architectDTOMapping(List<Architect> architects){
        return architects
                .stream()
                .map(architect -> {
                    var architectDTO = new ArchitectDTO();
                    architectDTO.setId(architect.getId());
                    architectDTO.setNameCompany(architect.getNameCompany());
                    architectDTO.setEstablishmentDate(architect.getEstablishmentDate());
                    architectDTO.setNumberOfEmployees(architect.getNumberOfEmployees());
                    architectDTO.setBuildings(architect.getBuildings().stream()
                            .map(building -> {
                                var buildingDTO = new BuildingDTO();
                                buildingDTO.setId(building.getId());
                                buildingDTO.setName(building.getName());
                                buildingDTO.setLocation(building.getLocation());
                                buildingDTO.setHeight(building.getHeight());
                                var buildingTypeDTO = new BuildingTypeDTO();
                                buildingTypeDTO.setId(building.getType().getId());
                                buildingTypeDTO.setCode(building.getType().getCode());
                                buildingTypeDTO.setType(building.getType().getType());
                                buildingTypeDTO.setRequiresSpecialPermission(building.getType().isRequiresSpecialPermission());
                                buildingDTO.setType(buildingTypeDTO);
                                return buildingDTO;
                            })
                            .collect(Collectors.toList()));
                    return architectDTO;
                })
                .collect(Collectors.toList());
    }*/
