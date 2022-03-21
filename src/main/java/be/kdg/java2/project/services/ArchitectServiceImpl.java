package be.kdg.java2.project.services;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.repository.ArchitectRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ArchitectServiceImpl implements ArchitectService {
    private final ArchitectRepository architectRepository;

    public ArchitectServiceImpl(ArchitectRepository architectRepository) {
        this.architectRepository = architectRepository;
    }

    @Override
    public List<Architect> findAll() {
        return architectRepository.findAll();
    }

    @Override
    public Architect findById(int id) {
        return architectRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(int id) {
        Architect architectToBeDeleted = architectRepository.findById(id).orElseThrow();
        architectToBeDeleted.getBuildings()
                .forEach(building -> building.removeArchitect(architectToBeDeleted));
        architectRepository.delete(architectToBeDeleted);
    }

    @Override
    public void addArchitect(Architect architect) {
        architectRepository.save(architect);
    }

    @Override
    public List<Architect> findArchitectsByNumberOfEmployeesIsGreaterThan(int numberOfEmployees) {
        return architectRepository.findArchitectsByNumberOfEmployeesIsGreaterThan(numberOfEmployees);
    }

    @Override
    public List<Architect> findArchitectsByNumberOfEmployeesIsLessThan(int numberOfEmployees) {
        return architectRepository.findArchitectsByNumberOfEmployeesIsLessThan(numberOfEmployees);
    }

    @Override
    public Architect findArchitectByNameCompany(String nameCompany) {
        return architectRepository.findArchitectByNameCompany(nameCompany);
    }

    @Override
    public void updateArchitect(Architect architect) {
        architectRepository.save(architect);
    }
}