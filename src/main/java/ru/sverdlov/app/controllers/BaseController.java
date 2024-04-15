package ru.sverdlov.app.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import ru.sverdlov.app.dto.ModelDTO;
import ru.sverdlov.app.models.util.ErrorResponse;
import ru.sverdlov.app.models.util.utilModel.ModelErrorResponse;
import ru.sverdlov.app.models.util.utilModel.ModelNotCreatedException;
import ru.sverdlov.app.models.util.utilModel.ModelNotFoundException;

import java.util.List;

public interface BaseController<T> {
    List<T> getAll();
    T getById(int id);
    ResponseEntity<HttpStatus> create(@RequestBody @Valid ModelDTO baseDTO, BindingResult bindingResult);
    ResponseEntity<ModelErrorResponse> handleException(ModelNotFoundException e);
    ResponseEntity<ModelErrorResponse> handleException(ModelNotCreatedException e);
}
