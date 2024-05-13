package ru.sverdlov.app.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.sverdlov.app.models.Model;

public class VacuumCleanerDTO {
    @NotNull(message = "У пылесоса должна быть модель")
    @Valid
    private ModelDTO modelDTO;

    @NotNull(message = "Объём пылесборника в пылесосе должен быть указан")
    @Positive(message = "Объём пылесборника в пылесосе должен быть больше 0")
    private Integer volumeOfDustCollector;

    @NotNull(message = "Количество режимов в пылесосе должно быть указано")
    @Positive(message = "Количество режимов в пылесосе должно быть больше 0")
    private Integer numberOfModes;

    public ModelDTO getModelDTO() {
        return modelDTO;
    }

    public void setModelDTO(ModelDTO modelDTO) {
        this.modelDTO = modelDTO;
    }

    public Integer getVolumeOfDustCollector() {
        return volumeOfDustCollector;
    }

    public void setVolumeOfDustCollector(Integer volumeOfDustCollector) {
        this.volumeOfDustCollector = volumeOfDustCollector;
    }

    public Integer getNumberOfModes() {
        return numberOfModes;
    }

    public void setNumberOfModes(Integer numberOfModes) {
        this.numberOfModes = numberOfModes;
    }
}
