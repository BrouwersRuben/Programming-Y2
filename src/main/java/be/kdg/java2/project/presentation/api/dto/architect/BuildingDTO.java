package be.kdg.java2.project.presentation.api.dto.architect;

import be.kdg.java2.project.presentation.api.dto.BuildingTypeDTO;

public class BuildingDTO {
    private int id;
    private String name;
    private String location;
    private double height;
    private BuildingTypeDTO type;

    public BuildingDTO() {
    }

    public int getId() {
        return id;
    }

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

    public BuildingTypeDTO getType() {
        return type;
    }

    public void setType(BuildingTypeDTO type) {
        this.type = type;
    }
}
