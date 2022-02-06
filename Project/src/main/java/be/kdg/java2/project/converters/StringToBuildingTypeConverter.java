package be.kdg.java2.project.converters;

import be.kdg.java2.project.domain.BuildingType;
import org.springframework.core.convert.converter.Converter;

public class StringToBuildingTypeConverter implements Converter<String, BuildingType> {
    @Override
    public BuildingType convert(String source) {
        if (source.toLowerCase().startsWith("res")) return BuildingType.RESIDENTIAL;
        if (source.toLowerCase().startsWith("edu")) return BuildingType.EDUCATIONAL;
        if (source.toLowerCase().startsWith("com")) return BuildingType.COMMERCIAL;
        if (source.toLowerCase().startsWith("ins")) return BuildingType.INSTITUTIONAL;
        if (source.toLowerCase().startsWith("bus")) return BuildingType.BUSINESS;
        if (source.toLowerCase().startsWith("ind")) return BuildingType.INDUSTRIAL;
        if (source.toLowerCase().startsWith("sto")) return BuildingType.STORAGE;
        if (source.toLowerCase().startsWith("det")) return BuildingType.DETACHED;
        if (source.toLowerCase().startsWith("hig")) return BuildingType.HIGHRISE;
        if (source.toLowerCase().startsWith("slu")) return BuildingType.SLUMS;
        if (source.toLowerCase().startsWith("uns")) return BuildingType.UNSAFE;
        if (source.toLowerCase().startsWith("spe")) return BuildingType.SPECIAL;
        if (source.toLowerCase().startsWith("par")) return BuildingType.PARKING;
        return null;
    }
}
