package be.kdg.java2.project.presentation;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.domain.Building;
import be.kdg.java2.project.domain.BuildingType;
import be.kdg.java2.project.domain.TypeOfBuilding;
import be.kdg.java2.project.exceptions.LocationNotFoundException;
import be.kdg.java2.project.presentation.dto.BuildingDTO;
import be.kdg.java2.project.presentation.dto.DeletingDTO;
import be.kdg.java2.project.services.ArchitectService;
import be.kdg.java2.project.services.BuildingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/buildings")
public class BuildingController {
    private final Logger logger = LoggerFactory.getLogger(BuildingController.class);
    private final BuildingService buildingService;
    private final ArchitectService architectService;

    public BuildingController(BuildingService buildingService, ArchitectService architectService) {
        this.buildingService = buildingService;
        this.architectService = architectService;
    }

    @GetMapping
    public String showAllBuildings(Model model) {
        model.addAttribute("allBuildings", buildingService.findAll());
        logger.debug(buildingService.findAll().toString());
        return "/mainpages/buildings";
    }

    @RequestMapping(value = "/filteredByLocation", method = RequestMethod.POST)
    public String showBuildingByLocation(/*@Param("loc")*/ @RequestParam(value = "loc") String location, Model model) {
        if(Objects.equals(location, "")){
            model.addAttribute("allBuildings", buildingService.findAll());
            model.addAttribute("errorMessage", "You did not enter a location");
            logger.warn("You did not enter a location");
        } else {
            List<Building> buildings = buildingService.findByLocation(location);
            logger.debug("Buildings with location: " + location + " are: " + buildings);
            model.addAttribute("allBuildings", buildings);
        }
        return "/mainpages/buildings";
    }

    @ExceptionHandler(LocationNotFoundException.class)
    //the req used for logging, like the url that threw the exception for example
    public ModelAndView handleError(HttpServletRequest req, LocationNotFoundException exception) {
        logger.error(exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", exception);
        modelAndView.setViewName("/errorpages/locationerror");
        return modelAndView;
    }

/*
    Model, ModelMap, and ModelAndView are used to define a model in a Spring MVC application.
    Model defines a holder for model attributes and is primarily designed for adding attributes to the model.
    ModelMap is an extension of Model with the ability to store attributes in a map and chain method calls.
    ModelAndView is a holder for a model and a view; it allows to return both model and view in one return value.
*/

    @GetMapping("/add")
    public String showAddBuildingsForm(Model model) {
        model.addAttribute("buildingDTO", new BuildingDTO());
        model.addAttribute("buildingTypes", BuildingType.values());
        model.addAttribute("architects", architectService.findAll());
        return "/addpages/addbuildings";
    }

    @PostMapping("/add")
    public String processAddBuilding(Model model, @Valid @ModelAttribute("buildingDTO") BuildingDTO buildingDTO, BindingResult errors) {
        if (errors.hasErrors()){
            errors.getAllErrors().forEach(error -> logger.error(error.toString()));
            model.addAttribute("buildingTypes", BuildingType.values());
            model.addAttribute("architects", architectService.findAll());
            return "/addpages/addbuildings";
        } else {
            List<Architect> architects = new ArrayList<>();
            buildingDTO.getArchitectsIDs().forEach((id) -> architects.add(architectService.findById(id)));
            Building building = new Building(buildingDTO.getName(), buildingDTO.getLocation(), buildingDTO.getHeight(), new TypeOfBuilding(buildingDTO.getType()));
            building.addArchitects(architects);
            architects.forEach(architect -> architect.addBuilding(building));
            buildingService.addBuilding(building);
            return "redirect:/buildings";
        }
    }

    @GetMapping("/buildingdetail")
    public String showBuildingDetail(@RequestParam("buildingID") Integer buildingID, Model model) {
        Building building = buildingService.findById(buildingID);
        model.addAttribute("building", building);
        return "/detailpages/buildingdetail";
    }

    @PostMapping(params = {"delete"})
    public String removeBuilding(@ModelAttribute("deletingDTO") DeletingDTO deletingDTO){
        buildingService.delete(deletingDTO.getID());
        return "redirect:/buildings";
    }
}
