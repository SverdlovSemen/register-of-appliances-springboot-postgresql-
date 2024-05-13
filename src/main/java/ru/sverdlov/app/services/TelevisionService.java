package ru.sverdlov.app.services;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.sverdlov.app.models.Model;
import ru.sverdlov.app.models.computer.Computer;
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

    public List<Television> findAllByFilters(String name, String color, Long minPrice, Long maxPrice, String category, String technology){
        Specification<Television> spec = Specification.where(null);

        if(name != null && !name.isEmpty()){
            spec = spec.and((root, query, criteriaBuilder) -> {
                Join<Computer, Model> join = root.join("model", JoinType.INNER);
                return criteriaBuilder.like(criteriaBuilder.lower(join.get("name")), "%" + name.toLowerCase() + "%");
            });
        }
        if (color != null && !color.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                Join<Computer, Model> join = root.join("model", JoinType.INNER);
                return criteriaBuilder.equal(criteriaBuilder.lower(join.get("color")), color.toLowerCase());
            });
        }
        if (minPrice != null) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                Join<Computer, Model> join = root.join("model", JoinType.INNER);
                return criteriaBuilder.greaterThanOrEqualTo(join.get("price"), minPrice);
            });
        }
        if (maxPrice != null) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                Join<Computer, Model> join = root.join("model", JoinType.INNER);
                return criteriaBuilder.lessThanOrEqualTo(join.get("price"), maxPrice);
            });
        }
        if(category != null && !category.isEmpty()){
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("category")), category.toLowerCase()));
        }
        if(technology != null && !technology.isEmpty()){
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("technology")), technology.toLowerCase()));
        }
        return televisionRepository.findAll(spec);
    }
}
