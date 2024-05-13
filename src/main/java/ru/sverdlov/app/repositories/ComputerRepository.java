package ru.sverdlov.app.repositories;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sverdlov.app.models.Model;
import ru.sverdlov.app.models.computer.Category;
import ru.sverdlov.app.models.computer.Computer;
import ru.sverdlov.app.models.computer.ProcessorType;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, Integer> {
    Optional<Computer> findOneByModelAndCategoryAndProcessorType(Model model, Category category, ProcessorType processorType);

    List<Computer> findAll(Specification<Computer> specification);
}
