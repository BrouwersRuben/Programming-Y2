package be.kdg.java2.project.repository;

import be.kdg.java2.project.domain.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Integer>, CustomBuildingRepository {
    @Query("SELECT b FROM Building b JOIN FETCH b.architects JOIN FETCH b.typeOfBuilding WHERE b.id = :id")
    Building findByIdWithArchitectsAndType(int id);

    Building findByName(String name);
}
