package be.kdg.java2.project.presentation.api;

import be.kdg.java2.project.domain.Building;
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
@RequestMapping("/api")
public class BuildingsController {

    private final BuildingService buildingService;

    public BuildingsController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @RequestMapping(value = {"/buildings", "/buildings/{location}"})
    public ResponseEntity<List<BuildingDTO>> getBuildings(@PathVariable(required = false, name = "location") String location){
        if (location == null){
            var allBuildings = buildingService.findAll();
            if (!allBuildings.isEmpty()){
                return ResponseEntity.ok(buildingDTOMapping(allBuildings));
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } else {
            var buildingsByLocation = buildingService.findByLocation(location);
            if (!buildingsByLocation.isEmpty()){
                return ResponseEntity.ok(buildingDTOMapping(buildingsByLocation));
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }

    @DeleteMapping("/buildings/del/{id}")
    public ResponseEntity<BuildingDTO> removeBuildingByID(@PathVariable(name = "id") Integer id){
        Building foundBuilding = buildingService.findById(id);
        if (foundBuilding != null){
            buildingService.delete(id);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private List<BuildingDTO> buildingDTOMapping(List<Building> buildings){
        return buildings
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
    }
}
