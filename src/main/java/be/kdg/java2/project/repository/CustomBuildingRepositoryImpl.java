package be.kdg.java2.project.repository;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.domain.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Queue;

@Repository
public class CustomBuildingRepositoryImpl implements CustomBuildingRepository{

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public CustomBuildingRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<Building> findBuildingByLocation(String location) {
        var em = entityManagerFactory.createEntityManager();

        String queryBuildings = "SELECT b FROM Building b WHERE b.location = :location";

        em.getTransaction().begin();

        Query buildingQuery = em.createQuery(queryBuildings);
        buildingQuery.setParameter("location",  location);

        var foundBuildings = (List<Building>) buildingQuery.getResultList();

        em.getTransaction().commit();

        return foundBuildings;
    }

    @Override
    public void addArchitect(int id, Architect architect) {
        var em = entityManagerFactory.createEntityManager();
        String queryBuilding = "SELECT b FROM Building b WHERE b.id = :id";

        em.getTransaction().begin();

        TypedQuery<Building> buildingQuery = em.createQuery(queryBuilding, Building.class);
        buildingQuery.setParameter("id", id);
        var building = buildingQuery.getSingleResult();

        building.addArchitect(architect);

        em.close();
    }
}
