package pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;

public record LocalityRangeDto (

        @Schema(description = "City identifier")
        String cityId,

        @Schema(description = "Range in kilometers")
        Double range

) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
