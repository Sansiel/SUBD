package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "Contry")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Name")
    private String name;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sportsman> sportsmans;

    public Country() {}

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

    public List<Sportsman> getSportsmans() {
        return sportsmans;
    }

    public void setSportsmans(List<Sportsman> sportsmans) {
        this.sportsmans = sportsmans;
    }

    @Override
    public String toString() {
        return "models.Country{" +
                "Name = " + name +
                '}';
    }
}
