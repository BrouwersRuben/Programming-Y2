package be.kdg.java2.project.repository;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.domain.Building;

import java.util.List;

public interface CustomBuildingRepository {

    List<Building> findBuildingByLocation(String location);
    void addArchitect(int id, Architect architect);
}
