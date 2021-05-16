package model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "frage")
public class Frage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @NotNull
    @Column(name = "ueberschrift", length = 20)
    private String ueberschrift;

    @NotNull
    @Column(name = "fragetext", length = 200)
    private String fragetext;

    @Column(name = "ablaufdatum")
    private LocalDateTime ablaufdatum;

    @OneToMany(mappedBy = "frage")
    private Set<Antwort> antworten = new HashSet<>();

    public Frage(String ueberschrift, String fragetext, LocalDateTime ablaufdatum) {

        this.ueberschrift = ueberschrift;
        this.fragetext = fragetext;
        this.ablaufdatum = ablaufdatum;
    }

    public Frage() {}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUeberschrift() {
        return ueberschrift;
    }

    public void setUeberschrift(String ueberschrift) {
        this.ueberschrift = ueberschrift;
    }

    public String getFragetext() {
        return fragetext;
    }

    public void setFragetext(String fragetext) {
        this.fragetext = fragetext;
    }

    public LocalDateTime getAblaufdatum() {
        return ablaufdatum;
    }

    public void setAblaufdatum(LocalDateTime ablaufdatum) {
        this.ablaufdatum = ablaufdatum;
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
        Frage frage = (Frage) o;
        return id.equals(frage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Frage{" +
                "id=" + id +
                ", ueberschrift='" + ueberschrift + '\'' +
                ", fragetext='" + fragetext + '\'' +
                ", ablaufdatum=" + ablaufdatum +
                '}';
    }
}
