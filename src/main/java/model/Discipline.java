package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "Discipline")
public class Discipline {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "discipline_id_seq")
    private int id;

    @Column(name = "Name")
    private String name;

    @OneToMany(mappedBy = "discipline", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Result> result;

    public Discipline() {}

    public Discipline(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return name;
    }
}
