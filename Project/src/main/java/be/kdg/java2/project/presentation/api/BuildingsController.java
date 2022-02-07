package be.kdg.java2.project.presentation.api;

import be.kdg.java2.project.presentation.api.dto.BuildingTypeDTO;
import be.kdg.java2.project.presentation.api.dto.building.ArchitectDTO;
import be.kdg.java2.project.presentation.api.dto.building.BuildingDTO;
import be.kdg.java2.project.services.BuildingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/buildings")
public class BuildingsController {

    private final BuildingService buildingService;

    public BuildingsController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @GetMapping()
    public ResponseEntity<List<BuildingDTO>> getAllBuildings(){
        var buildings = buildingService.findAll();

        if (buildings.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        var buildingDTOs = buildings
                .stream()
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
                    buildingDTO.setArchitects(building.getArchitects()
                            .stream()
                            .map(architect -> {
                                var architectDTO = new ArchitectDTO();
                                architectDTO.setId(architect.getId());
                                architectDTO.setNameCompany(architect.getNameCompany());
                                architectDTO.setEstablishmentDate(architect.getEstablishmentDate());
                                architectDTO.setNumberOfEmployees(architect.getNumberOfEmployees());
                                return architectDTO;
                            })
                            .collect(Collectors.toList()));
                    return buildingDTO;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(buildingDTOs, HttpStatus.OK);
    }

    @GetMapping("/location")
    public ResponseEntity<List<BuildingDTO>> getBuildingsByLocation(@RequestParam("name") String location){

        var buildings = buildingService.findByLocation(location);

        if (!buildings.isEmpty()){
            var buildingsDTOs = buildings
                    .stream()
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
                        buildingDTO.setArchitects(building.getArchitects()
                                .stream()
                                .map(architect -> {
                                    var architectDTO = new ArchitectDTO();
                                    architectDTO.setId(architect.getId());
                                    architectDTO.setNameCompany(architect.getNameCompany());
                                    architectDTO.setEstablishmentDate(architect.getEstablishmentDate());
                                    architectDTO.setNumberOfEmployees(architect.getNumberOfEmployees());
                                    return architectDTO;
                                })
                                .collect(Collectors.toList()));
                        return buildingDTO;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(buildingsDTOs);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
