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
import ru.sverdlov.app.models.computer.Computer;
import ru.sverdlov.app.models.television.Television;
import ru.sverdlov.app.models.util.Size;
import ru.sverdlov.app.models.util.error.EntityErrorResponse;
import ru.sverdlov.app.models.util.error.EntityNotCreatedException;
import ru.sverdlov.app.models.util.error.EntityNotFoundException;
import ru.sverdlov.app.models.util.validator.ComputerValidator;
import ru.sverdlov.app.models.util.validator.TelevisionValidator;
import ru.sverdlov.app.services.ComputerService;
import ru.sverdlov.app.services.TelevisionService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/televisions")
public class TelevisionController implements BaseController<Television, TelevisionDTO> {
    private final TelevisionService televisionService;
    private final ModelMapper modelMapper;
    private final TelevisionValidator televisionValidator;

    @Autowired
    public TelevisionController(TelevisionService televisionService, ModelMapper modelMapper, TelevisionValidator televisionValidator) {
        this.televisionService = televisionService;
        this.modelMapper = modelMapper;
        this.televisionValidator = televisionValidator;
    }

    @GetMapping()
    @Override
    public List<TelevisionDTO> getAll(){
        return televisionService.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Override
    public TelevisionDTO getById(@PathVariable("id") int id){
        return convertToDTO(televisionService.findOne(id));
    }

    @PostMapping("/registration")
    @Override
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid TelevisionDTO televisionDTO, BindingResult bindingResult){
        Television television = convertToEntity(televisionDTO);
        televisionValidator.validate(television, bindingResult);

        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<ObjectError> errors = bindingResult.getAllErrors();
            for(ObjectError error : errors){
                errorMsg.append(error.getDefaultMessage()).append("; ");
            }

            throw new EntityNotCreatedException(errorMsg.toString());
        }

        televisionService.save(television);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/search")
    public List<TelevisionDTO> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String technology) {

        return televisionService.findAllByFilters(name, color, minPrice, maxPrice, category, technology)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @ExceptionHandler
    @Override
    public ResponseEntity<EntityErrorResponse> handleException(EntityNotFoundException e){
        EntityErrorResponse response = new EntityErrorResponse("Телевизор с таким id не был найден", System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @Override
    public ResponseEntity<EntityErrorResponse> handleException(EntityNotCreatedException e){
        EntityErrorResponse response = new EntityErrorResponse("Телевизор не был создан. " + e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @Override
    public TelevisionDTO convertToDTO(Television television) {
        TelevisionDTO televisionDTO = modelMapper.map(television, TelevisionDTO.class);
        Model model = television.getModel();
        if(model != null) {
            televisionDTO.setModelDTO(modelMapper.map(model, ModelDTO.class));

            Size size = model.getSize();
            if(size != null)
                televisionDTO.getModelDTO().setSizeDTO(modelMapper.map(size, SizeDTO.class));

            Technic technic = model.getTechnic();
            if(technic != null)
                televisionDTO.getModelDTO().setTechnicDTO(modelMapper.map(technic, TechnicDTO.class));
        }

        return televisionDTO;
    }

    @Override
    public Television convertToEntity(TelevisionDTO televisionDTO) {
        return modelMapper.map(televisionDTO, Television.class);
    }
}
