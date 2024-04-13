package ru.sverdlov.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sverdlov.app.models.Model;
import ru.sverdlov.app.models.Technic;
import ru.sverdlov.app.models.util.Size;

import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {
    Optional<Model> findOneByNameAndColorAndSizeAndPriceAndIsAvailableAndTechnic(String name, String color, Size size, Long price, Boolean isAvailable,
                                                                  Technic technic);
}
