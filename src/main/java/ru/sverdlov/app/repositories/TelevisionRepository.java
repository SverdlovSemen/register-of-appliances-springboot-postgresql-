package ru.sverdlov.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sverdlov.app.models.Model;
import ru.sverdlov.app.models.television.Category;
import ru.sverdlov.app.models.television.Technology;
import ru.sverdlov.app.models.television.Television;

import java.util.Optional;

@Repository
public interface TelevisionRepository extends JpaRepository<Television, Integer> {
    Optional<Television> findOneByModelAndCategoryAndTechnology(Model model, Category category, Technology technology);
}
