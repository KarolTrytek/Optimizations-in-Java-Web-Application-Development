package pl.edu.pk.optimizationsapp.data.domain.ofz;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusDziedzinowyEnum {
    P("w przygotowaniu"),
    R("realizowana"),
    N("nieaktywna"),
    A("anulowana"),
    O("odmowa przyjęcia"),
    Z("zakończona");

    private final String opis;

}
