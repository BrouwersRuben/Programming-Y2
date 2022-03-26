package be.kdg.java2.project.repository;

import be.kdg.java2.project.domain.BuildingType;
import be.kdg.java2.project.domain.TypeOfBuilding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeOfBuildingRepository extends JpaRepository<TypeOfBuilding, Integer> {
    TypeOfBuilding findByType(BuildingType type);
}
