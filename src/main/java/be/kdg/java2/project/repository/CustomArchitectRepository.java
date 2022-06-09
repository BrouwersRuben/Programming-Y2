package be.kdg.java2.project.repository;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.domain.Building;

public interface CustomArchitectRepository {
    Architect findArchitectWithBuildingsAndEmployeesByID(int id);

    void addBuilding(int id, Building building);
}
