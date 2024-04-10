package ru.sverdlov.test.models.util;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.sverdlov.test.models.Model;

@Entity
@Table(name = "Size")
public class Size {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(mappedBy = "size")
    private Model model;

    @NotNull(message = "Длина модели техники должна присутствовать")
    @Positive(message = "Длина модели техники должна быть больше 0")
    @Column(name = "length")
    private Double length;

    @NotNull(message = "Ширина модели техники должна присутствовать")
    @Positive(message = "Ширина модели техники должна быть больше 0")
    @Column(name = "width")
    private Double width;

    @NotNull(message = "Высота модели техники должна присутствовать")
    @Positive(message = "Высота модели техники должна быть больше 0")
    @Column(name = "height")
    private Double height;

    public Size() {}

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }
}
