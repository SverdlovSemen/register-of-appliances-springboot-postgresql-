package ru.sverdlov.app.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SizeDTO {
    @NotNull(message = "Длина модели техники должна присутствовать")
    @Positive(message = "Длина модели техники должна быть больше 0")
    private int length;

    @NotNull(message = "Ширина модели техники должна присутствовать")
    @Positive(message = "Ширина модели техники должна быть больше 0")
    private int width;

    @NotNull(message = "Высота модели техники должна присутствовать")
    @Positive(message = "Высота модели техники должна быть больше 0")
    private int height;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
