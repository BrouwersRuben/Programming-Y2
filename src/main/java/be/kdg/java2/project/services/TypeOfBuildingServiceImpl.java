package be.kdg.java2.project.services;

import be.kdg.java2.project.domain.BuildingType;
import be.kdg.java2.project.domain.TypeOfBuilding;
import be.kdg.java2.project.repository.TypeOfBuildingRepository;
import org.springframework.stereotype.Service;

@Service
public class TypeOfBuildingServiceImpl implements TypeOfBuildingService{

    private final TypeOfBuildingRepository typeOfBuildingRepository;

    public TypeOfBuildingServiceImpl(TypeOfBuildingRepository typeOfBuildingRepository) {
        this.typeOfBuildingRepository = typeOfBuildingRepository;
    }

    @Override
    public TypeOfBuilding findByType(BuildingType type) {
        return typeOfBuildingRepository.findByType(type);
    }
}
