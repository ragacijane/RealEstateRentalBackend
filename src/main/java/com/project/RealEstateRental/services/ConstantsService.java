package com.project.RealEstateRental.services;

import com.project.RealEstateRental.models.Boroughs;
import com.project.RealEstateRental.models.Equipments;
import com.project.RealEstateRental.models.Structures;
import com.project.RealEstateRental.models.Types;
import com.project.RealEstateRental.repositories.BoroughsRepository;
import com.project.RealEstateRental.repositories.EquipmentsRepository;
import com.project.RealEstateRental.repositories.StructuresRepository;
import com.project.RealEstateRental.repositories.TypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ConstantsService {
    private final BoroughsRepository boroughsRepository;
    private final EquipmentsRepository equipmentsRepository;
    private final TypesRepository typesRepository;
    private final StructuresRepository structuresRepository;

    @Autowired
    public ConstantsService(BoroughsRepository boroughsRepository, EquipmentsRepository equipmentsRepository, TypesRepository typesRepository, StructuresRepository structuresRepository) {
        this.boroughsRepository = boroughsRepository;
        this.equipmentsRepository = equipmentsRepository;
        this.typesRepository = typesRepository;
        this.structuresRepository = structuresRepository;
    }
    public List<Boroughs> getBoroughs(){
        return boroughsRepository.findAll();
    }
    public List<Structures> getStructures(){
        return structuresRepository.findAll();
    }
    public List<Types> getTypes(){
        return typesRepository.findAll();
    }
    public List<Equipments> getEquipments(){
        return equipmentsRepository.findAll();
    }
}
