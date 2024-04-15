package ru.sverdlov.app.services;

import ru.sverdlov.app.repositories.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface BaseService<T> {
    List<T> findAll();
    T findOne(int id);
    Optional<T> findOne(T t);
    void save(T t);
}
