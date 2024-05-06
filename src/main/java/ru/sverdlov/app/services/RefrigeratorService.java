package ru.sverdlov.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sverdlov.app.models.Model;
import ru.sverdlov.app.models.refrigerator.Refrigerator;
import ru.sverdlov.app.models.util.error.EntityNotFoundException;
import ru.sverdlov.app.repositories.RefrigeratorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RefrigeratorService implements IService<Refrigerator>{
    private final RefrigeratorRepository refrigeratorRepository;
    private final ModelService modelService;

    @Autowired
    public RefrigeratorService(RefrigeratorRepository refrigeratorRepository, ModelService modelService) {
        this.refrigeratorRepository = refrigeratorRepository;
        this.modelService = modelService;
    }

    @Override
    public List<Refrigerator> findAll(){return refrigeratorRepository.findAll();}

    @Override
    public Refrigerator findOne(int id) {
        Optional<Refrigerator> refrigerator = refrigeratorRepository.findById(id);
        return refrigerator.orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Optional<Refrigerator> findOne(Refrigerator refrigerator) {
        Optional<Model> modelOptional = Optional.empty();
        if(refrigerator.getModel() != null)
            modelOptional = modelService.findOne(refrigerator.getModel());

        return refrigeratorRepository.findOneByModelAndNumberOfDoorsAndCompressorType(
                modelOptional.orElse(null),
                refrigerator.getNumberOfDoors(),
                refrigerator.getCompressorType()
        );
    }

    @Override
    public void save(Refrigerator refrigerator) {
        Optional<Refrigerator> refrigeratorFromDatabase = findOne(refrigerator);

        if(refrigeratorFromDatabase.isEmpty()){
            modelService.save(refrigerator.getModel());
            refrigeratorRepository.save(refrigerator);
        }
    }
}
