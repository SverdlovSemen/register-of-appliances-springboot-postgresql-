package ru.sverdlov.app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 3, max = 100, message = "Название техники должно быть в пределах от 3 до 100 символов")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Страна производитель не должна быть пустой")
    @Size(min = 3, max = 100, message = "Страна производитель должна быть от 3 до 100 символов")
    @Column(name="country_origin")
    private String countryOfOrigin;

    @NotEmpty(message = "Фирма производитель не должна быть пустой")
    @Size(min = 3, max = 100, message = "Страна производитель должна быть от 3 до 100 символов")
    @Column(name="manufacturer")
    private String manufacturer;

    @NotNull(message = "Возможность заказа онлайн должна быть указана")
    @Column(name = "is_possible_order_online")
    private Boolean isPossibleOrderOnline;

    @NotNull(message = "Фирма производитель должна быть указана")
    @Column(name = "is_possible_make_installments")
    private Boolean isPossibleMakeInstallments;

    @OneToMany(mappedBy = "technic")
    private List<Computer> computers;

    @OneToMany(mappedBy = "technic")
    private List<Refrigerator> refrigerators;

    @OneToMany(mappedBy = "technic")
    private List<Smartphone> smartphones;

    @OneToMany(mappedBy = "technic")
    private List<Television> televisions;

    @OneToMany(mappedBy = "technic")
    private List<VacuumCLeaner> vacuumCleaners;

    public Technic(){}

    public Technic(int id, String name, String countryOfOrigin, String manufacturer) {
        this.id = id;
        this.name = name;
        this.countryOfOrigin = countryOfOrigin;
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

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Boolean getPossibleOrderOnline() {
        return isPossibleOrderOnline;
    }

    public void setPossibleOrderOnline(Boolean possibleOrderOnline) {
        isPossibleOrderOnline = possibleOrderOnline;
    }

    public Boolean getPossibleMakeInstallments() {
        return isPossibleMakeInstallments;
    }

    public void setPossibleMakeInstallments(Boolean possibleMakeInstallments) {
        isPossibleMakeInstallments = possibleMakeInstallments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Technic technic = (Technic) o;

        if (id != technic.id) return false;
        if (!name.equals(technic.name)) return false;
        if (!countryOfOrigin.equals(technic.countryOfOrigin)) return false;
        if (!manufacturer.equals(technic.manufacturer)) return false;
        if (!isPossibleOrderOnline.equals(technic.isPossibleOrderOnline)) return false;
        if (!isPossibleMakeInstallments.equals(technic.isPossibleMakeInstallments)) return false;
        if (!Objects.equals(computers, technic.computers)) return false;
        if (!Objects.equals(refrigerators, technic.refrigerators))
            return false;
        if (!Objects.equals(smartphones, technic.smartphones)) return false;
        if (!Objects.equals(televisions, technic.televisions)) return false;
        return Objects.equals(vacuumCleaners, technic.vacuumCleaners);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + countryOfOrigin.hashCode();
        result = 31 * result + manufacturer.hashCode();
        result = 31 * result + isPossibleOrderOnline.hashCode();
        result = 31 * result + isPossibleMakeInstallments.hashCode();
        result = 31 * result + (computers != null ? computers.hashCode() : 0);
        result = 31 * result + (refrigerators != null ? refrigerators.hashCode() : 0);
        result = 31 * result + (smartphones != null ? smartphones.hashCode() : 0);
        result = 31 * result + (televisions != null ? televisions.hashCode() : 0);
        result = 31 * result + (vacuumCleaners != null ? vacuumCleaners.hashCode() : 0);
        return result;
    }
}
