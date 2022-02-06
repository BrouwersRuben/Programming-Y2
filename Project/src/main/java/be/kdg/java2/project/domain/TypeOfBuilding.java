package be.kdg.java2.project.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "types_of_buildings")
public class TypeOfBuilding extends EntityClass {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "code", nullable = false, unique = true, length = 4)
    private String code;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private BuildingType type;

    @Column(name = "permissions", nullable = false)
    private boolean requiresSpecialPermission;

    @OneToMany(mappedBy = "typeOfBuilding", cascade = CascadeType.ALL)
    private List<Building> buildings;
    // One type can have many buildings

    // Constructors
    protected TypeOfBuilding() {
    }

    public TypeOfBuilding(BuildingType type) {
        this.type = type;
        this.code = type.getBuildingCode();
        this.requiresSpecialPermission = type.isNeedsSpecialPermissions();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BuildingType getType() {
        return type;
    }

    public boolean isRequiresSpecialPermission() {
        return requiresSpecialPermission;
    }

    public void setRequiresSpecialPermission(boolean requiresSpecialPermission) {
        this.requiresSpecialPermission = requiresSpecialPermission;
    }

    public List<Building> getBuilding() {
        return buildings;
    }

    public void setBuilding(Building building) {
        if (this.buildings == null) {
            this.buildings = new ArrayList<>();
        }
        this.buildings.add(building);
    }

    @Override
    public String toString() {
        return type.toString().toLowerCase();
    }
}


