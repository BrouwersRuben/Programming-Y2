package be.kdg.java2.project.services;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.domain.Building;
import be.kdg.java2.project.repository.ArchitectRepository;
import be.kdg.java2.project.repository.BuildingRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ArchitectServiceImpl implements ArchitectService {
    private final ArchitectRepository architectRepository;

    private final  BuildingRepository buildingRepository;

    public ArchitectServiceImpl(ArchitectRepository architectRepository, BuildingRepository buildingRepository) {
        this.architectRepository = architectRepository;
        this.buildingRepository = buildingRepository;
    }

    @Override
    public List<Architect> findAll() {
        return architectRepository.findAll();
    }

    @Override
    public Architect findById(int id) {
        return architectRepository.findById(id).orElse(null);
    }

    @Override
    public Architect findArchitectWithBuildingsAndEmployeesByID(int id){
        return architectRepository.findArchitectWithBuildingsAndEmployeesByID(id);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Architect architectToBeDeleted = architectRepository.findById(id).orElseThrow();
        architectToBeDeleted.getBuildings()
                .forEach(building -> building.removeArchitect(architectToBeDeleted));
        architectRepository.delete(architectToBeDeleted);
    }

    @Override
    @Transactional
    public void addArchitect(Architect architect) {
        architectRepository.save(architect);
        if (architect.getBuildings() != null){
            architect.getBuildings().forEach(building -> buildingRepository.addArchitect(building.getId(), architect));
        }
    }

    @Override
    public List<Architect> findArchitectsByNumberOfEmployeesIsGreaterThan(int numberOfEmployees) {
        return architectRepository.findArchitectsByNumberOfEmployeesIsGreaterThan(numberOfEmployees);
    }

    @Override
    public List<Architect> findArchitectsByNumberOfEmployeesIsLessThan(int numberOfEmployees) {
        return architectRepository.findArchitectsByNumberOfEmployeesIsLessThan(numberOfEmployees);
    }

    @Override
    public Architect findArchitectByNameCompany(String nameCompany) {
        return architectRepository.findArchitectByNameCompany(nameCompany);
    }

    @Override
    @Transactional
    public void updateArchitect(Architect architect) {
        architectRepository.save(architect);
    }

    @Override
    public void addBuildingToArchitect(int id, Building building) {
        architectRepository.addBuilding(id, building);
    }
}
