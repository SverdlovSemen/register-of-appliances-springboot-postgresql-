package ru.sverdlov.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sverdlov.app.models.Model;
import ru.sverdlov.app.models.smartphone.Smartphone;
import ru.sverdlov.app.models.util.error.EntityNotFoundException;
import ru.sverdlov.app.repositories.SmartphoneRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SmartphoneService implements IService<Smartphone> {
    private final SmartphoneRepository smartphoneRepository;
    private final ModelService modelService;

    @Autowired
    public SmartphoneService(SmartphoneRepository smartphoneRepository, ModelService modelService) {
        this.smartphoneRepository = smartphoneRepository;
        this.modelService = modelService;
    }

    @Override
    public List<Smartphone> findAll(){return smartphoneRepository.findAll();}

    @Override
    public Smartphone findOne(int id) {
        Optional<Smartphone> smartphone = smartphoneRepository.findById(id);
        return smartphone.orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Optional<Smartphone> findOne(Smartphone smartphone) {
        Optional<Model> modelOptional = Optional.empty();
        if(smartphone.getModel() != null)
            modelOptional = modelService.findOne(smartphone.getModel());

        return smartphoneRepository.findOneByModelAndMemoryAndNumberOfCameras(
                modelOptional.orElse(null),
                smartphone.getMemory(),
                smartphone.getNumberOfCameras()
        );
    }

    @Override
    public void save(Smartphone smartphone) {
        Optional<Smartphone> smartphoneFromDatabase = findOne(smartphone);

        if(smartphoneFromDatabase.isEmpty()){
            modelService.save(smartphone.getModel());
            smartphoneRepository.save(smartphone);
        }
    }
}
