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
import ru.sverdlov.app.models.smartphone.Smartphone;
import ru.sverdlov.app.models.util.Size;
import ru.sverdlov.app.models.util.error.EntityErrorResponse;
import ru.sverdlov.app.models.util.error.EntityNotCreatedException;
import ru.sverdlov.app.models.util.error.EntityNotFoundException;
import ru.sverdlov.app.models.util.validator.SmartphoneValidator;
import ru.sverdlov.app.services.SmartphoneService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/smartphones")
public class SmartphoneController implements BaseController<Smartphone, SmartphoneDTO> {
    private final SmartphoneService smartphoneService;
    private final ModelMapper modelMapper;
    private final SmartphoneValidator smartphoneValidator;

    @Autowired
    public SmartphoneController(SmartphoneService smartphoneService, ModelMapper modelMapper, SmartphoneValidator smartphoneValidator) {
        this.smartphoneService = smartphoneService;
        this.modelMapper = modelMapper;
        this.smartphoneValidator = smartphoneValidator;
    }

    @GetMapping()
    @Override
    public List<SmartphoneDTO> getAll(){
        return smartphoneService.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Override
    public SmartphoneDTO getById(@PathVariable("id") int id){
        return convertToDTO(smartphoneService.findOne(id));
    }

    @PostMapping("/registration")
    @Override
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SmartphoneDTO smartphoneDTO, BindingResult bindingResult){
        Smartphone smartphone = convertToEntity(smartphoneDTO);
        smartphoneValidator.validate(smartphone, bindingResult);

        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<ObjectError> errors = bindingResult.getAllErrors();
            for(ObjectError error : errors){
                errorMsg.append(error.getDefaultMessage()).append("; ");
            }

            throw new EntityNotCreatedException(errorMsg.toString());
        }

        smartphoneService.save(smartphone);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/search")
    public List<SmartphoneDTO> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice,
            @RequestParam(required = false) Integer minMemory,
            @RequestParam(required = false) Integer maxMemory,
            @RequestParam(required = false) Integer minNumberOfCameras,
            @RequestParam(required = false) Integer maxNumberOfCameras) {

        return smartphoneService.findAllByFilters(name, color, minPrice, maxPrice, minMemory, maxMemory,minNumberOfCameras
                ,maxNumberOfCameras).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @ExceptionHandler
    @Override
    public ResponseEntity<EntityErrorResponse> handleException(EntityNotFoundException e){
        EntityErrorResponse response = new EntityErrorResponse("Смартфон с таким id не был найден", System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @Override
    public ResponseEntity<EntityErrorResponse> handleException(EntityNotCreatedException e){
        EntityErrorResponse response = new EntityErrorResponse("Смартфон не был создан. " + e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @Override
    public SmartphoneDTO convertToDTO(Smartphone smartphone) {
        SmartphoneDTO smartphoneDTO = modelMapper.map(smartphone, SmartphoneDTO.class);
        Model model = smartphone.getModel();
        if(model != null) {
            smartphoneDTO.setModelDTO(modelMapper.map(model, ModelDTO.class));

            Size size = model.getSize();
            if(size != null)
                smartphoneDTO.getModelDTO().setSizeDTO(modelMapper.map(size, SizeDTO.class));

            Technic technic = model.getTechnic();
            if(technic != null)
                smartphoneDTO.getModelDTO().setTechnicDTO(modelMapper.map(technic, TechnicDTO.class));
        }

        return smartphoneDTO;
    }

    @Override
    public Smartphone convertToEntity(SmartphoneDTO smartphoneDTO) {
        return modelMapper.map(smartphoneDTO, Smartphone.class);
    }
}
