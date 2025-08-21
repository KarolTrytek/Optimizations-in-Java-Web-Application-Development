package pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

public record ProfessionDto(

        @Schema(description = "Profession name")
        String profesionName,

        @Schema(description = "Profession Identifier")
        String professionId,

        @Schema(description = "Work experience in the profession")
        BigDecimal workExperience

) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
