//package ru.sverdlov.app.controllers;
//
//import jakarta.validation.Valid;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.RequestBody;
//import ru.sverdlov.app.dto.ModelDTO;
//import ru.sverdlov.app.models.util.BaseValidator;
//import ru.sverdlov.app.models.util.ErrorResponse;
//import ru.sverdlov.app.services.BaseService;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public abstract class BaseControllerImpl<T> implements BaseController {
//    protected final BaseService<T> service;
//    protected final ModelMapper modelMapper;
//    protected final BaseValidator<T> validator;
//
//    public BaseControllerImpl(BaseService<T> service, ModelMapper modelMapper, BaseValidator<T> validator) {
//        this.service = service;
//        this.modelMapper = modelMapper;
//        this.validator = validator;
//    }
//
//    @Override
//    public List<ModelDTO> getAll() {
//        return service.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
//    }
//
//    @Override
//    public BaseDTO getById(int id) {
//        return convertToDTO(service.findOne(id));
//    }
//
//    @Override
//    public ResponseEntity<HttpStatus> create(@RequestBody @Valid BaseDTO baseDTO, BindingResult bindingResult) {
//        Base base = convertToEntity(baseDTO);
//        return null;
//    }
//
//    @Override
//    public ResponseEntity<ErrorResponse> handleException(Exception e) {
//        return null;
//    }
//
//    protected abstract Class<T> getClassType();
//
//    protected ModelDTO convertToDTO(T entity){ return modelMapper.map(entity, ModelDTO.class);}
//
//    protected T convertToEntity(BaseDTO baseDTO){ return modelMapper.map(baseDTO, T.class);}
//}
