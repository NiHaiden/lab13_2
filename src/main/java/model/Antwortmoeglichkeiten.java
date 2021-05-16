package model;

public enum Antwortmoeglichkeiten {
    VolleZustimmung("volle Zustimmung"), TeilweiseZustimmung("teilweise Zustimmung"),
    KeineZustimmung("keine Zustimmung");

    private String antwort;

    Antwortmoeglichkeiten(String antwort) {
        this.antwort = antwort;
    }

    public String getAntwort() {
        return antwort;
    }
}