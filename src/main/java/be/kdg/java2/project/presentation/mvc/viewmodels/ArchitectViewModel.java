package be.kdg.java2.project.presentation.mvc.viewmodels;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public class ArchitectViewModel {
    // Attributes
    @NotEmpty(message = "The name of the firm should be given.")
    @Size(min = 5, max = 30, message = "The name should have a length between 5 and 30.")
    private String name;

    @NotNull(message = "The Establishment date should be given.")
    @PastOrPresent(message = "The Establishment date should be in the past or today.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate establishmentDate;

    @Min(value = 1, message = "There should be a minimum of 1 employee")
    private int numberOfEmployees;

    private List<Integer> buildingsIDs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Integer> getBuildingsIDs() {
        return buildingsIDs;
    }

    public void setBuildingsIDs(List<Integer> buildingsIDs) {
        this.buildingsIDs = buildingsIDs;
    }

    @Override
    public String toString() {
        return "ArchitectViewModel{" + "name='" + name + '\'' + ", date=" + establishmentDate + ", numberOfEmployees=" + numberOfEmployees + ", buildingsIds=" + buildingsIDs + '}';
    }
}
