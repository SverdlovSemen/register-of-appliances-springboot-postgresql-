package ru.sverdlov.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sverdlov.app.models.Model;
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
}
