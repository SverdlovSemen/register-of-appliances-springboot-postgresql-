package ru.sverdlov.app.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SmartphoneDTO {
    @NotNull(message = "У смартфона должна быть модель")
    @Valid
    private ModelDTO modelDTO;

    @NotNull(message = "Память в телефоне должна быть указана")
    @Positive(message = "Память в телефоне должна быть больше 0")
    private Integer memory;

    @NotNull(message = "Количество камер в телефоне должно быть указано")
    @Positive(message = "Количество камер в телефоне должно быть больше 0")
    private Integer numberOfCameras;

    public ModelDTO getModelDTO() {
        return modelDTO;
    }

    public void setModelDTO(ModelDTO modelDTO) {
        this.modelDTO = modelDTO;
    }

    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    public Integer getNumberOfCameras() {
        return numberOfCameras;
    }

    public void setNumberOfCameras(Integer numberOfCameras) {
        this.numberOfCameras = numberOfCameras;
    }
}
