package ru.sverdlov.test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sverdlov.test.models.Technic;

import java.util.Optional;

@Repository
public interface TechnicRepository extends JpaRepository<Technic, Integer> {
    Optional<Technic> findByName(String name);
}
