package pl.edu.pk.optimizationsapp.data.domain.ofz;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LanguageEnum {
    PL("pl", "polski", "polska"),
    BE("by", "białoruski", "białoruska"),
    UK("ua", "ukraiński", "ukraińska"),
    RU("ru", "rosyjski", "rosyjska"),
    EN("en", "angielski", "angielska");

    private final String id;

    private final String name;

    private final String version;
}
