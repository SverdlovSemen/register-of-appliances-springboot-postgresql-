package ru.sverdlov.test.models.computer;

import ru.sverdlov.test.models.Model;

public class Computer extends Model {
    private Category category;
    private ProcessorType processorType;

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
