package ru.sverdlov.app.models.util.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sverdlov.app.models.smartphone.Smartphone;
import ru.sverdlov.app.services.SmartphoneService;

@Component
public class SmartphoneValidator implements Validator {
    private final SmartphoneService smartphoneService;

    @Autowired
    public SmartphoneValidator(SmartphoneService smartphoneService) {
        this.smartphoneService = smartphoneService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Smartphone.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Smartphone smartphone = (Smartphone) target;
        if(smartphoneService.findOne(smartphone).isPresent()) {
            errors.reject("smartphone.duplicate", "Смартфон с данными полями уже существует в базе данных");
        }
    }
}
