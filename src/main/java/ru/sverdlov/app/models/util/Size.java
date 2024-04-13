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

//    @OneToMany(mappedBy = "size")
//    private List<Computer> computers;
//
//    @OneToMany(mappedBy = "size")
//    private List<Refrigerator> refrigerators;
//
//    @OneToMany(mappedBy = "size")
//    private List<Smartphone> smartphones;
//
//    @OneToMany(mappedBy = "size")
//    private List<Television> televisions;
//
//    @OneToMany(mappedBy = "size")
//    private List<VacuumCLeaner> vacuumCleaners;

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
