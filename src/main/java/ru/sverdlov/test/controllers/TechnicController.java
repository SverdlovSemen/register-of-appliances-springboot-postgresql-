package ru.sverdlov.test.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.sverdlov.test.dto.TechnicDTO;
import ru.sverdlov.test.models.Technic;
import ru.sverdlov.test.services.TechnicService;
import ru.sverdlov.test.models.util.utilTechnic.TechnicErrorResponse;
import ru.sverdlov.test.models.util.utilTechnic.TechnicNotCreatedException;
import ru.sverdlov.test.models.util.utilTechnic.TechnicNotFoundException;
import ru.sverdlov.test.models.util.utilTechnic.TechnicValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/technics")
public class TechnicController {
    private final TechnicService technicService;
    private final ModelMapper modelMapper;
    private final TechnicValidator technicValidator;

    @Autowired
    public TechnicController(TechnicService technicService, ModelMapper modelMapper, TechnicValidator technicValidator) {
        this.technicService = technicService;
        this.modelMapper = modelMapper;
        this.technicValidator = technicValidator;
    }

    @GetMapping()
    public List<TechnicDTO> getTechnics(){
        return technicService.findAll().stream().map(this::convertToTechnicDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TechnicDTO getTechnic(@PathVariable("id") int id){
        return convertToTechnicDTO(technicService.findOne(id));
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid TechnicDTO technicDTO, BindingResult bindingResult){
        Technic technic = convertToTechnic(technicDTO);
        technicValidator.validate(technic, bindingResult);

        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors){
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }

            throw new TechnicNotCreatedException(errorMsg.toString());
        }

        technicService.save(technic);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<TechnicErrorResponse> handleException(TechnicNotFoundException e){
        TechnicErrorResponse response = new TechnicErrorResponse("Техника с таким id не была найдена", System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<TechnicErrorResponse> handleException(TechnicNotCreatedException e){
        TechnicErrorResponse response = new TechnicErrorResponse(e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private TechnicDTO convertToTechnicDTO(Technic technic){
        return modelMapper.map(technic, TechnicDTO.class);
    }

    private Technic convertToTechnic(TechnicDTO technicDTO){return modelMapper.map(technicDTO, Technic.class);}
}
