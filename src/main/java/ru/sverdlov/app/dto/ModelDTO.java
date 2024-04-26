package ru.sverdlov.app.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.sverdlov.app.models.Technic;
import ru.sverdlov.app.models.util.Size;

public class ModelDTO {
    @Valid
    private TechnicDTO technicDTO;

    @NotBlank(message = "Наименование модели техники не должно быть пустым")
    @jakarta.validation.constraints.Size(min = 3, max = 100, message = "Наименование модели техники должно быть в пределах от 3 до 100 символов")
    private String name;

    @NotBlank(message = "Цвет модели техники не должен быть пустым")
    @jakarta.validation.constraints.Size(min = 3, max = 100, message = "Цвет должен быть от 3 до 50 символов")
    private String color;

    @Valid
    private SizeDTO sizeDTO;

    @NotNull(message = "Цена модели техники должна присутствовать")
    @Positive(message = "Цена модели техники должна быть выше 0")
    private Long price;

    @NotNull(message = "Наличие товара должно быть указано")
    private Boolean available;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public SizeDTO getSizeDTO() {
        return sizeDTO;
    }

    public void setSizeDTO(SizeDTO sizeDTO) {
        this.sizeDTO = sizeDTO;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public TechnicDTO getTechnicDTO() {
        return technicDTO;
    }

    public void setTechnicDTO(TechnicDTO technicDTO) {
        this.technicDTO = technicDTO;
    }
}
