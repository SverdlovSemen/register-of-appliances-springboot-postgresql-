package ru.sverdlov.app.dto;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.sverdlov.app.models.refrigerator.CompressorType;

public class RefrigeratorDTO {
    @NotNull(message = "У холодильника должна быть модель")
    @Valid
    private ModelDTO modelDTO;

    @NotNull(message = "Количество дверей в холодильнике должно быть указано")
    @Positive(message = "Количество дверей в холодильнике должно быть больше 0")
    private Integer numberOfDoors;

    @NotNull(message = "Тип компрессора в холодильнике должен быть указан")
    @Enumerated(EnumType.STRING)
    private CompressorType compressorType;

    public ModelDTO getModelDTO() {
        return modelDTO;
    }

    public void setModelDTO(ModelDTO modelDTO) {
        this.modelDTO = modelDTO;
    }

    public Integer getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(Integer numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    public CompressorType getCompressorType() {
        return compressorType;
    }

    public void setCompressorType(CompressorType compressorType) {
        this.compressorType = compressorType;
    }
}
