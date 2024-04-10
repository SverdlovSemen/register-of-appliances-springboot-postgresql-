package ru.sverdlov.test.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.sverdlov.test.models.VacuumCleaner.VacuumCLeaner;
import ru.sverdlov.test.models.computer.Computer;
import ru.sverdlov.test.models.refrigerator.Refrigerator;
import ru.sverdlov.test.models.smartphone.Smartphone;
import ru.sverdlov.test.models.television.Television;
import ru.sverdlov.test.models.util.Size;

import java.util.List;

@Entity
@Table(name = "Model")
public class Model {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "technic_id", referencedColumnName = "id")
    private Technic technic;

    @NotEmpty(message = "Наименование модели техники не должно быть пустым")
    @jakarta.validation.constraints.Size(min = 3, max = 100, message = "Наименование модели техники должно быть в пределах от 3 до 100 символов")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Серийный номер модели техники не должен быть пустым")
    @jakarta.validation.constraints.Size(min = 3, max = 100, message = "Серийный номер должен быть от 3 до 100 символов")
    @Column(name = "serial_number")
    private String serialNumber;

    @NotEmpty(message = "Цвет модели техники не должен быть пустым")
    @jakarta.validation.constraints.Size(min = 3, max = 100, message = "Цвет должен быть от 3 до 50 символов")
    @Column(name = "color")
    private String color;

    @OneToOne
    @JoinColumn(name = "size_id", referencedColumnName = "id")
    private Size size;

    @NotNull(message = "Цена модели техники должна присутствовать")
    @Positive(message = "Цена модели техники должна быть выше 0")
    @Column(name = "price")
    private Long price;

    @NotNull(message = "Наличие товара должно быть указано")
    @Column(name = "is_available")
    private Boolean isAvailable;


    @OneToMany(mappedBy = "model")
    private List<Computer> computers;

    @OneToMany(mappedBy = "model")
    private List<Refrigerator> refrigerators;

    @OneToMany(mappedBy = "model")
    private List<Smartphone> smartphones;

    @OneToMany(mappedBy = "model")
    private List<Television> televisions;

    @OneToMany(mappedBy = "model")
    private List<VacuumCLeaner> vacuumCleaners;

    public Model() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public Technic getTechnic() {
        return technic;
    }

    public void setTechnic(Technic technic) {
        this.technic = technic;
    }
}