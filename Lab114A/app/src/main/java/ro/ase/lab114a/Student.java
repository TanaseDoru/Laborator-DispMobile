package ro.ase.lab114a;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "studenti")
public class Student implements Serializable {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nume;
    private Date dataNasterii;
    private float medie;
    private String facultate; // Calculatoare, Comunicatii, Constructii,
    private boolean tipScolarizare; // buget = true, taxa = false

    public Student(String nume, Date dataNasterii, float medie, String facultate, boolean tipScolarizare) {
        this.nume = nume;
        this.dataNasterii = dataNasterii;
        this.medie = medie;
        this.facultate = facultate;
        this.tipScolarizare = tipScolarizare;
    }

    @Ignore
    public Student() {}

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Date getDataNasterii() {
        return dataNasterii;
    }

    public void setDataNasterii(Date dataNasterii) {
        this.dataNasterii = dataNasterii;
    }

    public float getMedie() {
        return medie;
    }

    public void setMedie(float medie) {
        this.medie = medie;
    }

    public String getFacultate() {
        return facultate;
    }

    public void setFacultate(String facultate) {
        this.facultate = facultate;
    }

    public boolean isTipScolarizare() {
        return tipScolarizare;
    }

    public void setTipScolarizare(boolean tipScolarizare) {
        this.tipScolarizare = tipScolarizare;
    }



    @Override
    public String toString() {
        return "Student{" +
                "nume='" + nume + '\'' +
                ", dataNasterii=" + dataNasterii +
                ", medie=" + medie +
                ", facultate='" + facultate + '\'' +
                ", tipScolarizare=" + tipScolarizare +
                '}';
    }
}
