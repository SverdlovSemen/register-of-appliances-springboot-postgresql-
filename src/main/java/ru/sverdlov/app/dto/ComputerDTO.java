package ru.sverdlov.app.dto;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import ru.sverdlov.app.models.Model;
import ru.sverdlov.app.models.computer.Category;
import ru.sverdlov.app.models.computer.ProcessorType;

public class ComputerDTO {
    @NotNull(message = "У компьютера должна быть модель")
    @Valid
    private ModelDTO modelDTO;

    @NotNull(message = "Категория не должна быть пустой")
    @Enumerated(EnumType.STRING)
    private Category category;

    @NotNull(message = "Тип процессора не должен быть пустым")
    @Enumerated(EnumType.STRING)
    private ProcessorType processorType;

    public ModelDTO getModelDTO() {
        return modelDTO;
    }

    public void setModelDTO(ModelDTO modelDTO) {
        this.modelDTO = modelDTO;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ProcessorType getProcessorType() {
        return processorType;
    }

    public void setProcessorType(ProcessorType processorType) {
        this.processorType = processorType;
    }
}
