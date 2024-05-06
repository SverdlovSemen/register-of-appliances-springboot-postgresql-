package ru.sverdlov.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sverdlov.app.dto.SizeDTO;
import ru.sverdlov.app.models.util.Size;
import ru.sverdlov.app.repositories.SizeRepository;

import java.util.Optional;

@Service
public class SizeService {
    private final SizeRepository sizeRepository;

    @Autowired
    public SizeService(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    public Optional<Size> findOne(Size size){
        return sizeRepository.findOneByLengthAndWidthAndHeight(
                size.getLength(),
                size.getWidth(),
                size.getHeight()
        );
    }

    public void save(Size size){
        if(size != null){
            Optional<Size> sizeFromDatabase = findOne(size);
            if(sizeFromDatabase.isPresent()){
                size.setId(sizeFromDatabase.get().getId());
            } else {
                sizeRepository.save(size);
            }
        }
    }
}
