package ru.sverdlov.test.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sverdlov.test.models.Technic;
import ru.sverdlov.test.repositories.TechnicRepository;
import ru.sverdlov.test.models.util.utilTechnic.TechnicNotFoundException;

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
        return technic.orElseThrow(TechnicNotFoundException::new);
    }

    public Optional<Technic> findOne(Technic technic){
        return technicRepository.findByName(technic.getName());
    }

    public void save(Technic technic){
        technicRepository.save(technic);
    }
}

















