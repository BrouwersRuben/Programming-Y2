package be.kdg.java2.project.presentation.api;

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
@RequestMapping("/api/architects")
//@RequestMapping("/api/architects")
public class ArchitectsController {
    private final Logger logger = LoggerFactory.getLogger(ArchitectsController.class);
    private final ArchitectService architectService;

    public ArchitectsController(ArchitectService architectService) {
        this.architectService = architectService;
    }

    @GetMapping()
    public ResponseEntity<List<ArchitectDTO>> getAllArchitects(){
        var architects = architectService.findAll();

        if (architects.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        var architectsdtos = architects
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

        return new ResponseEntity<>(architectsdtos, HttpStatus.OK);
    }

    @GetMapping("/nameCompany")
    public ResponseEntity<List<ArchitectDTO>> getArchitectByName(@RequestParam("name") String nameCompany){

        var architects = architectService.findArchitectByNameCompany(nameCompany);

        var architectsdtos = architects
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

        return new ResponseEntity<>(architectsdtos, HttpStatus.OK);
    }

    @GetMapping("/numberEmployees")
    public ResponseEntity<List<ArchitectDTO>> getArchitectByNumberOfEmployees(@RequestParam("amount") int numberOfEmplyees){

        var architects = architectService.findArchitectsByNumberOfEmployeesIsGreaterThan(numberOfEmplyees);

        if (!architects.isEmpty()){
            var architectDTOs = architects
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

            return ResponseEntity.ok(architectDTOs);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
