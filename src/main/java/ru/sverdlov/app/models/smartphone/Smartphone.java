package ru.sverdlov.app.models.smartphone;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.sverdlov.app.models.Model;

@Entity
@Table(name = "Smartphone")
public class Smartphone extends Model {

    @NotNull(message = "Память в телефоне должна быть указана")
    @Positive(message = "Память в телефоне должна быть больше 0")
    @Column(name = "memory")
    private Integer memory;

    @NotNull(message = "Количество камер в телефоне должно быть указано")
    @Positive(message = "Количество камер в телефоне должно быть больше 0")
    @Column(name = "number_cameras")
    private Integer numbersOfCameras;

    public Smartphone(){}

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
}
