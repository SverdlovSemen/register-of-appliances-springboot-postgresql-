package ru.sverdlov.test.models.smartphone;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.sverdlov.test.models.Model;

@Entity
@Table(name = "Smartphone")
public class Smartphone {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "У телефона должна быть модель")
    @ManyToOne
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    private Model model;

    @NotNull(message = "Память в телефоне должна быть указана")
    @Positive(message = "Память в телефоне должна быть больше 0")
    @Column(name = "memory")
    private Integer memory;

    @NotNull(message = "Количество камер в телефоне должно быть указано")
    @Positive(message = "Количество камер в телефоне должно быть больше 0")
    @Column(name = "number_cameras")
    private Integer numbersOfCameras;

    public Smartphone(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    public Integer getNumbersOfCameras() {
        return numbersOfCameras;
    }

    public void setNumbersOfCameras(Integer numbersOfCameras) {
        this.numbersOfCameras = numbersOfCameras;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
