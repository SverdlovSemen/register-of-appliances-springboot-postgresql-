package ru.sverdlov.app.services;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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

    public List<Smartphone> findAllByFilters(String name, String color, Long minPrice, Long maxPrice, Integer minMemory,
                                             Integer maxMemory, Integer minNumberOfCameras, Integer maxNumberOfCameras){
        Specification<Smartphone> spec = Specification.where(null);

        if(name != null && !name.isEmpty()){
            spec = spec.and((root, query, criteriaBuilder) -> {
                Join<Smartphone, Model> join = root.join("model", JoinType.INNER);
                return criteriaBuilder.like(criteriaBuilder.lower(join.get("name")), "%" + name.toLowerCase() + "%");
            });
        }
        if (color != null && !color.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                Join<Smartphone, Model> join = root.join("model", JoinType.INNER);
                return criteriaBuilder.equal(criteriaBuilder.lower(join.get("color")), color.toLowerCase());
            });
        }
        if (minPrice != null) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                Join<Smartphone, Model> join = root.join("model", JoinType.INNER);
                return criteriaBuilder.greaterThanOrEqualTo(join.get("price"), minPrice);
            });
        }
        if (maxPrice != null) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                Join<Smartphone, Model> join = root.join("model", JoinType.INNER);
                return criteriaBuilder.lessThanOrEqualTo(join.get("price"), maxPrice);
            });
        }
        if(minMemory != null){
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("memory"), minMemory));
        }
        if(maxMemory != null){
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("memory"), maxMemory));
        }
        if(minNumberOfCameras != null){
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("numberOfCameras"), minNumberOfCameras));
        }
        if(maxNumberOfCameras != null){
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("numberOfCameras"), maxMemory));
        }

        return smartphoneRepository.findAll(spec);
    }
}
