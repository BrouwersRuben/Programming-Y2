package be.kdg.java2.project.repository;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArchitectRepository extends JpaRepository<Architect, Integer>, CustomArchitectRepository {

    @Query("SELECT a from Architect a WHERE a.numberOfEmployees > ?1")
    List<Architect> findArchitectsByNumberOfEmployeesIsGreaterThan(int numberOfEmployees);

    @Query("SELECT a from Architect a WHERE a.numberOfEmployees < ?1")
    List<Architect> findArchitectsByNumberOfEmployeesIsLessThan(int numberOfEmployees);

    Architect findArchitectByNameCompany(String nameCompany);
}
