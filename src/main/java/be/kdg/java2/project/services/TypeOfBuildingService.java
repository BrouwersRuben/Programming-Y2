package be.kdg.java2.project.services;

import be.kdg.java2.project.domain.BuildingType;
import be.kdg.java2.project.domain.TypeOfBuilding;

public interface TypeOfBuildingService {
    TypeOfBuilding findByType(BuildingType type);
}
