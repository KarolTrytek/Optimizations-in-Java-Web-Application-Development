package pl.edu.pk.optimizationsapp.api.database_3_3.indexes_1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;

public record DictionaryDto(

        @Schema(description = "Dictionary code", example = "000", maxLength = 50)
        @NotNull
        String code,

        @Schema(description = "Description of dictionary value", example = "Description of value 000", maxLength = 600)
        String description
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}

