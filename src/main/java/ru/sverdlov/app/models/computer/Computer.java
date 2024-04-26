package ru.sverdlov.app.models.computer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import ru.sverdlov.app.models.Model;

@Entity
@Table(name = "Computer")
public class Computer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    @NotNull(message = "У компьютера должна быть модель")
    private Model model;

    @NotNull(message = "Категория не должна быть пустой")
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @NotNull(message = "Тип процессора не должен быть пустым")
    @Enumerated(EnumType.STRING)
    @Column(name = "processor_type")
    private ProcessorType processorType;

    public Computer(){}

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

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
