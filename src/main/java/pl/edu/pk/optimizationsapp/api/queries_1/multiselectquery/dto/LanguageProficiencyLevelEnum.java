package pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static pl.edu.pk.optimizationsapp.utils.Aliases.A1_ID;
import static pl.edu.pk.optimizationsapp.utils.Aliases.A2_ID;
import static pl.edu.pk.optimizationsapp.utils.Aliases.B1_ID;
import static pl.edu.pk.optimizationsapp.utils.Aliases.B2_ID;
import static pl.edu.pk.optimizationsapp.utils.Aliases.C1_ID;
import static pl.edu.pk.optimizationsapp.utils.Aliases.C2_ID;

@AllArgsConstructor
@Getter
public enum LanguageProficiencyLevelEnum {

    A1(List.of(A1_ID), "RPs001|A1", "A1 - początkujący"),
    A2(List.of(A1_ID, A2_ID),"RPs001|A2","A2 - niższy średnio zaawansowany"),
    B1(List.of(A1_ID, A2_ID, B1_ID),"RPs001|B1","B1 - średnio zaawansowany"),
    B2(List.of(A1_ID, A2_ID, B1_ID, B2_ID),"RPs001|B2", "B2 - wyższy średnio zaawansowany"),
    C1(List.of(A1_ID, A2_ID, B1_ID, B2_ID, C1_ID), "RPs001|C1", "C1 - zaawansowany"),
    C2(List.of(A1_ID, A2_ID, B1_ID, B2_ID, C1_ID, C2_ID), "RPs001|C2", "C2 - biegły");

    private final List<String> poziomyZawierajaceSie;

    private final String id;

    private final String nazwa;

    public static LanguageProficiencyLevelEnum findById(String id) {
        return Arrays.stream(values())
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

}
