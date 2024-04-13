package ru.sverdlov.app.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.sverdlov.app.dto.ModelDTO;
import ru.sverdlov.app.models.Model;
import ru.sverdlov.app.models.util.utilModel.ModelErrorResponse;
import ru.sverdlov.app.models.util.utilModel.ModelNotCreatedException;
import ru.sverdlov.app.models.util.utilModel.ModelNotFoundException;
import ru.sverdlov.app.models.util.utilModel.ModelValidator;
import ru.sverdlov.app.services.ModelService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/models")
public class ModelController {
    private final ModelService modelService;
    private final ModelMapper modelMapper;
    private final ModelValidator modelValidator;

    @Autowired
    public ModelController(ModelService modelService, ModelMapper modelMapper, ModelValidator modelValidator) {
        this.modelService = modelService;
        this.modelMapper = modelMapper;
        this.modelValidator = modelValidator;
    }

    @GetMapping()
    public List<ModelDTO> getTechnics(){
        return modelService.findAll().stream().map(this::convertToModelDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ModelDTO getModel(@PathVariable("id") int id){
        return convertToModelDTO(modelService.findOne(id));
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid ModelDTO modelDTO, BindingResult bindingResult){
        Model model = convertToModel(modelDTO);
        modelValidator.validate(model, bindingResult);

        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors){
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }

            throw new ModelNotCreatedException(errorMsg.toString());
        }

        modelService.save(model);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ModelErrorResponse> handleException(ModelNotFoundException e){
        ModelErrorResponse response = new ModelErrorResponse("Модель техники с таким id не была найдена", System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ModelErrorResponse> handleException(ModelNotCreatedException e){
        ModelErrorResponse response = new ModelErrorResponse(e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private ModelDTO convertToModelDTO(Model model){
        return modelMapper.map(model, ModelDTO.class);
    }

    private Model convertToModel(ModelDTO modelDTO){return modelMapper.map(modelDTO, Model.class);}
}
