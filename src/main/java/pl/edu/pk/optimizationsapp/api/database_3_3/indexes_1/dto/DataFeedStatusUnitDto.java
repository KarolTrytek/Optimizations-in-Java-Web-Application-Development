package pl.edu.pk.optimizationsapp.api.database_3_3.indexes_1.dto;

import java.sql.Timestamp;

public record DataFeedStatusUnitDto(

        String unitCode,
        String unitNameShort,
        String unitType,
        Timestamp lastJobOffersMessage,
        Timestamp lastEventsMessage

) {
}
