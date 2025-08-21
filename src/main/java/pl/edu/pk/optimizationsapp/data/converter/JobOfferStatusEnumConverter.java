package pl.edu.pk.optimizationsapp.data.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOfferStatusEnum;

@Converter
public class JobOfferStatusEnumConverter implements AttributeConverter<JobOfferStatusEnum, String> {

    @Override
    public String convertToDatabaseColumn(JobOfferStatusEnum statusOfertyEnum) {
        return statusOfertyEnum.getCode();
    }

    @Override
    public JobOfferStatusEnum convertToEntityAttribute(String s) {
        return JobOfferStatusEnum.parse(s);
    }
}
