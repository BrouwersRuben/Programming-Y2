package be.kdg.java2.project.bootstrap;

import be.kdg.java2.project.domain.*;
import be.kdg.java2.project.repository.ArchitectRepository;
import be.kdg.java2.project.repository.BuildingRepository;
import be.kdg.java2.project.repository.TypeOfBuildingRepository;
import be.kdg.java2.project.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Configuration
@Transactional
@Profile("!test")
public class SeedData implements CommandLineRunner {
    private final BuildingRepository buildingRepository;
    private final TypeOfBuildingRepository typeOfBuildingRepository;
    private final ArchitectRepository architectRepository;
    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(SeedData.class);

    public SeedData(BuildingRepository buildingRepository, TypeOfBuildingRepository typeOfBuildingRepository, ArchitectRepository architectRepository, UserRepository userRepository) {
        this.buildingRepository = buildingRepository;
        this.typeOfBuildingRepository = typeOfBuildingRepository;
        this.architectRepository = architectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.debug("Seeding the repositories (H2)");

        // Types of buildings
        TypeOfBuilding residential = new TypeOfBuilding(BuildingType.RESIDENTIAL);
        TypeOfBuilding educational = new TypeOfBuilding(BuildingType.EDUCATIONAL);
        TypeOfBuilding commercial = new TypeOfBuilding(BuildingType.COMMERCIAL);
        TypeOfBuilding institutional = new TypeOfBuilding(BuildingType.INSTITUTIONAL);
        TypeOfBuilding business = new TypeOfBuilding(BuildingType.BUSINESS);
        TypeOfBuilding industrial = new TypeOfBuilding(BuildingType.INDUSTRIAL);
        TypeOfBuilding storage = new TypeOfBuilding(BuildingType.STORAGE);
        TypeOfBuilding detached = new TypeOfBuilding(BuildingType.DETACHED);
        TypeOfBuilding highrise = new TypeOfBuilding(BuildingType.HIGHRISE);
        TypeOfBuilding slums = new TypeOfBuilding(BuildingType.SLUMS);
        TypeOfBuilding unsafe = new TypeOfBuilding(BuildingType.UNSAFE);
        TypeOfBuilding special = new TypeOfBuilding(BuildingType.SPECIAL);
        TypeOfBuilding parking = new TypeOfBuilding(BuildingType.PARKING);

        // Buildings
        Building portAuthority = new Building("Port Authority", "Antwerp, Belgium", 46, business);
        Building vitraFireStation = new Building("Vitra Fire Station", "Weil-am-Reinn, Germany", 12, institutional);
        Building sydneyOperaHouse = new Building("Sydney Opera House", "Sydney, Australia", 65, special);
        Building unoXPetrolStation = new Building("The Uno-X Petrol Station", "Denmark", 10, commercial);

        // Architects
        Architect zahaHadid = new Architect("Zaha Hadid Architects", LocalDate.of(1980, 1, 1), 708);
        Architect utzon = new Architect("Utzon Associates Architects", LocalDate.of(1982, 1, 1), 69);
        Architect grimmParker = new Architect("Grimm and Parker Architects", LocalDate.of(1972, 1, 1), 70);
        Architect diamondSchmitt = new Architect("Diamond Schmitt Architects", LocalDate.of(1975, 1, 1), 25);

        // Users
        User updater = new User("Updater", "updater@kdg.be", Role.U, "$2a$10$ng5ekeJ2KHTAlhRkQV1jeeDjElLC1SBcMnmyS.bNmD3zUZ6PnpzKK", zahaHadid);
        User creater = new User("Creator", "creator@kdg.be", Role.CD, "$2a$10$109Cs92jQJDwixf5Opq9/ewRkTHKXfpI9h46cqnyWrSirH3oQpL.S", grimmParker);
        User user = new User("User", "user@kdg.be", Role.N, "$2a$10$rdzY3uR5u92hIJfi/0sS7.6k6UIS0YXVDpvMPDCYpgMc2/JJqkZhy", diamondSchmitt);

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
        zahaHadid.addUser(updater);

        utzon.addBuilding(sydneyOperaHouse);
        utzon.addBuilding(unoXPetrolStation);

        grimmParker.addBuilding(portAuthority);
        grimmParker.addBuilding(sydneyOperaHouse);
        grimmParker.addUser(creater);

        diamondSchmitt.addBuilding(vitraFireStation);
        diamondSchmitt.addBuilding(unoXPetrolStation);
        diamondSchmitt.addUser(user);

        typeOfBuildingRepository.save(residential);
        typeOfBuildingRepository.save(educational);
        typeOfBuildingRepository.save(commercial);
        typeOfBuildingRepository.save(institutional);
        typeOfBuildingRepository.save(business);
        typeOfBuildingRepository.save(industrial);
        typeOfBuildingRepository.save(storage);
        typeOfBuildingRepository.save(detached);
        typeOfBuildingRepository.save(highrise);
        typeOfBuildingRepository.save(slums);
        typeOfBuildingRepository.save(unsafe);
        typeOfBuildingRepository.save(special);
        typeOfBuildingRepository.save(parking);

        buildingRepository.save(portAuthority);
        buildingRepository.save(vitraFireStation);
        buildingRepository.save(sydneyOperaHouse);
        buildingRepository.save(unoXPetrolStation);

        architectRepository.save(zahaHadid);
        architectRepository.save(utzon);
        architectRepository.save(grimmParker);
        architectRepository.save(diamondSchmitt);

        userRepository.save(updater);
        userRepository.save(creater);
        userRepository.save(user);
    }
}
