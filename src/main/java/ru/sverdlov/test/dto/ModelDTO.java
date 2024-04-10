package ru.sverdlov.test.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.sverdlov.test.models.Technic;
import ru.sverdlov.test.models.util.Size;

public class ModelDTO {
    @NotEmpty(message = "Наименование модели техники не должно быть пустым")
    @jakarta.validation.constraints.Size(min = 3, max = 100, message = "Наименование модели техники должно быть в пределах от 3 до 100 символов")
    private String name;

    @NotEmpty(message = "Цвет модели техники не должен быть пустым")
    @jakarta.validation.constraints.Size(min = 3, max = 100, message = "Цвет должен быть от 3 до 50 символов")
    private String color;

    private Size size;

    @NotNull(message = "Цена модели техники должна присутствовать")
    @Positive(message = "Цена модели техники должна быть выше 0")
    private Long price;

    @NotNull(message = "Наличие товара должно быть указано")
    private Boolean isAvailable;

    private Technic technic;
}
