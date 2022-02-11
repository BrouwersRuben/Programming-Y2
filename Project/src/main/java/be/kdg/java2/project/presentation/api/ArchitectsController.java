package be.kdg.java2.project.presentation.api;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.presentation.api.dto.architect.ArchitectDTO;
import be.kdg.java2.project.presentation.api.dto.architect.BuildingDTO;
import be.kdg.java2.project.presentation.api.dto.BuildingTypeDTO;
import be.kdg.java2.project.services.ArchitectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ArchitectsController {
    private final Logger logger = LoggerFactory.getLogger(ArchitectsController.class);
    private final ArchitectService architectService;

    public ArchitectsController(ArchitectService architectService) {
        this.architectService = architectService;
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

    @DeleteMapping("/architects/del/{id}")
    public ResponseEntity<ArchitectDTO> removeArchitectByID(@PathVariable(name = "id") Integer id){
        Architect foundArchitect = architectService.findById(id);
        if (foundArchitect != null){
            architectService.delete(id);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private List<ArchitectDTO> architectDTOMapping(List<Architect> architects){
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
    }
}
