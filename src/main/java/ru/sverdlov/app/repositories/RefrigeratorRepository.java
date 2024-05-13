package ru.sverdlov.app.repositories;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sverdlov.app.models.Model;
import ru.sverdlov.app.models.computer.Computer;
import ru.sverdlov.app.models.refrigerator.CompressorType;
import ru.sverdlov.app.models.refrigerator.Refrigerator;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefrigeratorRepository extends JpaRepository<Refrigerator, Integer> {
    Optional<Refrigerator> findOneByModelAndNumberOfDoorsAndCompressorType(Model model, Integer numberOfDoors, CompressorType compressorType);
    List<Refrigerator> findAll(Specification<Refrigerator> specification);
}
