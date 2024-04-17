package ru.sverdlov.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sverdlov.app.models.Technic;
import ru.sverdlov.app.models.util.EntityNotFoundException;
import ru.sverdlov.app.repositories.TechnicRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TechnicService {
    private final TechnicRepository technicRepository;

    @Autowired
    public TechnicService(TechnicRepository technicRepository) {
        this.technicRepository = technicRepository;
    }

    public List<Technic> findAll(){
        return technicRepository.findAll();
    }

    public Technic findOne(int id){
        Optional<Technic> technic = technicRepository.findById(id);
        return technic.orElseThrow(EntityNotFoundException::new);
    }

    public Optional<Technic> findOne(Technic technic){
        return technicRepository.findByNameAndCountryOfOriginAndManufacturerAndIsPossibleOrderOnlineAndIsPossibleMakeInstallments(
                technic.getName(),
                technic.getCountryOfOrigin(),
                technic.getManufacturer(),
                technic.getPossibleOrderOnline(),
                technic.getPossibleMakeInstallments());
    }

    public void save(Technic technic){
        technicRepository.save(technic);
    }
}

















