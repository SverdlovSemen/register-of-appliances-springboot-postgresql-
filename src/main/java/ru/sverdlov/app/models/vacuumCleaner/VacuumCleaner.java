package ru.sverdlov.app.models.vacuumCleaner;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.sverdlov.app.models.Model;

@Entity
@Table(name = "vacuum_cleaner")
public class VacuumCleaner {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "У пылесоса должна быть модель")
    @Valid
    @ManyToOne
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    private Model model;

    @NotNull(message = "Объём пылесборника в пылесосе должен быть указан")
    @Positive(message = "Объём пылесборника в пылесосе должен быть больше 0")
    @Column(name = "volume_dust_collector")
    private Integer volumeOfDustCollector;

    @NotNull(message = "Количество режимов в пылесосе должно быть указано")
    @Positive(message = "Количество режимов в пылесосе должно быть больше 0")
    @Column(name = "number_modes")
    private Integer numberOfModes;

    public VacuumCleaner() {}

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Integer getVolumeOfDustCollector() {
        return volumeOfDustCollector;
    }

    public void setVolumeOfDustCollector(Integer volumeOfDustCollector) {
        this.volumeOfDustCollector = volumeOfDustCollector;
    }

    public Integer getNumberOfModes() {
        return numberOfModes;
    }

    public void setNumberOfModes(Integer numberOfModes) {
        this.numberOfModes = numberOfModes;
    }
}