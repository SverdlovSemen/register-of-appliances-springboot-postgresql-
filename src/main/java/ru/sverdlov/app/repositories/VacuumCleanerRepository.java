package ru.sverdlov.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sverdlov.app.models.Model;
import ru.sverdlov.app.models.vacuumCleaner.VacuumCleaner;

import java.util.Optional;

@Repository
public interface VacuumCleanerRepository extends JpaRepository<VacuumCleaner, Integer> {
    Optional<VacuumCleaner> findOneByModelAndVolumeOfDustCollectorAndNumberOfModes(Model model, Integer volumeOfDustCollector,
                                                                                   Integer numberOfModes);
}
