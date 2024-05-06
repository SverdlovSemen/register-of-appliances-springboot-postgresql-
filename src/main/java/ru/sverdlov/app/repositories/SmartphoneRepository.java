package ru.sverdlov.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sverdlov.app.models.Model;
import ru.sverdlov.app.models.smartphone.Smartphone;

import java.util.Optional;

@Repository
public interface SmartphoneRepository extends JpaRepository<Smartphone, Integer> {
    Optional<Smartphone> findOneByModelAndMemoryAndNumberOfCameras(Model model, Integer memory, Integer numberOfCameras);
}
