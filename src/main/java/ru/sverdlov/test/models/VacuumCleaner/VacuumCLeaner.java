package ru.sverdlov.test.models.VacuumCleaner;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.sverdlov.test.models.Model;

@Entity
@Table(name = "VacuumCleaner")
public class VacuumCLeaner {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "У пылесоса должна быть модель")
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
