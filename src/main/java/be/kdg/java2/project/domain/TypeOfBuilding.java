package be.kdg.java2.project.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TYPES_OF_BUILDINGS")
public class TypeOfBuilding extends EntityClass {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "TYPE_CODE", nullable = false, unique = true, length = 4)
    private String code;

    @Column(name = "TYPE_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private BuildingType type;

    @Column(name = "TYPE_PERMISSIONS", nullable = false)
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

    public BuildingType getType() {
        return type;
    }

    public boolean isRequiresSpecialPermission() {
        return requiresSpecialPermission;
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


