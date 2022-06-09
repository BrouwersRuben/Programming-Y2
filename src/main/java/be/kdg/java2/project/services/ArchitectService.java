package be.kdg.java2.project.services;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.domain.Building;

import java.util.List;

public interface ArchitectService {
    List<Architect> findAll();

    Architect findById(int id);

    Architect findArchitectWithBuildingsAndEmployeesByID(int id);

    void delete(int id);

    void addArchitect(Architect architect);

    List<Architect> findArchitectsByNumberOfEmployeesIsGreaterThan(int numberOfEmployees);

    List<Architect> findArchitectsByNumberOfEmployeesIsLessThan(int numberOfEmployees);

    Architect findArchitectByNameCompany(String nameCompany);

    void updateArchitect(Architect architect);

    void addBuildingToArchitect(int id, Building building);
}
