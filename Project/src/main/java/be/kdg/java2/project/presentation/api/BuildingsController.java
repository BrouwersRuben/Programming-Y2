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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/buildings")
public class BuildingsController {
    private final Logger logger = LoggerFactory.getLogger(BuildingsController.class);

    private final BuildingService buildingService;
    private final ArchitectService architectService;
    private final ModelMapper modelMapper;

    public BuildingsController(BuildingService buildingService, ArchitectService architectService, ModelMapper modelMapper) {
        this.buildingService = buildingService;
        this.architectService = architectService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public ResponseEntity<List<BuildingDTO>> getBuildingsByLoc(@RequestParam(value = "location", required = false) String location){
        if (location == null){
            return new ResponseEntity<>(buildingDTOMapping(buildingService.findAll()), HttpStatus.OK);
        } else {
            var buildingsByLocation = buildingService.findByLocation(location);
            if (buildingsByLocation.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(buildingDTOMapping(buildingsByLocation), HttpStatus.OK);
            }
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<BuildingDTO> removeBuildingByID(@PathVariable(name = "id") Integer id){
        Building foundBuilding = buildingService.findById(id);
        if (foundBuilding != null){
            buildingService.delete(id);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping()
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
