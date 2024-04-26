package ru.sverdlov.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TechnicDTO {
    @NotBlank(message = "Имя не должно быть пустым")
    @Size(min = 3, max = 100, message = "Название техники должно быть в пределах от 3 до 100 символов")
    private String name;

    @NotBlank(message = "Страна производитель не должна быть пустой")
    @Size(min = 3, max = 100, message = "Страна производитель должна быть от 3 до 100 символов")
    private String countryOrigin;

    @NotBlank(message = "Фирма производитель не должна быть пустой")
    @Size(min = 3, max = 100, message = "Страна производитель должна быть от 3 до 100 символов")
    private String manufacturer;

    @NotNull(message = "Возможность заказа онлайн должна быть указана")
    private Boolean possibleOrderOnline;

    @NotNull(message = "Возможность оформления рассрочки должна быть указана")
    private Boolean possibleMakeInstallments;

    public TechnicDTO() {}

    public TechnicDTO(String name, String countryOrigin, String manufacturer, Boolean possibleOrderOnline,
                      Boolean possibleMakeInstallments) {
        this.name = name;
        this.countryOrigin = countryOrigin;
        this.manufacturer = manufacturer;
        this.possibleOrderOnline = possibleOrderOnline;
        this.possibleMakeInstallments = possibleMakeInstallments;
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

    //@JsonSetter("isPossibleOrderOnline")
    public void setPossibleOrderOnline(Boolean possibleOrderOnline) {
        this.possibleOrderOnline = possibleOrderOnline;
    }

    public Boolean getPossibleMakeInstallments() {
        return possibleMakeInstallments;
    }

    //@JsonSetter("isPossibleMakeInstallments")
    public void setPossibleMakeInstallments(Boolean possibleMakeInstallments) {
        this.possibleMakeInstallments = possibleMakeInstallments;
    }
}
