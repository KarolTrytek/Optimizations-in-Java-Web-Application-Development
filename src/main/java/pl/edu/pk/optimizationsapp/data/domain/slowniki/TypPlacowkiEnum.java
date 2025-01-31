package pl.edu.pk.optimizationsapp.data.domain.slowniki;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypPlacowkiEnum {
    APE("Akredytowany Partner EURES", "Partnerzy EURES"),
    OHP("Ochotniczy Hufiec Pracy", "OHP"),
    UP("Powiatowy Urząd Pracy", "PUP"),
    UW("Wojewódzki Urząd Pracy", "WUP"),
    PGP("Pracodawca", "Pracodawca"),
    JAP("Jednostka Administracji Publicznej", "Administracja");

    private final String nazwa;

    private final String nazwaSkrocona;

}
