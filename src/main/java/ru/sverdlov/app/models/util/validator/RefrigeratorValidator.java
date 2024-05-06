package ru.sverdlov.app.models.util.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sverdlov.app.models.Model;
import ru.sverdlov.app.models.refrigerator.Refrigerator;
import ru.sverdlov.app.services.ModelService;
import ru.sverdlov.app.services.RefrigeratorService;

@Component
public class RefrigeratorValidator implements Validator {
    private final RefrigeratorService refrigeratorService;

    @Autowired
    public RefrigeratorValidator(RefrigeratorService refrigeratorService) {
        this.refrigeratorService = refrigeratorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Validator.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Refrigerator refrigerator = (Refrigerator) target;
        if(refrigeratorService.findOne(refrigerator).isPresent()){
            errors.reject("model.duplicate","Модель с данными полями уже существует в базе данных");
        }
    }
}
