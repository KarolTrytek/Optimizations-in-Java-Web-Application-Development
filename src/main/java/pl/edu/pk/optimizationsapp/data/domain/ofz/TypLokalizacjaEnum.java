package pl.edu.pk.optimizationsapp.data.domain.ofz;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypLokalizacjaEnum {

    B("[cbop.i18n.typLokalizacja.brakDanych]"),
    S("[cbop.i18n.typLokalizacja.siedzibaGlowna]"),
    F("[cbop.i18n.typLokalizacja.filia]"),
    O("[cbop.i18n.typLokalizacja.miejsceOddelegowania]"),
    I("[cbop.i18n.typLokalizacja.inna]"),
    U("[cbop.i18n.typLokalizacja.siedzibaPracodawcyDocelowego]");

    private final String opis;

}
