package be.kdg.java2.project.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BUILDINGS")
public class Building extends EntityClass {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "BUILDING_NAME", nullable = false, unique = true, length = 30)
    private String name;

    @Column(name = "BUILDING_LOCATION", nullable = false, length = 30)
    private String location;

    @Column(name = "BUILDING_HEIGHT", nullable = false)
    private double height;

    @ManyToMany(mappedBy = "buildings", fetch = FetchType.LAZY)
    private List<Architect> architects;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_of_building_id")
    private TypeOfBuilding typeOfBuilding;

    protected Building() {
    }

    public Building(String name, String location, double height, TypeOfBuilding typeOfBuilding) {
        this.name = name;
        this.location = location;
        this.height = height;
        this.architects = new ArrayList<>();
        this.typeOfBuilding = typeOfBuilding;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public TypeOfBuilding getType() {
        return typeOfBuilding;
    }

    public void setTypeOfBuilding(TypeOfBuilding typeOfBuilding) {
        this.typeOfBuilding = typeOfBuilding;
    }

    public List<Architect> getArchitects() {
        return architects;
    }

    public void addArchitects(List<Architect> architectsList) {
        if (this.architects == null) {
            this.architects = new ArrayList<>();
        }
        this.architects.addAll(architectsList);
    }

    public void addArchitect(Architect architect) {
        if (this.architects == null) {
            this.architects = new ArrayList<>();
        }
        this.architects.add(architect);
    }

    public void removeArchitects(List<Architect> architectsList) {
        this.architects.removeAll(architectsList);
    }

    public void removeArchitect(Architect architect) {
        this.architects.remove(architect);
    }

    @Override
    public String toString() {
        return "Building{" + "name='" + name + '\'' + ", location='" + location + '\'' + ", height=" + height + '}';
    }
}
