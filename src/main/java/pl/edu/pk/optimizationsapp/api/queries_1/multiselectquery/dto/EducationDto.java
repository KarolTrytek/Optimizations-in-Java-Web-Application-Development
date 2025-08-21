package pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;

public record EducationDto(
        @Schema(description = "Education level enumerator", example = "WY")
        @NotNull
        EducationLevelEnum educationLevel,

        @Schema(description = "Education level name", example = "Wyższe(W tym Licencjat)")
        String educationLevelName,

        @Schema(description = "Education type identifier", example = "RPd052|13")
        String typeId,

        @Schema(description = "Education type name", example = "gastronomiczne")
        String typeName

) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
