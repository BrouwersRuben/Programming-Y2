package be.kdg.java2.project.domain;

public enum BuildingType {
    RESIDENTIAL("residential", "st8j", true), EDUCATIONAL("educational", "a2nb", false), COMMERCIAL("commercial", "hjw6", false), INSTITUTIONAL("institutional", "h5bp", false), BUSINESS("business", "s3n", false), INDUSTRIAL("industrial", "5huy", true), STORAGE("storage", "gy1w", false), DETACHED("detached", "n0jx", false), HIGHRISE("highrise", "q9qj", true), SLUMS("slums", "gp7j", true), UNSAFE("unsafe", "r3cZ", false), SPECIAL("special", "k6ca", false), PARKING("parking", "Z1og", true);

    // Variables
    private final String type;
    private final String buildingCode;
    private final boolean needsSpecialPermissions;

    // Constructor
    BuildingType(String type, String buildingCode, boolean needsSpecialPermissions) {
        this.type = type;
        this.buildingCode = buildingCode;
        this.needsSpecialPermissions = needsSpecialPermissions;
    }

    // Getters
    public String getBuildingCode() {
        return buildingCode;
    }

    public boolean isNeedsSpecialPermissions() {
        return needsSpecialPermissions;
    }

    public String getType() {
        return type;
    }
}