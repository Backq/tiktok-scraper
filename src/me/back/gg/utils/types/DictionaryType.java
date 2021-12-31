package me.back.gg.utils.types;

public enum DictionaryType {
    Complete_Dictionary(0),
    Children_Dictionary(1),
    Food(2),
    Animals(3),
    Colors(4),
    Human_Body(5),
    Education(6),
    Family(7),
    Geometric_Figures(8),
    Communication_Tools(9),
    Numbers(10),
    Numbers_0_9(11),
    Professions(12),
    Transport(13);

    public final int i;

    DictionaryType(int i) {
        this.i = i;
    }

    public int getID() { return i; }
}
