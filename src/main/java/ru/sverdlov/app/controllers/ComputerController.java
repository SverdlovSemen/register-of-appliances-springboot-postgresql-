package ru.sverdlov.app.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.sverdlov.app.dto.ComputerDTO;
import ru.sverdlov.app.dto.ModelDTO;
import ru.sverdlov.app.dto.SizeDTO;
import ru.sverdlov.app.dto.TechnicDTO;
import ru.sverdlov.app.models.Model;
import ru.sverdlov.app.models.Technic;
import ru.sverdlov.app.models.computer.Category;
import ru.sverdlov.app.models.computer.Computer;
import ru.sverdlov.app.models.util.*;
import ru.sverdlov.app.services.ComputerService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/computers")
public class ComputerController implements BaseController<Computer, ComputerDTO> {
    private final ComputerService computerService;
    private final ModelMapper modelMapper;
    private final EntityValidator entityValidator;

    @Autowired
    public ComputerController(ComputerService computerService, ModelMapper modelMapper, EntityValidator entityValidator) {
        this.computerService = computerService;
        this.modelMapper = modelMapper;
        this.entityValidator = entityValidator;
    }

    @GetMapping()
    @Override
    public List<ComputerDTO> getAll(){
        return computerService.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Override
    public ComputerDTO getById(@PathVariable("id") int id){
        return convertToDTO(computerService.findOne(id));
    }

    @PostMapping("/registration")
    @Override
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid ComputerDTO computerDTO, BindingResult bindingResult){
        Computer computer = convertToEntity(computerDTO);
        entityValidator.validate(computer, bindingResult);

        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<ObjectError> errors = bindingResult.getAllErrors();
            for(ObjectError error : errors){
                errorMsg.append(error.getDefaultMessage()).append("; ");
            }

            throw new EntityNotCreatedException(errorMsg.toString());
        }

        computerService.save(computer);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    @Override
    public ResponseEntity<EntityErrorResponse> handleException(EntityNotFoundException e){
        EntityErrorResponse response = new EntityErrorResponse("Компьютер с таким id не был найден", System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @Override
    public ResponseEntity<EntityErrorResponse> handleException(EntityNotCreatedException e){
        EntityErrorResponse response = new EntityErrorResponse("Компьютер не был создан. " + e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ComputerDTO convertToDTO(Computer computer) {
        ComputerDTO computerDTO = modelMapper.map(computer, ComputerDTO.class);
        Model model = computer.getModel();
        if(model != null) {
            computerDTO.setModelDTO(modelMapper.map(model, ModelDTO.class));

            Size size = model.getSize();
            if(size != null)
                computerDTO.getModelDTO().setSizeDTO(modelMapper.map(size, SizeDTO.class));

            Technic technic = model.getTechnic();
            if(technic != null)
                computerDTO.getModelDTO().setTechnicDTO(modelMapper.map(technic, TechnicDTO.class));
        }

        return computerDTO;
    }

    @Override
    public Computer convertToEntity(ComputerDTO computerDTO) {
        return modelMapper.map(computerDTO, Computer.class);
    }
}