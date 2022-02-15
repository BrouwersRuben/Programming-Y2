package be.kdg.java2.project.presentation.api;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.domain.Building;
import be.kdg.java2.project.domain.TypeOfBuilding;
import be.kdg.java2.project.presentation.api.dto.BuildingTypeDTO;
import be.kdg.java2.project.presentation.api.dto.building.ArchitectDTO;
import be.kdg.java2.project.presentation.api.dto.building.BuildingAddDTO;
import be.kdg.java2.project.presentation.api.dto.building.BuildingDTO;
import be.kdg.java2.project.presentation.mvc.viewmodels.BuildingViewModel;
import be.kdg.java2.project.services.ArchitectService;
import be.kdg.java2.project.services.BuildingService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BuildingsController {

    private final BuildingService buildingService;
    private final ArchitectService architectService;
    private final ModelMapper modelMapper;

    public BuildingsController(BuildingService buildingService, ArchitectService architectService, ModelMapper modelMapper) {
        this.buildingService = buildingService;
        this.architectService = architectService;
        this.modelMapper = modelMapper;
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

    @DeleteMapping("/buildings/{id}")
    public ResponseEntity<BuildingDTO> removeBuildingByID(@PathVariable(name = "id") Integer id){
        Building foundBuilding = buildingService.findById(id);
        if (foundBuilding != null){
            buildingService.delete(id);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/buildings")
    public ResponseEntity<Void> createBuilding(@RequestBody @Valid BuildingAddDTO buildingDTO, BindingResult errors){
        if (errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            List<Architect> architects = new ArrayList<>();
            buildingDTO.getArchitectsIDs().forEach((id) -> architects.add(architectService.findById(id)));
            Building building = new Building(buildingDTO.getName(), buildingDTO.getLocation(), buildingDTO.getHeight(), new TypeOfBuilding(buildingDTO.getType()));
            building.addArchitects(architects);
            architects.forEach(architect -> architect.addBuilding(building));
            buildingService.addBuilding(building);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    private List<BuildingDTO> buildingDTOMapping(List<Building> buildings){
        return buildings
                .stream()
                .map(building -> modelMapper.map(building, BuildingDTO.class))
                .collect(Collectors.toList());
    }
}

/*    private List<BuildingDTO> buildingDTOMapping(List<Building> buildings){
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
    }*/
