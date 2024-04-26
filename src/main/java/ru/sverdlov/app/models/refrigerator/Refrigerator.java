package ru.sverdlov.app.models.refrigerator;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import ru.sverdlov.app.models.Model;

@Entity
@Table(name = "Refrigerator")
public class Refrigerator {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    @NotNull(message = "У холодильника должна быть модель")
    private Model model;

    @NotNull(message = "Количество дверей в холодильнике должно быть указано")
    @Positive(message = "Количество дверей в холодильнике должно быть больше 0")
    @Column(name = "number_doors")
    private Integer numberOfDoors;

    @NotEmpty(message = "Тип компрессора в холодильнике должен быть указан")
    @Size(min = 2, max = 50, message = "Тип компрессора в холодильнике должен быть в пределах от 2 до 50 символов")
    @Column(name = "compressor_type")
    private CompressorType compressorType;

    public Refrigerator() {}

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
