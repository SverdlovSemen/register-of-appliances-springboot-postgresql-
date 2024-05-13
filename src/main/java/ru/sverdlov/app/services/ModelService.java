package ru.sverdlov.app.services;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sverdlov.app.dto.ModelDTO;
import ru.sverdlov.app.dto.TechnicDTO;
import ru.sverdlov.app.models.Model;
import ru.sverdlov.app.models.Technic;
import ru.sverdlov.app.models.util.error.EntityNotFoundException;
import ru.sverdlov.app.models.util.Size;
import ru.sverdlov.app.repositories.ModelRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ModelService implements IService<Model> {
    private final ModelRepository modelRepository;
    private final TechnicService technicService;
    private final SizeService sizeService;

    @Autowired
    public ModelService(ModelRepository modelRepository, TechnicService technicService, SizeService sizeService) {
        this.modelRepository = modelRepository;
        this.technicService = technicService;
        this.sizeService = sizeService;
    }

    @Override
    public List<Model> findAll(){
        return modelRepository.findAll();
    }

    @Override
    public Model findOne(int id){
        Optional<Model> model = modelRepository.findById(id);
        return model.orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Optional<Model> findOne(Model model){
        Optional<Size> sizeOptional = Optional.empty();
        Optional<Technic> technicOptional = Optional.empty();
        if(model.getSize() != null) {
            sizeOptional = sizeService.findOne(model.getSize());
        }
        if(model.getTechnic() != null) {
            technicOptional = technicService.findOne(model.getTechnic());
        }

        return modelRepository.findOneByTechnicAndNameAndColorAndSizeAndPriceAndAvailable(
                technicOptional.orElse(null),
                model.getName(),
                model.getColor(),
                sizeOptional.orElse(null),
                model.getPrice(),
                model.getAvailable()
        );
    }

    @Override
    public void save(Model model){
        Optional<Model> modelFromDatabase = findOne(model);
        if(modelFromDatabase.isPresent()) {
            model.setId(modelFromDatabase.get().getId());
            model.setSerialNumber(modelFromDatabase.get().getSerialNumber());
        } else {
            sizeService.save(model.getSize());
            technicService.save(model.getTechnic());

            String uniqueSerialNumber = UUID.randomUUID().toString();
            model.setSerialNumber(uniqueSerialNumber);

            modelRepository.save(model);
        }
    }

    public List<Model> findAllByFilters(String name, String technicName, String color, Long minPrice, Long maxPrice){
        Specification<Model> spec = Specification.where(null);

        if(name != null && !name.isEmpty()){
            spec = spec.and(((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%")));
        }
        if (technicName != null && !technicName.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                Join<Model, Technic> join = root.join("technic", JoinType.INNER); // Join таблицы Model с таблицей Technic
                return criteriaBuilder.like(criteriaBuilder.lower(join.get("name")), "%" + technicName.toLowerCase() + "%");
            });
        }
        if (color != null && !color.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("color")), color.toLowerCase()));
        }
        if (minPrice != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
        }

        if (maxPrice != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
        }

        return modelRepository.findAll(spec);
    }

    public List<Model> findAllSortedByName(){
        return modelRepository.findAllByOrderByName();
    }

    public List<Model> findAllSortedByPrice(){
        return modelRepository.findAllByOrderByPrice();
    }
}






















