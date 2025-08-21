package pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;

/**
 * DTO dla danych o jezyku obcym.
 */
public record ForeignLanguageDto(

        @Schema(description = "Identyfikator jezyka obcego")
        @NotNull
        String jezykId,

        @Schema(description = "Nazwa jezyka obcego")
        String jezykNazwa,

        @Schema(description = "Identyfikator stopnia znajomosci w mowie", example = "B1")
        LanguageProficiencyLevelEnum stopienZnajomosciWMowie,

        @Schema(description = "Identyfikator stopnia znajomosci w pismie", example = "B1")
        LanguageProficiencyLevelEnum stopienZnajomosciWPismie

) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
