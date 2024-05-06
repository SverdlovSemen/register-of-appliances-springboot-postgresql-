package ru.sverdlov.app.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.sverdlov.app.dto.TechnicDTO;
import ru.sverdlov.app.models.Technic;
import ru.sverdlov.app.models.util.error.EntityErrorResponse;
import ru.sverdlov.app.models.util.error.EntityNotCreatedException;
import ru.sverdlov.app.models.util.error.EntityNotFoundException;
import ru.sverdlov.app.models.util.validator.TechnicValidator;
import ru.sverdlov.app.services.TechnicService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/technics")
public class TechnicController implements BaseController<Technic, TechnicDTO> {
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
    @Override
    public List<TechnicDTO> getAll() {
        return technicService.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Override
    public TechnicDTO getById(@PathVariable("id") int id) {
        return convertToDTO(technicService.findOne(id));
    }

    @PostMapping("/registration")
    @Override
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid TechnicDTO technicDTO, BindingResult bindingResult) {
        Technic technic = convertToEntity(technicDTO);
        technicValidator.validate(technic, bindingResult);

        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<ObjectError> errors = bindingResult.getAllErrors();
            for(ObjectError error : errors){
                errorMsg.append(error.getDefaultMessage()).append(";");
            }

            throw new EntityNotCreatedException(errorMsg.toString());
        }

        technicService.save(technic);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    @Override
    public ResponseEntity<EntityErrorResponse> handleException(EntityNotFoundException e) {
        EntityErrorResponse response = new EntityErrorResponse("Техника с таким id не была найдена", System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @Override
    public ResponseEntity<EntityErrorResponse> handleException(EntityNotCreatedException e) {
        EntityErrorResponse response = new EntityErrorResponse("Техника не была создана. " + e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @Override
    public TechnicDTO convertToDTO(Technic technic) {
        return modelMapper.map(technic, TechnicDTO.class);
    }

    @Override
    public Technic convertToEntity(TechnicDTO technicDTO) {
        return modelMapper.map(technicDTO, Technic.class);
    }
}
