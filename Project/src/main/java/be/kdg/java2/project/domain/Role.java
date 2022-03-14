package be.kdg.java2.project.domain;

public enum Role {
    N("ROLE_USER"), U("ROLE_UPDATER"), CD("ROLE_CREATOR");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
