package be.kdg.java2.project.repository;

import be.kdg.java2.project.domain.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Integer> {
    List<Building> findBuildingByLocation(String location);

    Building findByName(String name);
}
