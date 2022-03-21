package be.kdg.java2.project.utils;

import be.kdg.java2.project.domain.Building;
import be.kdg.java2.project.exceptions.LocationNotFoundException;

import java.util.List;
import java.util.Objects;

public class LocationCheckerUtil {
    public static void checkLocation(String location, List<Building> buildings) {
        boolean result = buildings.stream().anyMatch(building -> Objects.equals(building.getLocation(), location));
        if (!result) throw new LocationNotFoundException("This location is not found in the database.", location);
//        if (buildings.stream().noneMatch(building -> Objects.equals(building.getLocation(), location))) throw new LocationNotFoundException("This location is not found in the database.", location);
    }
}
