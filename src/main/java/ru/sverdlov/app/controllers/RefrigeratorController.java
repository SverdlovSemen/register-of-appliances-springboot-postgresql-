package ru.sverdlov.app.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.sverdlov.app.dto.*;
import ru.sverdlov.app.models.Model;
import ru.sverdlov.app.models.Technic;
import ru.sverdlov.app.models.refrigerator.Refrigerator;
import ru.sverdlov.app.models.util.*;
import ru.sverdlov.app.models.util.error.EntityErrorResponse;
import ru.sverdlov.app.models.util.error.EntityNotCreatedException;
import ru.sverdlov.app.models.util.error.EntityNotFoundException;
import ru.sverdlov.app.models.util.validator.RefrigeratorValidator;
import ru.sverdlov.app.services.RefrigeratorService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/refrigerators")
public class RefrigeratorController implements BaseController<Refrigerator, RefrigeratorDTO> {
    private final RefrigeratorService refrigeratorService;
    private final ModelMapper modelMapper;
    private final RefrigeratorValidator refrigeratorValidator;

    @Autowired
    public RefrigeratorController(RefrigeratorService refrigeratorService, ModelMapper modelMapper, RefrigeratorValidator refrigeratorValidator) {
        this.refrigeratorService = refrigeratorService;
        this.modelMapper = modelMapper;
        this.refrigeratorValidator = refrigeratorValidator;
    }

    @GetMapping()
    @Override
    public List<RefrigeratorDTO> getAll(){
        return refrigeratorService.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Override
    public RefrigeratorDTO getById(@PathVariable("id") int id){
        return convertToDTO(refrigeratorService.findOne(id));
    }

    @PostMapping("/registration")
    @Override
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid RefrigeratorDTO refrigeratorDTO, BindingResult bindingResult){
        Refrigerator refrigerator = convertToEntity(refrigeratorDTO);
        refrigeratorValidator.validate(refrigerator, bindingResult);

        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<ObjectError> errors = bindingResult.getAllErrors();
            for(ObjectError error : errors){
                errorMsg.append(error.getDefaultMessage()).append("; ");
            }

            throw new EntityNotCreatedException(errorMsg.toString());
        }

        refrigeratorService.save(refrigerator);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/search")
    public List<RefrigeratorDTO> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice,
            @RequestParam(required = false) Integer minNumberOfDoors,
            @RequestParam(required = false) Integer maxNumberOfDoors,
            @RequestParam(required = false) String compressorType) {

        return refrigeratorService.findAllByFilters(name, color, minPrice, maxPrice, minNumberOfDoors, maxNumberOfDoors,
                        compressorType).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @ExceptionHandler
    @Override
    public ResponseEntity<EntityErrorResponse> handleException(EntityNotFoundException e){
        EntityErrorResponse response = new EntityErrorResponse("Холодильник с таким id не был найден", System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @Override
    public ResponseEntity<EntityErrorResponse> handleException(EntityNotCreatedException e){
        EntityErrorResponse response = new EntityErrorResponse("Холодильник не был создан. " + e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @Override
    public RefrigeratorDTO convertToDTO(Refrigerator refrigerator) {
        RefrigeratorDTO refrigeratorDTO = modelMapper.map(refrigerator, RefrigeratorDTO.class);
        Model model = refrigerator.getModel();
        if(model != null) {
            refrigeratorDTO.setModelDTO(modelMapper.map(model, ModelDTO.class));

            Size size = model.getSize();
            if(size != null)
                refrigeratorDTO.getModelDTO().setSizeDTO(modelMapper.map(size, SizeDTO.class));

            Technic technic = model.getTechnic();
            if(technic != null)
                refrigeratorDTO.getModelDTO().setTechnicDTO(modelMapper.map(technic, TechnicDTO.class));
        }

        return refrigeratorDTO;
    }

    @Override
    public Refrigerator convertToEntity(RefrigeratorDTO refrigeratorDTO) {
        return modelMapper.map(refrigeratorDTO, Refrigerator.class);
    }
}
























