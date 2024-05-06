package ru.sverdlov.app.models.util.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.sverdlov.app.models.vacuumCleaner.VacuumCleaner;
import ru.sverdlov.app.services.VacuumCleanerService;

@Component
public class VacuumCleanerValidator implements Validator {
    private final VacuumCleanerService vacuumCleanerService;

    @Autowired
    public VacuumCleanerValidator(VacuumCleanerService vacuumCleanerService) {
        this.vacuumCleanerService = vacuumCleanerService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return VacuumCleaner.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        VacuumCleaner vacuumCleaner = (VacuumCleaner) target;
        if(vacuumCleanerService.findOne(vacuumCleaner).isPresent()){
            errors.reject("vacuumCleaner.duplicate", "Пылесос с данными полями уже существует в базе данных");
        }
    }
}























