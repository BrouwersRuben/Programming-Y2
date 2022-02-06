package be.kdg.java2.project.presentation;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.domain.Building;
import be.kdg.java2.project.presentation.dto.ArchitectDTO;
import be.kdg.java2.project.presentation.dto.DeletingDTO;
import be.kdg.java2.project.services.ArchitectService;
import be.kdg.java2.project.services.BuildingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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

//    Does a little the same as @DateTimeFormatter
//    @InitBinder
//    public void extendBinding(WebDataBinder binder){
//        DateFormat dateFormat = new SimpleDateFormat("yyyy**MM**dd");
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
//        DateFormatter dateFormatter = new DateFormatter("dd-MM-yyyy");
//        binder.addCustomFormatter(dateFormatter, "establishmentDate"); //Only for establishmentDate
//        binder.addCustomFormatter(dateFormatter); //For all fields
//    }

    @GetMapping
    public String showAllArchitects(Model model) {
        model.addAttribute("allArchitects", architectService.findAll());
        logger.debug(architectService.findAll().toString());
        return "/mainpages/architects";
    }

    //I was not sure on how i made the urls thesame as the building page, because it gave errors
    @RequestMapping(value = "/filteredByName", method = RequestMethod.POST)
    public String showArchitectsByName(@Param("name") @RequestParam(value = "name") String name, Model model) {
        if(Objects.equals(name, "")){
            model.addAttribute("allArchitects", architectService.findAll());
            model.addAttribute("errorMessage", "You did not enter anything");
            logger.warn("You did not enter the name");
        } else {
            Architect architect = architectService.findArchitectByNameCompany(name);
            logger.debug("Found architect with name: " + name);
            model.addAttribute("allArchitects", architect);
        }
        return "/mainpages/architects";
    }

    //if field left empty, the application crashes, because it tries to convert an int to a string..., but i'm not sure where. I feel like bean validation might fix this
    @RequestMapping(value = "/filteredByNumberOfEmployees", method = RequestMethod.POST)
    public String showArchitectsByNumberOfEmployees(@Param("numbE") @RequestParam(value = "numbE") int numberOfEmployees, Model model) {
        if(numberOfEmployees == 0 || numberOfEmployees < 0){
            model.addAttribute("allArchitects", architectService.findAll());
            model.addAttribute("errorMessage", "You did not enter anything valid");
            logger.warn("You did not enter the numberOfEmployees");
        } else {
            List<Architect> architects = architectService.findArchitectsByNumberOfEmployeesIsGreaterThan(numberOfEmployees);
            logger.debug("Architects with: " + numberOfEmployees + " number of empoyees are: " + architects);
            model.addAttribute("allArchitects", architects);
        }
        return "/mainpages/architects";
    }

    @GetMapping("/add")
    public String showAddArchitectForm(Model model) {
        model.addAttribute("architectDTO", new ArchitectDTO());
        model.addAttribute("buildings", buildingService.findAll());
        return "addpages/addarchitects";
    }

    @PostMapping("/add")
    public String processAddArchitect(Model model, @Valid @ModelAttribute("architectDTO") ArchitectDTO architectDTO, BindingResult errors) {
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(error -> logger.error(error.toString()));
            model.addAttribute("buildings", buildingService.findAll());
            return "addpages/addarchitects";
        } else {
            Architect architect = new Architect(architectDTO.getName(), architectDTO.getEstablishmentDate(), architectDTO.getNumberOfEmployees());

            List<Building> buildings = new ArrayList<>();
            architectDTO.getBuildingsIDs().forEach((id) -> buildings.add(buildingService.findById(id)));
            architect.addBuildings(buildings);
            buildings.forEach(building -> building.addArchitect(architect));

            architectService.addArchitect(architect);
            return "redirect:/architects";
        }
    }

    @GetMapping("/architectdetail")
    public String showArchitectDetail(@RequestParam("architectID") Integer architectID, Model model) {
        Architect architect = architectService.findById(architectID);
        model.addAttribute("architect", architect);
        return "detailpages/architectdetail";
    }

    @PostMapping(params = {"delete"})
    public String removeArchitect(@ModelAttribute("deletingDTO") DeletingDTO deletingDTO) {
        architectService.delete(deletingDTO.getID());
        return "redirect:/architects";
    }
}

