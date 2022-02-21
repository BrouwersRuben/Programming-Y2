package be.kdg.java2.project.repository;

import be.kdg.java2.project.domain.Architect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArchitectRepository extends JpaRepository<Architect, Integer> {
    @Query("SELECT a from Architect a WHERE a.numberOfEmployees > ?1")
    List<Architect> findArchitectsByNumberOfEmployeesIsGreaterThan(int numberOfEmployees);

    Architect findArchitectByNameCompany(String nameCompany);
}
