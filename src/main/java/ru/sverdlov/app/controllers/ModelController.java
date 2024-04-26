package ru.sverdlov.app.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.sverdlov.app.dto.ModelDTO;
import ru.sverdlov.app.dto.SizeDTO;
import ru.sverdlov.app.dto.TechnicDTO;
import ru.sverdlov.app.models.Model;
import ru.sverdlov.app.models.Technic;
import ru.sverdlov.app.models.util.EntityErrorResponse;
import ru.sverdlov.app.models.util.EntityNotCreatedException;
import ru.sverdlov.app.models.util.EntityNotFoundException;
import ru.sverdlov.app.models.util.EntityValidator;
import ru.sverdlov.app.services.ModelService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/models")
public class ModelController implements BaseController<Model, ModelDTO> {
    private final ModelService modelService;
    private final ModelMapper modelMapper;
    private final EntityValidator entityValidator;

    @Autowired
    public ModelController(ModelService modelService, ModelMapper modelMapper, EntityValidator entityValidator) {
        this.modelService = modelService;
        this.modelMapper = modelMapper;
        this.entityValidator = entityValidator;
    }

    @GetMapping()
    @Override
    public List<ModelDTO> getAll() {
        return modelService.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Override
    public ModelDTO getById(@PathVariable("id") int id) {
        return convertToDTO(modelService.findOne(id));
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid ModelDTO modelDTO, BindingResult bindingResult){
        Model model = convertToEntity(modelDTO);
        entityValidator.validate(model, bindingResult);

        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<ObjectError> errors = bindingResult.getAllErrors();
            for(ObjectError error : errors){
                errorMsg.append(error.getDefaultMessage()).append(";");
            }

            throw new EntityNotCreatedException(errorMsg.toString());
        }

        modelService.save(model);
        return ResponseEntity.ok(HttpStatus.OK);

    }

    @ExceptionHandler
    @Override
    public ResponseEntity<EntityErrorResponse> handleException(EntityNotFoundException e){
        EntityErrorResponse response = new EntityErrorResponse("Модель техники с таким id не была найдена", System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @Override
    public ResponseEntity<EntityErrorResponse> handleException(EntityNotCreatedException e){
        EntityErrorResponse response = new EntityErrorResponse("Модель техники не была создана. " + e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ModelDTO convertToDTO(Model model) {
        ModelDTO modelDTO = modelMapper.map(model, ModelDTO.class);
        if(model.getTechnic() != null)
            modelDTO.setTechnicDTO(modelMapper.map(model.getTechnic(), TechnicDTO.class));
        if(model.getSize() != null)
            modelDTO.setSizeDTO(modelMapper.map(model.getSize(), SizeDTO.class));

        return modelDTO;
    }

    @Override
    public Model convertToEntity(ModelDTO modelDTO) {
        return modelMapper.map(modelDTO, Model.class);
    }
}























