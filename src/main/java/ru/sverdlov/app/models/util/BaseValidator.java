package ru.sverdlov.app.models.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sverdlov.app.services.BaseService;
import ru.sverdlov.app.services.BaseServiceImpl;

@Component
public class BaseValidator<T> implements Validator {
    private final BaseServiceImpl baseServiceImpl;

    @Autowired
    public BaseValidator(BaseServiceImpl baseServiceImpl) {
        this.baseServiceImpl = baseServiceImpl;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
