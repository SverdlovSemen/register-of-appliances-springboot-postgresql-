package ru.sverdlov.app.models.computer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import ru.sverdlov.app.models.Model;

@Entity
@Table(name = "Computer")
public class Computer extends Model {

    @NotNull(message = "Категория не должна быть пустой")
    @Column(name = "category")
    private Category category;

    @NotNull(message = "Тип процессора не должен быть пустым")
    @Column(name = "processorType")
    private ProcessorType processorType;

    public Computer(){}

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ProcessorType getProcessorType() {
        return processorType;
    }

    public void setProcessorType(ProcessorType processorType) {
        this.processorType = processorType;
    }
}
