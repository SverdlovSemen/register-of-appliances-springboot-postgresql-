package ru.sverdlov.app.models.util.utilTechnic;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sverdlov.app.models.Technic;
import ru.sverdlov.app.services.TechnicService;

@Component
public class TechnicValidator implements Validator {
    private final TechnicService technicService;

    public TechnicValidator(TechnicService technicService) {
        this.technicService = technicService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Technic.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Technic technic = (Technic) target;

        if(technicService.findOne(technic).isPresent())
            errors.rejectValue("name", "", "Техника с данным именем уже существует");
    }
}
