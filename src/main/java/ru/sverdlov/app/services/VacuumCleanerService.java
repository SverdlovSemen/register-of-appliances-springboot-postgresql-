package ru.sverdlov.app.services;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.sverdlov.app.models.Model;
import ru.sverdlov.app.models.refrigerator.Refrigerator;
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

    public List<VacuumCleaner> findAllByFilters(String name, String color, Long minPrice, Long maxPrice, Integer minVolumeOfDustCollector,
                                    Integer maxVolumeOfDustCollector, Integer minNumberOfModes, Integer maxNumberOfModes){
        Specification<VacuumCleaner> spec = Specification.where(null);

        if(name != null && !name.isEmpty()){
            spec = spec.and((root, query, criteriaBuilder) -> {
                Join<Refrigerator, Model> join = root.join("model", JoinType.INNER);
                return criteriaBuilder.like(criteriaBuilder.lower(join.get("name")), "%" + name.toLowerCase() + "%");
            });
        }
        if (color != null && !color.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                Join<Refrigerator, Model> join = root.join("model", JoinType.INNER);
                return criteriaBuilder.equal(criteriaBuilder.lower(join.get("color")), color.toLowerCase());
            });
        }
        if (minPrice != null) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                Join<Refrigerator, Model> join = root.join("model", JoinType.INNER);
                return criteriaBuilder.greaterThanOrEqualTo(join.get("price"), minPrice);
            });
        }
        if (maxPrice != null) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                Join<Refrigerator, Model> join = root.join("model", JoinType.INNER);
                return criteriaBuilder.lessThanOrEqualTo(join.get("price"), maxPrice);
            });
        }
        if(minVolumeOfDustCollector != null){
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("volumeOfDustCollector"), minVolumeOfDustCollector));
        }
        if(maxVolumeOfDustCollector != null){
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("volumeOfDustCollector"), maxVolumeOfDustCollector));
        }
        if(minNumberOfModes != null){
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("numberOfModes"), minNumberOfModes));
        }
        if(maxNumberOfModes != null){
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("numberOfModes"), maxNumberOfModes));
        }

        return vacuumCleanerRepository.findAll(spec);
    }
}
