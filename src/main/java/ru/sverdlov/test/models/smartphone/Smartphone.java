package ru.sverdlov.test.models.smartphone;

import ru.sverdlov.test.models.Model;

public class Smartphone extends Model {
    private Integer memory;
    private Integer numbersOfCameras;

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
