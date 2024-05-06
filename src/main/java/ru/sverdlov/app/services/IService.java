package ru.sverdlov.app.services;


import java.util.List;
import java.util.Optional;

public interface IService<T> {
    List<T> findAll();
    T findOne(int id);
    Optional<T> findOne(T t);
    void save(T t);
}
