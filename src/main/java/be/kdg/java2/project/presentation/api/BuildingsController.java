package be.kdg.java2.project.presentation.api;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.domain.Building;
import be.kdg.java2.project.domain.TypeOfBuilding;
import be.kdg.java2.project.presentation.api.dto.building.BuildingAddDTO;
import be.kdg.java2.project.presentation.api.dto.building.BuildingDTO;
import be.kdg.java2.project.security.CreaterOnly;
import be.kdg.java2.project.security.CustomUserDetailService;
import be.kdg.java2.project.services.ArchitectService;
import be.kdg.java2.project.services.BuildingService;
import be.kdg.java2.project.services.TypeOfBuildingService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/buildings")
public class BuildingsController {
    private final Logger logger = LoggerFactory.getLogger(BuildingsController.class);

    private final BuildingService buildingService;
    private final ArchitectService architectService;
    private final TypeOfBuildingService typeOfBuildingService;
    private final ModelMapper modelMapper;

    public BuildingsController(BuildingService buildingService, ArchitectService architectService, TypeOfBuildingService typeOfBuildingService, ModelMapper modelMapper) {
        this.buildingService = buildingService;
        this.architectService = architectService;
        this.typeOfBuildingService = typeOfBuildingService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("{buildingId}")
    public ResponseEntity<BuildingDTO> getBuildingById(@PathVariable int buildingId) {
        var building = buildingService.findById(buildingId);
        if (building == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(modelMapper.map(building, BuildingDTO.class), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BuildingDTO>> getFilteredBuildings(@RequestParam(value = "location", required = false) String location) {
        if (location == null) {
            var allBuildings = buildingService.findAll();
            if (allBuildings.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(buildingDTOMapping(allBuildings), HttpStatus.OK);
            }
        } else {
            var buildingsByLocation = buildingService.findByLocation(location);
            if (buildingsByLocation.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(buildingDTOMapping(buildingsByLocation), HttpStatus.OK);
            }
        }
    }

    @DeleteMapping("{id}")
    @CreaterOnly
    public ResponseEntity<BuildingDTO> removeBuildingByID(@PathVariable(name = "id") Integer id, @AuthenticationPrincipal CustomUserDetailService userDetails) {
        Building foundBuilding = buildingService.findById(id);
        if (foundBuilding != null) {
            buildingService.delete(id);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping()
    @CreaterOnly
    public ResponseEntity<BuildingDTO> createBuilding(@RequestBody @Valid BuildingAddDTO buildingDTO, BindingResult errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            List<Architect> architects = new ArrayList<>();
            TypeOfBuilding foundType = typeOfBuildingService.findByType(buildingDTO.getType());
            buildingDTO.getArchitectsIDs().forEach((id) -> architects.add(architectService.findById(id)));
            Building building = new Building(buildingDTO.getName(), buildingDTO.getLocation(), buildingDTO.getHeight(), foundType);
            building.addArchitects(architects);
            buildingService.addBuilding(building);
            return new ResponseEntity<>(modelMapper.map(building, BuildingDTO.class), HttpStatus.CREATED);
        }
    }

    private List<BuildingDTO> buildingDTOMapping(List<Building> buildings) {
        return buildings.stream().map(building -> modelMapper.map(building, BuildingDTO.class)).collect(Collectors.toList());
    }
}
