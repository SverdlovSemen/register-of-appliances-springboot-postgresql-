package ru.sverdlov.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sverdlov.app.models.Technic;

import java.util.Optional;

@Repository
public interface TechnicRepository extends JpaRepository<Technic, Integer> {
    Optional<Technic> findByNameAndCountryOriginAndManufacturerAndPossibleOrderOnlineAndPossibleMakeInstallments(String name,
                      String countryOrigin, String manufacturer, Boolean possibleOrderOnline, Boolean possibleMakeInstallments);
}
