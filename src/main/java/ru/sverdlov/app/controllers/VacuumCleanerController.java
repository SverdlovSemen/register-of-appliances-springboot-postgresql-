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
import ru.sverdlov.app.models.util.validator.VacuumCleanerValidator;

import ru.sverdlov.app.models.util.Size;
import ru.sverdlov.app.models.util.error.EntityErrorResponse;
import ru.sverdlov.app.models.util.error.EntityNotCreatedException;
import ru.sverdlov.app.models.util.error.EntityNotFoundException;
import ru.sverdlov.app.models.vacuumCleaner.VacuumCleaner;
import ru.sverdlov.app.services.VacuumCleanerService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vacuumcleaners")
public class VacuumCleanerController implements BaseController<VacuumCleaner, VacuumCleanerDTO> {
    private final VacuumCleanerService vacuumCleanerService;
    private final ModelMapper modelMapper;
    private final VacuumCleanerValidator vacuumCleanerValidator;

    @Autowired
    public VacuumCleanerController(VacuumCleanerService vacuumCleanerService, ModelMapper modelMapper, VacuumCleanerValidator vacuumCleanerValidator) {
        this.vacuumCleanerService = vacuumCleanerService;
        this.modelMapper = modelMapper;
        this.vacuumCleanerValidator = vacuumCleanerValidator;
    }

    @GetMapping()
    @Override
    public List<VacuumCleanerDTO> getAll(){
        return vacuumCleanerService.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Override
    public VacuumCleanerDTO getById(@PathVariable("id") int id){
        return convertToDTO(vacuumCleanerService.findOne(id));
    }

    @PostMapping("/registration")
    @Override
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid VacuumCleanerDTO vacuumCleanerDTO, BindingResult bindingResult){
        VacuumCleaner vacuumCleaner = convertToEntity(vacuumCleanerDTO);
        vacuumCleanerValidator.validate(vacuumCleaner, bindingResult);

        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<ObjectError> errors = bindingResult.getAllErrors();
            for(ObjectError error : errors){
                errorMsg.append(error.getDefaultMessage()).append("; ");
            }

            throw new EntityNotCreatedException(errorMsg.toString());
        }

        vacuumCleanerService.save(vacuumCleaner);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/search")
    public List<VacuumCleanerDTO> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice,
            @RequestParam(required = false) Integer minVolumeOfDustCollector,
            @RequestParam(required = false) Integer maxVolumeOfDustCollector,
            @RequestParam(required = false) Integer minNumberOfModes,
            @RequestParam(required = false) Integer maxNumberOfModes) {

        return vacuumCleanerService.findAllByFilters(name, color, minPrice, maxPrice, minVolumeOfDustCollector, maxVolumeOfDustCollector,
                minNumberOfModes, maxNumberOfModes).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @ExceptionHandler
    @Override
    public ResponseEntity<EntityErrorResponse> handleException(EntityNotFoundException e){
        EntityErrorResponse response = new EntityErrorResponse("Пылесос с таким id не был найден", System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @Override
    public ResponseEntity<EntityErrorResponse> handleException(EntityNotCreatedException e){
        EntityErrorResponse response = new EntityErrorResponse("Пылесос не был создан. " + e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @Override
    public VacuumCleanerDTO convertToDTO(VacuumCleaner vacuumCleaner) {
        VacuumCleanerDTO vacuumCleanerDTO = modelMapper.map(vacuumCleaner, VacuumCleanerDTO.class);
        Model model = vacuumCleaner.getModel();
        if(model != null) {
            vacuumCleanerDTO.setModelDTO(modelMapper.map(model, ModelDTO.class));

            Size size = model.getSize();
            if(size != null)
                vacuumCleanerDTO.getModelDTO().setSizeDTO(modelMapper.map(size, SizeDTO.class));

            Technic technic = model.getTechnic();
            if(technic != null)
                vacuumCleanerDTO.getModelDTO().setTechnicDTO(modelMapper.map(technic, TechnicDTO.class));
        }

        return vacuumCleanerDTO;
    }

    @Override
    public VacuumCleaner convertToEntity(VacuumCleanerDTO vacuumCleanerDTO) {
        return modelMapper.map(vacuumCleanerDTO, VacuumCleaner.class);
    }
}
