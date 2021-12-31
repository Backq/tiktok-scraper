package me.back.gg.utils.types;

public enum Language {
    Italian("Nuova+parola"),
    English("New+word"),
    Spanish("Nueva+palabra"),
    French("Nouveau+mot");

    public final String mammt;

    Language(String mammt) {
        this.mammt = mammt;
    }

    public String getLanguage() { return mammt; }
}
