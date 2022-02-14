package be.kdg.java2.project.presentation.api.dto.architect;

public class ArchitectUpdateDTO {
    private int id;
    private int numberOfEmployees;

    public ArchitectUpdateDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }
}
