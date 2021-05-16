package model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "antwort", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"ant_mit_id", "ant_frage_id"})
})
public class Antwort implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ant_id")
    private Integer ant_id;

    @ManyToOne
    @JoinColumn(name = "ant_mit_id", nullable = false)
    private Mitarbeiter mitarbeiter;

    @ManyToOne
    @JoinColumn(name = "ant_frage_id", nullable = false)
    private Frage frage;

    @Column(name="antwort")
    private Antwortmoeglichkeiten antwort;

    @Column(name="timestamp")
    private LocalDateTime timestamp;

    public Antwort(Antwortmoeglichkeiten antwort, LocalDateTime timestamp) {
        this.antwort = antwort;
        this.timestamp = timestamp;
    }

    public Antwort() {

    }

    public Integer getAnt_id() {
        return ant_id;
    }

    public void setAnt_id(Integer ant_id) {
        this.ant_id = ant_id;
    }

    public Mitarbeiter getMitarbeiter() {
        return mitarbeiter;
    }

    public void setMitarbeiter(Mitarbeiter mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
    }

    public Frage getFrage() {
        return frage;
    }

    public void setFrage(Frage frage) {
        if(timestamp.isAfter(frage.getAblaufdatum())) {
            throw new IllegalArgumentException("A question that is expired may not be answered anymore.");
        }
        this.frage = frage;
    }

    public Antwortmoeglichkeiten getAntwort() {
        return antwort;
    }

    public void setAntwort(Antwortmoeglichkeiten antwort) {
        this.antwort = antwort;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Antwort antwort = (Antwort) o;
        return ant_id.equals(antwort.ant_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ant_id);
    }

    @Override
    public String toString() {
        return "Antwort{" +
                "ant_id=" + ant_id +
                ", antwort=" + antwort +
                ", timestamp=" + timestamp +
                '}';
    }
}


