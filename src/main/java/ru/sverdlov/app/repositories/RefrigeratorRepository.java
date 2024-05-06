package ru.sverdlov.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sverdlov.app.models.Model;
import ru.sverdlov.app.models.refrigerator.CompressorType;
import ru.sverdlov.app.models.refrigerator.Refrigerator;

import java.util.Optional;

@Repository
public interface RefrigeratorRepository extends JpaRepository<Refrigerator, Integer> {
    Optional<Refrigerator> findOneByModelAndNumberOfDoorsAndCompressorType(Model model, Integer numberOfDoors, CompressorType compressorType);
}
