package ru.sverdlov.app.models.television;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.sverdlov.app.models.Model;

@Entity
@Table(name = "Television")
public class Television {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "У телевизора должна быть модель")
    @ManyToOne
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    private Model model;

    @NotNull(message = "Категория у телевизора не должна быть пустой")
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @NotNull(message = "Технология у телевизора не должна быть пустой")
    @Enumerated(EnumType.STRING)
    @Column(name = "technology")
    private Technology technology;

    public Television(){}

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

    public Technology getTechnology() {
        return technology;
    }

    public void setTechnology(Technology technology) {
        this.technology = technology;
    }
}
