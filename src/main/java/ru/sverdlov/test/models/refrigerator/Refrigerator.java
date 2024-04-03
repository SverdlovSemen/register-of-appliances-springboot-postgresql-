package ru.sverdlov.test.models.refrigerator;

import ru.sverdlov.test.models.Model;

public class Refrigerator extends Model {
    private Integer numberOfDoors;
    private CompressorType compressorType;

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
