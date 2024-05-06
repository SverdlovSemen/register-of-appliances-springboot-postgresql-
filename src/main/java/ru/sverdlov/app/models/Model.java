package ru.sverdlov.app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.sverdlov.app.models.vacuumCleaner.VacuumCleaner;
import ru.sverdlov.app.models.computer.Computer;
import ru.sverdlov.app.models.refrigerator.Refrigerator;
import ru.sverdlov.app.models.smartphone.Smartphone;
import ru.sverdlov.app.models.television.Television;
import ru.sverdlov.app.models.util.Size;

import java.util.List;

@Entity
@Table(name = "Model")
public class Model {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "technic_id", referencedColumnName = "id")
    private Technic technic;

    @NotBlank(message = "Наименование модели техники не должно быть пустым")
    @jakarta.validation.constraints.Size(min = 3, max = 100, message = "Наименование модели техники должно быть в пределах от 3 до 100 символов")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Серийный номер модели техники не должен быть пустым")
    @jakarta.validation.constraints.Size(min = 3, max = 100, message = "Серийный номер должен быть от 3 до 100 символов")
    @Column(name = "serial_number")
    private String serialNumber;

    @NotBlank(message = "Цвет модели техники не должен быть пустым")
    @jakarta.validation.constraints.Size(min = 3, max = 100, message = "Цвет должен быть от 3 до 50 символов")
    @Column(name = "color")
    private String color;

    @ManyToOne
    @JoinColumn(name = "size_id", referencedColumnName = "id")
    private Size size;

    @NotNull(message = "Цена модели техники должна присутствовать")
    @Positive(message = "Цена модели техники должна быть выше 0")
    @Column(name = "price")
    private Long price;

    @NotNull(message = "Наличие товара должно быть указано")
    @Column(name = "available")
    private Boolean available;

    @OneToMany(mappedBy = "model")
    private List<Computer> computers;

    @OneToMany(mappedBy = "model")
    private List<Refrigerator> refrigerators;

    @OneToMany(mappedBy = "model")
    private List<Smartphone> smartphones;

    @OneToMany(mappedBy = "model")
    private List<Television> televisions;

    @OneToMany(mappedBy = "model")
    private List<VacuumCleaner> vacuumCleaners;

    public Model() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Technic getTechnic() {
        return technic;
    }

    public void setTechnic(Technic technic) {
        this.technic = technic;
    }
}
