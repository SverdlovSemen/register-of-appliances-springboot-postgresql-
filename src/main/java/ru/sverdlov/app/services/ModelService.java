package ru.sverdlov.app.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sverdlov.app.dto.ComputerDTO;
import ru.sverdlov.app.dto.ModelDTO;
import ru.sverdlov.app.dto.RefrigeratorDTO;
import ru.sverdlov.app.models.Model;
import ru.sverdlov.app.models.Technic;
import ru.sverdlov.app.models.util.EntityNotCreatedException;
import ru.sverdlov.app.models.util.EntityNotFoundException;
import ru.sverdlov.app.models.util.Size;
import ru.sverdlov.app.repositories.ModelRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ModelService {
    private final ModelRepository modelRepository;
    private final TechnicService technicService;
    private final SizeService sizeService;

    @Autowired
    public ModelService(ModelRepository modelRepository, TechnicService technicService, SizeService sizeService) {
        this.modelRepository = modelRepository;
        this.technicService = technicService;
        this.sizeService = sizeService;
    }

    public List<Model> findAll(){
        return modelRepository.findAll();
    }

    public Model findOne(int id){
        Optional<Model> model = modelRepository.findById(id);
        return model.orElseThrow(EntityNotFoundException::new);
    }

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
}
