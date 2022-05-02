package be.kdg.java2.project.presentation.mvc;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.domain.Building;
import be.kdg.java2.project.domain.BuildingType;
import be.kdg.java2.project.domain.TypeOfBuilding;
import be.kdg.java2.project.presentation.mvc.viewmodels.BuildingViewModel;
import be.kdg.java2.project.presentation.mvc.viewmodels.DeletingViewModel;
import be.kdg.java2.project.security.CreaterOnly;
import be.kdg.java2.project.services.ArchitectService;
import be.kdg.java2.project.services.BuildingService;
import be.kdg.java2.project.services.TypeOfBuildingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/buildings")
public class BuildingController {
    private final Logger logger = LoggerFactory.getLogger(BuildingController.class);
    private final BuildingService buildingService;
    private final ArchitectService architectService;
    private final TypeOfBuildingService typeOfBuildingService;

    public BuildingController(BuildingService buildingService, ArchitectService architectService, TypeOfBuildingService typeOfBuildingService) {
        this.buildingService = buildingService;
        this.architectService = architectService;
        this.typeOfBuildingService = typeOfBuildingService;
    }

    @GetMapping
    public String showAllBuildings(Model model) {
        model.addAttribute("allBuildings", buildingService.findAll());
        logger.debug(buildingService.findAll().toString());
        return "/mainpages/buildings";
    }

/*    @ExceptionHandler(LocationNotFoundException.class)
    //the req used for logging, like the url that threw the exception for example
    public ModelAndView handleError(HttpServletRequest req, LocationNotFoundException exception) {
        logger.error(exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", exception);
        modelAndView.setViewName("/errorpages/locationerror");
        return modelAndView;
    }*/

    @GetMapping("/add")
    public String showAddBuildingsForm(Model model) {
        model.addAttribute("buildingDTO", new BuildingViewModel());
        model.addAttribute("buildingTypes", BuildingType.values());
        model.addAttribute("architects", architectService.findAll());
        return "/addpages/addbuildings";
    }

    @PostMapping("/add")
    @CreaterOnly
    public String processAddBuilding(Model model, @Valid @ModelAttribute("buildingDTO") BuildingViewModel buildingViewModel, BindingResult errors, HttpServletResponse response) {
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(error -> logger.error(error.toString()));
            model.addAttribute("buildingTypes", BuildingType.values());
            model.addAttribute("architects", architectService.findAll());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "/addpages/addbuildings";
        } else {
            List<Architect> architects = new ArrayList<>();
            TypeOfBuilding foundType = typeOfBuildingService.findByType(buildingViewModel.getType());
            buildingViewModel.getArchitectsIDs().forEach((id) -> architects.add(architectService.findById(id)));
            Building building = new Building(buildingViewModel.getName(), buildingViewModel.getLocation(), buildingViewModel.getHeight(), foundType);
            building.addArchitects(architects);
            architects.forEach(architect -> architect.addBuilding(building));
            buildingService.addBuilding(building);
            response.setStatus(HttpServletResponse.SC_CREATED);
            return "redirect:/buildings";
        }
    }

    @GetMapping("/buildingdetail")
    public String showBuildingDetail(@RequestParam("buildingID") Integer buildingID, Model model, HttpServletResponse response) {
        Building building = buildingService.findByIdWithArchitectsAndType(buildingID);
        if (building == null){
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return "/mainpages/buildings";
        }
        model.addAttribute("building", building);
        return "/detailpages/buildingdetail";
    }

    @PostMapping(params = {"delete"})
    @CreaterOnly
    public String removeBuilding(@ModelAttribute("deletingDTO") DeletingViewModel deletingViewModel) {
        buildingService.delete(deletingViewModel.getID());
        return "redirect:/buildings";
    }
}
