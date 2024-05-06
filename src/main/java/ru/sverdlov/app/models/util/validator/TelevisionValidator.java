package ru.sverdlov.app.models.util.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sverdlov.app.models.television.Television;
import ru.sverdlov.app.services.TelevisionService;

@Component
public class TelevisionValidator implements Validator {
    private final TelevisionService televisionService;

    @Autowired
    public TelevisionValidator(TelevisionService televisionService) {
        this.televisionService = televisionService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Television.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Television television = (Television) target;
        if(televisionService.findOne(television).isPresent()){
            errors.reject("television.duplicate", "Телевизор с данными полями уже существует в базе данных");
        }
    }
}












