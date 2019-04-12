package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "Result")
public class Result {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "result_id_seq")
    private int id;

    @Column(name = "place")
    private int place;

    @Column(name = "age")
    private int year;

    @Column(name = "Record")
    private int record;

    @ManyToOne
    @JoinColumn(name="DisciplineID", nullable=false)
    private Discipline discipline;


    @OneToMany(mappedBy = "result", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sportsman> sportsmans;

    public Result() {}

    public Result(int place, int year, int record, Discipline discipline) {
        this.place = place;
        this.year = year;
        this.record = record;
        this.discipline = discipline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRecord() {
        return record;
    }

    public void setRecord(int record) {
        this.record = record;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public List<Sportsman> getSportsmans() {
        return sportsmans;
    }

    public void setSportsmans(List<Sportsman> sportsmans) {
        this.sportsmans = sportsmans;
    }

    @Override
    public String toString() {
        return place + " in " + discipline.getName();
    }
}
