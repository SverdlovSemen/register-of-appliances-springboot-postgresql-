package ru.sverdlov.app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.sverdlov.app.models.VacuumCleaner.VacuumCLeaner;
import ru.sverdlov.app.models.computer.Computer;
import ru.sverdlov.app.models.refrigerator.Refrigerator;
import ru.sverdlov.app.models.smartphone.Smartphone;
import ru.sverdlov.app.models.television.Television;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Technic")
public class Technic {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Имя не должно быть пустым")
    @Size(min = 3, max = 100, message = "Название техники должно быть в пределах от 3 до 100 символов")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Страна производитель не должна быть пустой")
    @Size(min = 3, max = 100, message = "Страна производитель должна быть от 3 до 100 символов")
    @Column(name="country_origin")
    private String countryOrigin;

    @NotBlank(message = "Фирма производитель не должна быть пустой")
    @Size(min = 3, max = 100, message = "Страна производитель должна быть от 3 до 100 символов")
    @Column(name="manufacturer")
    private String manufacturer;

    @NotNull(message = "Возможность заказа онлайн должна быть указана")
    @Column(name = "possible_order_online")
    private Boolean possibleOrderOnline;

    @NotNull(message = "Фирма производитель должна быть указана")
    @Column(name = "possible_make_installments")
    private Boolean possibleMakeInstallments;

    @OneToMany(mappedBy = "technic")
    private List<Model> models;

    public Technic(){}

    public Technic(int id, String name, String countryOrigin, String manufacturer) {
        this.id = id;
        this.name = name;
        this.countryOrigin = countryOrigin;
        this.manufacturer = manufacturer;
    }

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

    public String getCountryOrigin() {
        return countryOrigin;
    }

    public void setCountryOrigin(String countryOrigin) {
        this.countryOrigin = countryOrigin;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Boolean getPossibleOrderOnline() {
        return possibleOrderOnline;
    }

    public void setPossibleOrderOnline(Boolean possibleOrderOnline) {
        this.possibleOrderOnline = possibleOrderOnline;
    }

    public Boolean getPossibleMakeInstallments() {
        return possibleMakeInstallments;
    }

    public void setPossibleMakeInstallments(Boolean possibleMakeInstallments) {
        this.possibleMakeInstallments = possibleMakeInstallments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Technic technic = (Technic) o;

        if (id != technic.id) return false;
        if (!name.equals(technic.name)) return false;
        if (!countryOrigin.equals(technic.countryOrigin)) return false;
        if (!manufacturer.equals(technic.manufacturer)) return false;
        if (!possibleOrderOnline.equals(technic.possibleOrderOnline)) return false;
        return possibleMakeInstallments.equals(technic.possibleMakeInstallments);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + countryOrigin.hashCode();
        result = 31 * result + manufacturer.hashCode();
        result = 31 * result + possibleOrderOnline.hashCode();
        result = 31 * result + possibleMakeInstallments.hashCode();
        return result;
    }
}
