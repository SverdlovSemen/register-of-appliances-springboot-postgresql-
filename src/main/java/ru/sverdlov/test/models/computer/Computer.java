package ru.sverdlov.test.models.computer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import ru.sverdlov.test.models.Model;

@Entity
@Table(name = "Computer")
public class Computer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "У компьютера должна быть модель")
    @ManyToOne
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    private Model model;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
