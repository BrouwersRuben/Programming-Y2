package be.kdg.java2.project.services;

import be.kdg.java2.project.domain.Building;
import be.kdg.java2.project.repository.BuildingRepository;
import be.kdg.java2.project.utils.LocationCheckerUtil;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BuildingServiceImpl implements BuildingService {
    private final BuildingRepository buildingRepository;

    public BuildingServiceImpl(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    @Override
    public List<Building> findAll() {
        return buildingRepository.findAll();
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
    public void addBuilding(Building building) {
        buildingRepository.save(building);
    }

    @Override
    public List<Building> findByLocation(String location) {
        // Search within DB with a where clause, more efficient. should be done in db.
//        LocationCheckerUtil.checkLocation(location, buildingRepository.findAll());
        return buildingRepository.findBuildingByLocation(location);
    }
}
