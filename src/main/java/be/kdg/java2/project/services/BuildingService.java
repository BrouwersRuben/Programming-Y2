package be.kdg.java2.project.services;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.domain.Building;

import java.util.List;

public interface BuildingService {
    List<Building> findAll();

    Building findByIdWithArchitectsAndType(int id);

    Building findById(int id);

    void delete(int id);

    void addBuilding(Building building);

    List<Building> findByLocation(String location);

    void addArchitectToBuilding(int id, Architect architect);
}
