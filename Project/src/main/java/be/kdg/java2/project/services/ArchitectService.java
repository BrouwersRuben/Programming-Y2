package be.kdg.java2.project.services;

import be.kdg.java2.project.domain.Architect;

import java.util.List;

public interface ArchitectService {
    List<Architect> findAll();

    Architect findById(int id);

    void delete(int id);

    void addArchitect(Architect architect);

    List<Architect> findArchitectsByNumberOfEmployeesIsGreaterThan(int numberOfEmployees);

    List<Architect> findArchitectByNameCompany(String nameCompany);

    void updateArchitect(Architect architect);
}
