package be.kdg.java2.project.repository;

import be.kdg.java2.project.domain.Architect;
import be.kdg.java2.project.domain.Building;
import be.kdg.java2.project.domain.User;
import jdk.jfr.Registered;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CustomArchitectRepositoryImpl implements CustomArchitectRepository{
    private final EntityManager em;

    public CustomArchitectRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public Architect findArchitectWithBuildingsAndEmployeesByID(int id) {
        String queryArchitect = "SELECT a FROM Architect a JOIN FETCH a.buildings WHERE a.id = :id";
        String queryUsers = "SELECT u FROM User u WHERE u.workingFirm.id = :id";

        TypedQuery<Architect> architectQuery = em.createQuery(queryArchitect, Architect.class);
        architectQuery.setParameter("id", id);
        var architect = architectQuery.getSingleResult();

        Query userQuery = em.createQuery(queryUsers);
        userQuery.setParameter("id", id);
        var users = userQuery.getResultList();

        architect.addUsers(users);

        return architect;
    }
}
