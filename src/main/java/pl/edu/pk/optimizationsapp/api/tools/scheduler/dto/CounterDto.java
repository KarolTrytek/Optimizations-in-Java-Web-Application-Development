package pl.edu.pk.optimizationsapp.api.tools.scheduler.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;

public record CounterDto(

        @Schema(description = "Number of proposal")
        String proposalsNumber,

        @Schema(description = "Number of job offers from employment office")
        String jobOffersNumberFromEO,

        @Schema(description = "Number of work places")
        String workPlacesNumber

) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
