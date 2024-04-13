package ru.sverdlov.app.models.util.utilModel;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sverdlov.app.models.Model;
import ru.sverdlov.app.services.ModelService;

@Component
public class ModelValidator implements Validator {
    private final ModelService modelService;

    @Autowired
    public ModelValidator(ModelService modelService) {
        this.modelService = modelService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Model.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Model model = (Model) target;

        if(modelService.findOne(model).isPresent())
            errors.reject("model.duplicate", "Модель с данными полями уже существует в базе данных");
    }
}
