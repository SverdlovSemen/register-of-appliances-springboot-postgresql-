package ru.sverdlov.test.models.television;

import ru.sverdlov.test.models.Model;

public class Television extends Model {
    private Category category;
    private Technology technology;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Technology getTechnology() {
        return technology;
    }

    public void setTechnology(Technology technology) {
        this.technology = technology;
    }
}
