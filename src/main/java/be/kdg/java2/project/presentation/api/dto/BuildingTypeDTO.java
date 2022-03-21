package be.kdg.java2.project.presentation.api.dto;

import be.kdg.java2.project.domain.BuildingType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class BuildingTypeDTO {
    private int id;
    private String code;
    @Enumerated(EnumType.STRING)
    private BuildingType type;
    private boolean requiresSpecialPermission;

    public BuildingTypeDTO() {
    }

    public int getId() {
        return id;
    }

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

    public void setType(BuildingType type) {
        this.type = type;
    }

    public boolean isRequiresSpecialPermission() {
        return requiresSpecialPermission;
    }

    public void setRequiresSpecialPermission(boolean requiresSpecialPermission) {
        this.requiresSpecialPermission = requiresSpecialPermission;
    }
}
