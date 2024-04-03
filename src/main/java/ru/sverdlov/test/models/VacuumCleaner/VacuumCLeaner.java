package ru.sverdlov.test.models.VacuumCleaner;

import ru.sverdlov.test.models.Model;

public class VacuumCLeaner extends Model {
    private Double volumeOfDustCollector;
    private Integer numberOfModes;

    public Double getVolumeOfDustCollector() {
        return volumeOfDustCollector;
    }

    public void setVolumeOfDustCollector(Double volumeOfDustCollector) {
        this.volumeOfDustCollector = volumeOfDustCollector;
    }

    public Integer getNumberOfModes() {
        return numberOfModes;
    }

    public void setNumberOfModes(Integer numberOfModes) {
        this.numberOfModes = numberOfModes;
    }
}
