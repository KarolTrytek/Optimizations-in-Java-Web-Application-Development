package pl.edu.pk.optimizationsapp.data.domain.ofz;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OcenaPowodowNieprzyjeciaKandydatowEnum {

    U("uzasadnione"),
    N("nieuzasadnione");

    private final String opis;

}
