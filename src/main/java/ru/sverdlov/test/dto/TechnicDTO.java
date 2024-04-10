package ru.sverdlov.test.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.sverdlov.test.models.Model;

import java.util.List;

public class TechnicDTO {
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 3, max = 100, message = "Название техники должно быть в пределах от 3 до 100 символов")
    private String name;

    @NotEmpty(message = "Страна производитель не должна быть пустой")
    @Size(min = 3, max = 100, message = "Страна производитель должна быть от 3 до 100 символов")
    private String countryOfOrigin;

    @NotEmpty(message = "Фирма производитель не должна быть пустой")
    @Size(min = 3, max = 100, message = "Страна производитель должна быть от 3 до 100 символов")
    private String manufacturer;

    @NotNull(message = "Возможность заказа онлайн должна быть указана")
    private Boolean isPossibleOrderOnline;

    @NotNull(message = "Возможность оформления рассрочки должна быть указана")
    private Boolean isPossibleMakeInstallments;

    public TechnicDTO() {}

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

    @JsonSetter("isPossibleOrderOnline")
    public void setPossibleOrderOnline(Boolean possibleOrderOnline) {
        isPossibleOrderOnline = possibleOrderOnline;
    }

    public Boolean getPossibleMakeInstallments() {
        return isPossibleMakeInstallments;
    }

    @JsonSetter("isPossibleMakeInstallments")
    public void setPossibleMakeInstallments(Boolean possibleMakeInstallments) {
        isPossibleMakeInstallments = possibleMakeInstallments;
    }
}
