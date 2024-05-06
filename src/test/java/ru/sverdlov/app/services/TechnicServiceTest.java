package ru.sverdlov.app.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sverdlov.app.models.Technic;
import ru.sverdlov.app.models.util.error.EntityNotFoundException;
import ru.sverdlov.app.repositories.TechnicRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TechnicServiceTest {
    @Mock
    private TechnicRepository technicRepository;

    @InjectMocks
    private TechnicService technicService;

    private Technic technic1;
    private Technic technic2;

    @BeforeEach
    void setUp(){
        technic1 = new Technic(1, "Headphones", "Britain", "Marshall");
        technic2 = new Technic(2, "Keyboard", "Switzerland", "Logitech");
    }

    @Test
    void findAll(){
        Mockito.when(technicRepository.findAll()).thenReturn(List.of(technic1, technic2));

        Assertions.assertEquals(technicService.findAll(), List.of(technic1, technic2));
    }

    @Test
    void findOne_technicExist_returnTechnic(){
        int id = 2;
        Mockito.when(technicRepository.findById(id)).thenReturn(Optional.of(technic2));

        Assertions.assertEquals(technicService.findOne(id), technic2);
    }

    @Test
    void findOne_technicNotExist_throwsException(){
        int notExistingId = 99;
        Mockito.when(technicRepository.findById(notExistingId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> technicService.findOne(notExistingId));
    }

    @Test
    void save(){
        technicService.save(technic1);
        Mockito.verify(technicRepository, Mockito.times(1)).save(technic1);
    }
}























