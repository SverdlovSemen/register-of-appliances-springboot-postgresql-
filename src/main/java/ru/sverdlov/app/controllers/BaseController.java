package ru.sverdlov.app.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.sverdlov.app.models.util.EntityErrorResponse;
import ru.sverdlov.app.models.util.EntityNotCreatedException;
import ru.sverdlov.app.models.util.EntityNotFoundException;

import java.util.List;

public interface BaseController<Entity, DTO> {
    List<DTO> getAll();
    DTO getById(@PathVariable("id") int id);
    ResponseEntity<HttpStatus> create(@RequestBody @Valid DTO dto, BindingResult bindingResult);
    ResponseEntity<EntityErrorResponse> handleException(EntityNotFoundException e);
    ResponseEntity<EntityErrorResponse> handleException(EntityNotCreatedException e);
    DTO convertToDTO(Entity entity);
    Entity convertToEntity(DTO dto);
}
