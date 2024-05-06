package ru.sverdlov.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sverdlov.app.dto.SizeDTO;
import ru.sverdlov.app.models.Technic;
import ru.sverdlov.app.models.computer.Computer;
import ru.sverdlov.app.models.util.Size;

import java.util.Optional;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {
    Optional<Size> findOneByLengthAndWidthAndHeight(int length, int width, int height);
}