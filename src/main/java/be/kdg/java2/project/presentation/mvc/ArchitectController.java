package be.kdg.java2.project.presentation.mvc;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.domain.Building;
import be.kdg.java2.project.presentation.mvc.viewmodels.ArchitectViewModel;
import be.kdg.java2.project.presentation.mvc.viewmodels.DeletingViewModel;
import be.kdg.java2.project.security.CreaterOnly;
import be.kdg.java2.project.services.ArchitectService;
import be.kdg.java2.project.services.BuildingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/architects")
public class ArchitectController {
    private final Logger logger = LoggerFactory.getLogger(ArchitectController.class);
    private final BuildingService buildingService;
    private final ArchitectService architectService;

    public ArchitectController(BuildingService buildingService, ArchitectService architectService) {
        this.buildingService = buildingService;
        this.architectService = architectService;
    }

    @GetMapping
    public String showAllArchitects(Model model) {
        model.addAttribute("allArchitects", architectService.findAll());
        logger.debug(architectService.findAll().toString());
        return "/mainpages/architects";
    }

    @GetMapping("/add")
    public String showAddArchitectForm(Model model) {
        model.addAttribute("architectDTO", new ArchitectViewModel());
        model.addAttribute("buildings", buildingService.findAll());
        return "addpages/addarchitects";
    }

    @PostMapping("/add")
//    @CreaterOnly
    public String processAddArchitect(Model model, @Valid @ModelAttribute("architectDTO") ArchitectViewModel architectViewModel, BindingResult errors, HttpServletResponse response) {
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(error -> logger.error(error.toString()));
            model.addAttribute("buildings", buildingService.findAll());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "addpages/addarchitects";
        } else {
            Architect architect = new Architect(architectViewModel.getName(), architectViewModel.getEstablishmentDate(), architectViewModel.getNumberOfEmployees());
            List<Building> buildings = new ArrayList<>();
            architectViewModel.getBuildingsIDs().forEach((id) -> buildings.add(buildingService.findById(id)));
            architect.addBuildings(buildings);
            buildings.forEach(building -> building.addArchitect(architect));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            architectService.addArchitect(architect);
            return "redirect:/architects";
        }
    }

    @GetMapping("/architectdetail")
    public String showArchitectDetail(@RequestParam("architectID") Integer architectID, Model model, HttpServletResponse response) {
        Architect architect = architectService.findById(architectID);
        if (architect == null){
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return "/mainpages/buildings";
        }
        model.addAttribute("architect", architect);
        return "detailpages/architectdetail";
    }

    @PostMapping(params = {"delete"})
    @CreaterOnly
    public String removeArchitect(@ModelAttribute("deletingDTO") DeletingViewModel deletingViewModel) {
        architectService.delete(deletingViewModel.getID());
        return "redirect:/architects";
    }
}

