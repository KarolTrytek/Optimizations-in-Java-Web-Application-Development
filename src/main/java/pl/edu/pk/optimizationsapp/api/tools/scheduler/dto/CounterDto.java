package pl.edu.pk.optimizationsapp.api.tools.scheduler.dto;

import java.io.Serial;
import java.io.Serializable;

public record CounterDto(

        //@Schema(description = "Ilość propozycji")
        String proposalsNumber,

        //@Schema(description = "Ilość ofert pracy")
        String jobOffersNumberFromEO,

        //@Schema(description = "Ilość miejsc pracy")
        String workPlacesNumber

) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
