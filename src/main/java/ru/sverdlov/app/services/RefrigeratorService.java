package ru.sverdlov.app.services;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.sverdlov.app.models.Model;
import ru.sverdlov.app.models.refrigerator.Refrigerator;
import ru.sverdlov.app.models.smartphone.Smartphone;
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

    public List<Refrigerator> findAllByFilters(String name, String color, Long minPrice, Long maxPrice, Integer minNumberOfDoors,
                                             Integer maxNumberOfDoors, String compressorType){
        Specification<Refrigerator> spec = Specification.where(null);

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
        if(minNumberOfDoors != null){
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("numberOfDoors"), minNumberOfDoors));
        }
        if(maxNumberOfDoors != null){
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("numberOfDoors"), maxNumberOfDoors));
        }
        if(compressorType != null && !compressorType.isEmpty()){
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("compressorType")), compressorType.toLowerCase()));
        }

        return refrigeratorRepository.findAll(spec);
    }
}






















