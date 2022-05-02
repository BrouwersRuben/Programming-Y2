package be.kdg.java2.project.repository;

import be.kdg.java2.project.domain.Architect;

public interface CustomArchitectRepository {
    Architect findArchitectWithBuildingsAndEmployeesByID(int id);
}
