package ru.sverdlov.app.models.VacuumCleaner;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.sverdlov.app.models.Model;

@Entity
@Table(name = "VacuumCleaner")
public class VacuumCLeaner {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    private Model model;

    @NotNull(message = "Объём пылесборника в пылесосе должен быть указан")
    @Positive(message = "Объём пылесборника в пылесосе должен быть больше 0")
    @Column(name = "volume_dust_collector")
    private Double volumeOfDustCollector;

    @NotNull(message = "Количество режимов в пылесосе должно быть указано")
    @Positive(message = "Количество режимов в пылесосе должно быть больше 0")
    @Column(name = "number_modes")
    private Integer numberOfModes;

    public VacuumCLeaner() {}

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
