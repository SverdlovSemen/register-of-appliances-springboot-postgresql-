package ru.sverdlov.test.models.television;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.sverdlov.test.models.Model;

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

    @NotEmpty(message = "Категория у телевизора не должна быть пустой")
    @Size(min = 3, max = 100, message = "Категория у телевизора должна быть в пределах от 3 до 100 символов")
    @Column(name = "category")
    private Category category;

    @NotEmpty(message = "Технология у телевизора не должна быть пустой")
    @Size(min = 3, max = 100, message = "Технология у телевизора должна быть в пределах от 3 до 100 символов")
    @Column(name = "technology")
    private Technology technology;

    public Television(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
