package model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "mitarbeiter")
public class Mitarbeiter implements Serializable {

    @Id
    @Min(value = 111111) //wegen sechstellig yk
    @Column(name = "mit_nr", unique = true)
    private Integer mit_nr;

    @Column(name = "familienName")
    private String familienName;

    @Column(name = "vorname")
    private String vorname;

    @OneToMany(mappedBy = "mitarbeiter")
    private Set<Antwort> antworten = new HashSet<>();

    public Mitarbeiter(Integer mit_nr, String familienName, String vorname) {
        this.mit_nr = mit_nr;
        this.familienName = familienName;
        this.vorname = vorname;
    }

    public Mitarbeiter() {

    }


    public Integer getMit_nr() {
        return mit_nr;
    }

    public void setMit_nr(Integer mit_nr) {
        this.mit_nr = mit_nr;
    }

    public String getFamilienName() {
        return familienName;
    }

    public void setFamilienName(String familienName) {
        this.familienName = familienName;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public Set<Antwort> getAntworten() {
        return antworten;
    }

    public void setAntworten(Set<Antwort> antworten) {
        this.antworten = antworten;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mitarbeiter that = (Mitarbeiter) o;
        return mit_nr.equals(that.mit_nr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mit_nr);
    }
}

