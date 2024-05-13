package ru.sverdlov.app.repositories;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sverdlov.app.models.Model;
import ru.sverdlov.app.models.Technic;
import ru.sverdlov.app.models.util.Size;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {
    Optional<Model> findOneByTechnicAndNameAndColorAndSizeAndPriceAndAvailable(Technic technic, String name, String color, Size size,
                                                                               Long price, Boolean available);
    List<Model> findAll(Specification<Model> specification);

    List<Model> findAllByOrderByName();

    List<Model> findAllByOrderByPrice();
}
