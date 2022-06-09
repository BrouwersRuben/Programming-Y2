package be.kdg.java2.project.repository;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.domain.Building;
import be.kdg.java2.project.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CustomArchitectRepositoryImpl implements CustomArchitectRepository{
    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public CustomArchitectRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Architect findArchitectWithBuildingsAndEmployeesByID(int id) {
        var em = entityManagerFactory.createEntityManager();
        Architect foundArchitect;

        String queryArchitect = "SELECT a FROM Architect a JOIN FETCH a.buildings WHERE a.id = :id";

        em.getTransaction().begin();

        TypedQuery<Architect> architectQuery = em.createQuery(queryArchitect, Architect.class);
        architectQuery.setParameter("id", id);
        foundArchitect = architectQuery.getSingleResult();

        em.getTransaction().commit();

        return foundArchitect;
    }

    @Override
    public void addBuilding(int id, Building building) {
        var em = entityManagerFactory.createEntityManager();
        String queryArchitect = "SELECT a FROM Architect a WHERE a.id = :id";

        em.getTransaction().begin();

        TypedQuery<Architect> architectQuery = em.createQuery(queryArchitect, Architect.class);
        architectQuery.setParameter("id", id);
        var architect = architectQuery.getSingleResult();

        architect.addBuilding(building);

        em.close();
    }
}
