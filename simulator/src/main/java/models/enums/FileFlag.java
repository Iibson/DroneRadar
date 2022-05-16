package models.enums;

import java.util.Locale;

public enum FileFlag {
    BEG,
    UPD,
    DROP,
    EXT;

    @Override
    public String toString() {
        return this.name()
                .toUpperCase(Locale.ROOT);
    }
}
