//package ru.sverdlov.app.models.util.validator;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//import org.springframework.validation.Validator;
//import ru.sverdlov.app.models.Model;
//import ru.sverdlov.app.models.Technic;
//import ru.sverdlov.app.models.VacuumCleaner.VacuumCLeaner;
//import ru.sverdlov.app.models.computer.Computer;
//import ru.sverdlov.app.models.refrigerator.Refrigerator;
//import ru.sverdlov.app.models.smartphone.Smartphone;
//import ru.sverdlov.app.models.television.Television;
//import ru.sverdlov.app.services.ComputerService;
//import ru.sverdlov.app.services.ModelService;
//import ru.sverdlov.app.services.SizeService;
//import ru.sverdlov.app.services.TechnicService;
//
//@Component
//public class EntityValidator implements Validator {
//    private final ModelService modelService;
//    private final TechnicService technicService;
//    private final SizeService sizeService;
//    private final ComputerService computerService;
//
//    @Autowired
//    public EntityValidator(ModelService modelService, TechnicService technicService, SizeService sizeService, ComputerService computerService) {
//        this.modelService = modelService;
//        this.technicService = technicService;
//        this.sizeService = sizeService;
//        this.computerService = computerService;
//    }
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return Model.class.equals(clazz) || Technic.class.equals(clazz)
//                || Computer.class.equals(clazz) || Refrigerator.class.equals(clazz) || Smartphone.class.equals(clazz)
//                || Television.class.equals(clazz) || VacuumCLeaner.class.equals(clazz);
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
//        if(target instanceof Technic){
//            Technic technic = (Technic) target;
//            if(technicService.findOne(technic).isPresent()){
//                errors.reject("technic.duplicate", "Техника с данными полями уже существует в базе данных");
//            }
//        }
//        else if(target instanceof Model){
//            Model model = (Model) target;
//            if(modelService.findOne(model).isPresent()){
//                errors.reject("model.duplicate", "Модель с данными полями уже существует в базе данных");
//            }
//        }
//        else if(target instanceof Computer){
//            Computer computer = (Computer) target;
//            if(computerService.findOne(computer).isPresent()){
//                errors.reject("computer.duplicate", "Компьютер с данными полями уже существует в базе данных");
//            }
//        }
//        else {
//            errors.reject("unsupported.type", "Неподдерживаемый тип объекта для валидации");
//        }
//    }
//}




















