package ru.sverdlov.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sverdlov.app.models.Model;
import ru.sverdlov.app.models.television.Television;
import ru.sverdlov.app.models.util.error.EntityNotFoundException;
import ru.sverdlov.app.repositories.TelevisionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TelevisionService implements IService<Television>{
    private final TelevisionRepository televisionRepository;
    private final ModelService modelService;

    @Autowired
    public TelevisionService(TelevisionRepository televisionRepository, ModelService modelService) {
        this.televisionRepository = televisionRepository;
        this.modelService = modelService;
    }

    @Override
    public List<Television> findAll(){return televisionRepository.findAll();}

    @Override
    public Television findOne(int id) {
        Optional<Television> television = televisionRepository.findById(id);
        return television.orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Optional<Television> findOne(Television television) {
        Optional<Model> modelOptional = Optional.empty();
        if(television.getModel() != null)
            modelOptional = modelService.findOne(television.getModel());

        return televisionRepository.findOneByModelAndCategoryAndTechnology(
                modelOptional.orElse(null),
                television.getCategory(),
                television.getTechnology()
        );
    }

    @Override
    public void save(Television television) {
        Optional<Television> televisionFromDatabase = findOne(television);

        if(televisionFromDatabase.isEmpty()){
            modelService.save(television.getModel());
            televisionRepository.save(television);
        }
    }
}
