package ru.sverdlov.app.models.util;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.sverdlov.app.models.Model;

import java.util.List;

@Entity
@Table(name = "Size")
public class Size {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "size")
    private List<Model> models;

    @NotNull(message = "Длина модели техники должна присутствовать")
    @Positive(message = "Длина модели техники должна быть больше 0")
    @Column(name = "length")
    private int length;

    @NotNull(message = "Ширина модели техники должна присутствовать")
    @Positive(message = "Ширина модели техники должна быть больше 0")
    @Column(name = "width")
    private int width;

    @NotNull(message = "Высота модели техники должна присутствовать")
    @Positive(message = "Высота модели техники должна быть больше 0")
    @Column(name = "height")
    private int height;

    public Size() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
