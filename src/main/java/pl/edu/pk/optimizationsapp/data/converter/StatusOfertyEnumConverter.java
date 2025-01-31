package pl.edu.pk.optimizationsapp.data.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import pl.edu.pk.optimizationsapp.data.domain.ofz.StatusOfertyEnum;

@Converter
public class StatusOfertyEnumConverter implements AttributeConverter<StatusOfertyEnum, String> {

    @Override
    public String convertToDatabaseColumn(StatusOfertyEnum statusOfertyEnum) {
        return statusOfertyEnum.getCode();
    }

    @Override
    public StatusOfertyEnum convertToEntityAttribute(String s) {
        return StatusOfertyEnum.parse(s);
    }
}
