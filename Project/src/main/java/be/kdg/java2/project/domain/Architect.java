package be.kdg.java2.project.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "architects")
public class Architect extends EntityClass {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "company_name", nullable = false, unique = true, length = 30)
    private String nameCompany;

    @Column(name = "establishment_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate establishmentDate;

    @Column(name = "number_of_employees", nullable = false)
    private int numberOfEmployees;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "architect_building", joinColumns = @JoinColumn(name = "architect_id"), inverseJoinColumns = @JoinColumn(name = "building_id"))
    private List<Building> buildings;
    // List, because then elements can be retrieved at index, could use set or map for the unique stuff, but didn't

    @OneToMany(mappedBy = "workingFirm", cascade = CascadeType.ALL)
    private List<User> users;

    protected Architect() {
    }

    public Architect(String nameCompany, LocalDate establishmentDate, int numberOfEmployees) {
        this.nameCompany = nameCompany;
        this.establishmentDate = establishmentDate;
        this.numberOfEmployees = numberOfEmployees;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public LocalDate getEstablishmentDate() {
        return establishmentDate;
    }

    public void setEstablishmentDate(LocalDate establishmentDate) {
        this.establishmentDate = establishmentDate;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void addBuildings(List<Building> buildings) {
        if (this.buildings == null) {
            this.buildings = new ArrayList<>();
        }
        this.buildings.addAll(buildings);
    }

    public void addBuilding(Building building) {
        if (this.buildings == null) {
            this.buildings = new ArrayList<>();
        }
        this.buildings.add(building);
    }

    public void removeBuildings(List<Building> buildings) {
        this.buildings.removeAll(buildings);
    }

    public void removeBuilding(Building building) {
        this.buildings.remove(building);
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUsers(List<User> users) {
        if (this.users == null) {
            this.users = new ArrayList<>();
        }
        this.users.addAll(users);
    }

    public void addUser(User user) {
        if (this.users == null) {
            this.users = new ArrayList<>();
        }
        this.users.add(user);
    }
}
