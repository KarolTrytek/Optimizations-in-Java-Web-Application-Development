package pl.edu.pk.optimizationsapp.utils;

import pl.edu.pk.optimizationsapp.data.domain.ofz.TypOfertyEnum;

import java.time.LocalDate;
import java.util.List;

public class Constants{

    private Constants(){}

    public static final LocalDate DATA_PRZYJ_ZGLOSZENIA = LocalDate.of(2024, 2, 2);

    public static final List<TypOfertyEnum> TYPES1 = List.of(TypOfertyEnum.OFERTA_PRACY, TypOfertyEnum.PRAKTYKI, TypOfertyEnum.PRAKT_STUDENT);
    public static final List<TypOfertyEnum> TYPES2 = List.of(TypOfertyEnum.STAZ, TypOfertyEnum.STAZ_UP, TypOfertyEnum.SPOL_UZYTECZ);
}
