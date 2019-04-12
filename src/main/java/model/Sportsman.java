package model;

import javax.persistence.*;

@Entity
@Table (name = "sportsman")
public class Sportsman {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sportsman_id_seq")
    private int id;

    @Column(name = "FName")
    private String fname;

    @Column(name = "MName")
    private String mname;

    @Column(name = "LName")
    private String lname;

    @Column(name = "old")
    private int age;

    @Column(name = "weight")
    private int weight;

    @ManyToOne()
    @JoinColumn(name="ResultID", nullable=false)
    private Result result;

    @ManyToOne()
    @JoinColumn(name="ContryID", nullable=false)
    private Country country;

    @ManyToOne()
    @JoinColumn(name="MedicineID", nullable=false)
    private Medicine medicine;

    public Sportsman() {}

    public Sportsman(String fname, String mname, String lname, int age, int weight, Result result, Country country, Medicine medicine) {
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.age = age;
        this.weight = weight;
        this.result = result;
        this.country = country;
        this.medicine = medicine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    @Override
    public String toString() {
        return "models.Sportsman{" +
                "FName = " + fname +
                '}';
    }
}
