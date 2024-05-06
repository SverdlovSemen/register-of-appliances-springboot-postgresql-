package ru.sverdlov.app.models.util.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sverdlov.app.models.computer.Computer;
import ru.sverdlov.app.services.ComputerService;

@Component
public class ComputerValidator implements Validator {
    private final ComputerService computerService;

    @Autowired
    public ComputerValidator(ComputerService computerService) {
        this.computerService = computerService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Computer.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Computer computer = (Computer) target;
        if(computerService.findOne(computer).isPresent()) {
            errors.reject("computer.duplicate", "Компьютер с данными полями уже существует в базе данных");
        }
    }
}

















