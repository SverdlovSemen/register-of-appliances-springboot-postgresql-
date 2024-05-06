package ru.sverdlov.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sverdlov.app.models.Model;
import ru.sverdlov.app.models.vacuumCleaner.VacuumCleaner;
import ru.sverdlov.app.models.util.error.EntityNotFoundException;
import ru.sverdlov.app.repositories.VacuumCleanerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VacuumCleanerService implements IService<VacuumCleaner> {
    private final VacuumCleanerRepository vacuumCleanerRepository;
    private final ModelService modelService;

    @Autowired
    public VacuumCleanerService(VacuumCleanerRepository vacuumCleanerRepository, ModelService modelService) {
        this.vacuumCleanerRepository = vacuumCleanerRepository;
        this.modelService = modelService;
    }

    @Override
    public List<VacuumCleaner> findAll(){return vacuumCleanerRepository.findAll();}

    @Override
    public VacuumCleaner findOne(int id) {
        Optional<VacuumCleaner> vacuumCLeaner = vacuumCleanerRepository.findById(id);
        return vacuumCLeaner.orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Optional<VacuumCleaner> findOne(VacuumCleaner vacuumCLeaner) {
        Optional<Model> modelOptional = Optional.empty();
        if(vacuumCLeaner.getModel() != null)
            modelOptional = modelService.findOne(vacuumCLeaner.getModel());

        return vacuumCleanerRepository.findOneByModelAndVolumeOfDustCollectorAndNumberOfModes(
                modelOptional.orElse(null),
                vacuumCLeaner.getVolumeOfDustCollector(),
                vacuumCLeaner.getNumberOfModes()
        );
    }

    @Override
    public void save(VacuumCleaner vacuumCLeaner) {
        Optional<VacuumCleaner> vacuumCLeanerFromDatabase = findOne(vacuumCLeaner);

        if(vacuumCLeanerFromDatabase.isEmpty()){
            modelService.save(vacuumCLeaner.getModel());
            vacuumCleanerRepository.save(vacuumCLeaner);
        }
    }
}
