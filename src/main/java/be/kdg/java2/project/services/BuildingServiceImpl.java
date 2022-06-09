package be.kdg.java2.project.services;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.domain.Building;
import be.kdg.java2.project.repository.ArchitectRepository;
import be.kdg.java2.project.repository.BuildingRepository;
import be.kdg.java2.project.utils.LocationCheckerUtil;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BuildingServiceImpl implements BuildingService {
    private final BuildingRepository buildingRepository;

    private final ArchitectRepository architectRepository;

    public BuildingServiceImpl(BuildingRepository buildingRepository, ArchitectRepository architectRepository) {
        this.buildingRepository = buildingRepository;
        this.architectRepository = architectRepository;
    }

    @Override
    public List<Building> findAll() {
        return buildingRepository.findAll();
    }

    @Override
    public Building findByIdWithArchitectsAndType(int id) {
        return buildingRepository.findByIdWithArchitectsAndType(id);
    }

    @Override
    public Building findById(int id) {
        return buildingRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(int id) {
        Building buildingToDelete = buildingRepository.findById(id).orElse(null);
        buildingToDelete.getArchitects()
                .forEach(architect -> architect.removeBuilding(buildingToDelete));
        buildingRepository.delete(buildingToDelete);
    }

    @Override
    @Transactional
    public void addBuilding(Building building) {
        buildingRepository.save(building);
        if (building.getArchitects() != null) {
            building.getArchitects().forEach(architect -> architectRepository.addBuilding(architect.getId(), building));
        }
    }

    @Override
    public List<Building> findByLocation(String location) {
        return buildingRepository.findBuildingByLocation(location);
    }

    @Override
    public void addArchitectToBuilding(int id, Architect architect){
        buildingRepository.addArchitect(id, architect);
    }
}
