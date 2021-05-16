import db.AntwortRepository;
import db.FrageRepository;
import db.MitarbeiterRepository;
import model.Antwort;
import model.Antwortmoeglichkeiten;
import model.Frage;
import model.Mitarbeiter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AntwortSystemTest {
    AntwortRepository antwortRepository;
    FrageRepository frageRepository;
    MitarbeiterRepository mitarbeiterRepository;
    @BeforeEach
    void initialize() {
        antwortRepository = AntwortRepository.getInstance();
        frageRepository = FrageRepository.getInstance();
        mitarbeiterRepository = MitarbeiterRepository.getInstance();
        insertTestData();
    }

    private void insertTestData() {
        Mitarbeiter m1 = new Mitarbeiter(111111, "Haiden", "Niklas");
        Mitarbeiter m2 = new Mitarbeiter(222222, "Reagan", "Ronald");
        Mitarbeiter m3 = new Mitarbeiter(333333, "Trump", "Donald");
        Mitarbeiter[] mitarbeiterArr = {m1, m2, m3};
        Arrays.stream(mitarbeiterArr).forEach(mitarbeiterRepository::persistMitarbeiter);

        Frage f1 = new Frage("Frage 1", "Sind Sie mit Ihrem aktuellen Arbeitsplatz zufrieden?",
                LocalDateTime.now().plusDays(1));
        Frage f2 = new Frage("Frage 2", "Ist Ihnen unser Essensangebot genehm?",
                LocalDateTime.now().plusDays(1));
        Frage f3 = new Frage("Frage 3", "Haben Sie das Gefuehl, dass ein gutes Arbeitsklima herrscht?",
                LocalDateTime.now().minusDays(1));
        Frage[] frageArr = {f1, f2, f3};
        Arrays.stream(frageArr).forEach(frageRepository::persistFrage);
    }


    @AfterEach
    void tearDown() {
        try {
            antwortRepository.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testSuccessfulAnswer() {
        Mitarbeiter mitarbeiter = mitarbeiterRepository.getMitarbeiterById(111111).get();
        Frage frage = frageRepository.getFrageById(1).get();

        Antwort antwort = new Antwort(Antwortmoeglichkeiten.VolleZustimmung, LocalDateTime.now());
        antwort.setMitarbeiter(mitarbeiter);
        antwort.setFrage(frage);
        antwortRepository.persistAntwort(antwort);
        assertEquals(1, antwortRepository.findAll().size());
    }

    @Test
    void testAnswerQuestionAgain() {
        testSuccessfulAnswer();
        testSuccessfulAnswer();
        assertEquals(1, antwortRepository.findAll().size());
    }

    @Test
    void testAnswerExpiredQuestion() {
        Mitarbeiter mitarbeiter = mitarbeiterRepository.getMitarbeiterById(111111).get();
        Frage frage = frageRepository.getFrageById(3).get();
        assertThrows(IllegalArgumentException.class, () -> {
            Antwort antwort = new Antwort(Antwortmoeglichkeiten.VolleZustimmung, LocalDateTime.now());
            antwort.setMitarbeiter(mitarbeiter);
            antwort.setFrage(frage);
        });

    }

}