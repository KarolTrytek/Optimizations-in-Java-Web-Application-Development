package pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto;

import java.io.Serial;
import java.io.Serializable;

public record EmployerDictionaryDto(

        String employerName,

        String employerNip

)implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
