package ru.sverdlov.test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sverdlov.test.models.Model;
import ru.sverdlov.test.models.Technic;
import ru.sverdlov.test.models.util.Size;

import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {
    Optional<Model> findOneByNameAndColorAndSizeAndPriceAndIsAvailableAndTechnic(String name, String color, Size size, Long price, Boolean isAvailable,
                                                                  Technic technic);
}
