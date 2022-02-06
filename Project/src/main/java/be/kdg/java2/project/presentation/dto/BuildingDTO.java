package be.kdg.java2.project.presentation.dto;

import be.kdg.java2.project.domain.BuildingType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class BuildingDTO {
    // Attributes
    @NotEmpty(message = "The name of the building should be given.")
    @Size(min = 5, max = 30, message = "The name should have a length between 5 and 30.")
    private String name;

    @NotEmpty(message = "The location of the building should be given.")
    @Size(min = 5, max = 30, message = "The location name should have a length between 5 and 30.")
    private String location;

    @Min(value = 1, message = "The height of the building should be at least 1m")
    private int height;

    @NotEmpty(message = "There should be at least 1 architect firm assigned to this building")
    private List<Integer> architectsIDs;

    @NotNull(message = "The type of the building should be given")
    private BuildingType type;

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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Integer> getArchitectsIDs() {
        return architectsIDs;
    }

    public void setArchitectsIDs(List<Integer> architectsIDs) {
        this.architectsIDs = architectsIDs;
    }

    public BuildingType getType() {
        return type;
    }

    public void setType(BuildingType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BuildingDTO{" + "name='" + name + '\'' + ", location='" + location + '\'' + ", height=" + height + ", architectsIDs=" + architectsIDs + ", stringType='" + type + '\'' + '}';
    }
}
