package ru.sverdlov.app.models.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sverdlov.app.models.Model;
import ru.sverdlov.app.models.Technic;
import ru.sverdlov.app.services.ModelService;
import ru.sverdlov.app.services.TechnicService;

@Component
public class EntityValidator implements Validator {
    private final ModelService modelService;
    private final TechnicService technicService;

    @Autowired
    public EntityValidator(ModelService modelService, TechnicService technicService) {
        this.modelService = modelService;
        this.technicService = technicService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Model.class.isAssignableFrom(clazz) || Technic.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(target instanceof Model){
            Model model = (Model) target;
            if(modelService.findOne(model).isPresent()){
                errors.reject("model.duplicate", "Модель с данными полями уже существует в базе данных");
            }
        } else if(target instanceof Technic){
            Technic technic = (Technic) target;
            if(technicService.findOne(technic).isPresent()){
                errors.reject("technic.duplicate", "Техника с данными полями уже существует в базе данных");
            }
        } else{
            errors.reject("unsupported.type", "Неподдерживаемый тип объекта для валидации");
        }
    }
}




















