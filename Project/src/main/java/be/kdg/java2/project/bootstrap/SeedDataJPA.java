package be.kdg.java2.project.bootstrap;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.domain.Building;
import be.kdg.java2.project.domain.BuildingType;
import be.kdg.java2.project.domain.TypeOfBuilding;
import be.kdg.java2.project.repository.ArchitectRepository;
import be.kdg.java2.project.repository.BuildingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Configuration
@Transactional
public class SeedDataJPA implements CommandLineRunner {
    private final BuildingRepository buildingRepository;
    private final ArchitectRepository architectRepository;

    private final Logger logger = LoggerFactory.getLogger(SeedDataJPA.class);

    public SeedDataJPA(BuildingRepository buildingRepository, ArchitectRepository architectRepository) {
        this.buildingRepository = buildingRepository;
        this.architectRepository = architectRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.debug("Seeding the repositories (H2)");

        // Buildings
        Building portAuthority = new Building("Port Authority", "Antwerp, Belgium", 46, new TypeOfBuilding(BuildingType.BUSINESS));
        Building vitraFireStation = new Building("Vitra Fire Station", "Weil-am-Reinn, Germany", 12, new TypeOfBuilding(BuildingType.INSTITUTIONAL));
        Building sydneyOperaHouse = new Building("Sydney Opera House", "Sydney, Australia", 65, new TypeOfBuilding(BuildingType.SPECIAL));
        Building unoXPetrolStation = new Building("The Uno-X Petrol Station", "Denmark", 10, new TypeOfBuilding(BuildingType.COMMERCIAL));

        // Architects
        Architect zahaHadid = new Architect("Zaha Hadid Architects", LocalDate.of(1980, 1, 1), 708);
        Architect utzon = new Architect("Utzon Associates Architects", LocalDate.of(1982, 1, 1), 69);
        Architect grimmParker = new Architect("Grimm and Parker Architects", LocalDate.of(1972, 1, 1), 70);
        Architect diamondSchmitt = new Architect("Diamond Schmitt Architects", LocalDate.of(1975, 1, 1), 25);

        // Relations (not all of these are correct in the real world)
        portAuthority.addArchitect(zahaHadid);
        portAuthority.addArchitect(grimmParker);

        vitraFireStation.addArchitect(diamondSchmitt);
        vitraFireStation.addArchitect(zahaHadid);

        sydneyOperaHouse.addArchitect(utzon);
        sydneyOperaHouse.addArchitect(grimmParker);

        unoXPetrolStation.addArchitect(utzon);
        unoXPetrolStation.addArchitect(diamondSchmitt);

        zahaHadid.addBuilding(portAuthority);
        zahaHadid.addBuilding(vitraFireStation);

        utzon.addBuilding(sydneyOperaHouse);
        utzon.addBuilding(unoXPetrolStation);

        grimmParker.addBuilding(portAuthority);
        grimmParker.addBuilding(sydneyOperaHouse);

        diamondSchmitt.addBuilding(vitraFireStation);
        diamondSchmitt.addBuilding(unoXPetrolStation);

        buildingRepository.save(portAuthority);
        buildingRepository.save(vitraFireStation);
        buildingRepository.save(sydneyOperaHouse);
        buildingRepository.save(unoXPetrolStation);

        architectRepository.save(zahaHadid);
        architectRepository.save(utzon);
        architectRepository.save(grimmParker);
        architectRepository.save(diamondSchmitt);
    }
}
