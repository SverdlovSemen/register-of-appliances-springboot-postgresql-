package ru.sverdlov.app.services;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.sverdlov.app.models.Model;
import ru.sverdlov.app.models.Technic;
import ru.sverdlov.app.models.computer.Computer;
import ru.sverdlov.app.models.util.error.EntityNotFoundException;
import ru.sverdlov.app.repositories.ComputerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ComputerService implements IService<Computer> {
    private final ComputerRepository computerRepository;
    private final ModelService modelService;

    @Autowired
    public ComputerService(ComputerRepository computerRepository, ModelService modelService) {
        this.computerRepository = computerRepository;
        this.modelService = modelService;
    }

    @Override
    public List<Computer> findAll(){return computerRepository.findAll();}

    @Override
    public Computer findOne(int id) {
        Optional<Computer> computer = computerRepository.findById(id);
        return computer.orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Optional<Computer> findOne(Computer computer) {
        Optional<Model> modelOptional = Optional.empty();
        if(computer.getModel() != null)
             modelOptional = modelService.findOne(computer.getModel());

        return computerRepository.findOneByModelAndCategoryAndProcessorType(
                modelOptional.orElse(null),
                computer.getCategory(),
                computer.getProcessorType()
        );
    }

    @Override
    public void save(Computer computer) {
        Optional<Computer> computerFromDatabase = findOne(computer);

        if(computerFromDatabase.isEmpty()){
            modelService.save(computer.getModel());
            computerRepository.save(computer);
        }
    }

    public List<Computer> findAllByFilters(String name, String color, Long minPrice, Long maxPrice, String category, String processorType){
        Specification<Computer> spec = Specification.where(null);

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
        if(processorType != null && !processorType.isEmpty()){
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("processorType")), processorType.toLowerCase()));
        }
        return computerRepository.findAll(spec);
    }
}




















