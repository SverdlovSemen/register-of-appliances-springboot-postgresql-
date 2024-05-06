package ru.sverdlov.app.dto;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import ru.sverdlov.app.models.Model;
import ru.sverdlov.app.models.television.Category;
import ru.sverdlov.app.models.television.Technology;

public class TelevisionDTO {
    @NotNull(message = "У телевизора должна быть модель")
    @Valid
    private ModelDTO modelDTO;

    @NotNull(message = "У телевизора должна быть категория")
    @Enumerated(EnumType.STRING)
    private Category category;

    @NotNull(message = "У телевизора должна быть технология")
    @Enumerated(EnumType.STRING)
    private Technology technology;

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

    public Technology getTechnology() {
        return technology;
    }

    public void setTechnology(Technology technology) {
        this.technology = technology;
    }
}
