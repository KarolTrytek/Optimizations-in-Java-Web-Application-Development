package pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static pl.edu.pk.optimizationsapp.utils.Aliases.BW_ID;
import static pl.edu.pk.optimizationsapp.utils.Aliases.GM_ID;
import static pl.edu.pk.optimizationsapp.utils.Aliases.LZ_ID;
import static pl.edu.pk.optimizationsapp.utils.Aliases.PO_ID;
import static pl.edu.pk.optimizationsapp.utils.Aliases.PP_ID;
import static pl.edu.pk.optimizationsapp.utils.Aliases.SB_ID;
import static pl.edu.pk.optimizationsapp.utils.Aliases.SO_ID;
import static pl.edu.pk.optimizationsapp.utils.Aliases.SZ_ID;
import static pl.edu.pk.optimizationsapp.utils.Aliases.WY_ID;
import static pl.edu.pk.optimizationsapp.utils.Aliases.ZB_ID;
import static pl.edu.pk.optimizationsapp.utils.Aliases.ZZ_ID;

@AllArgsConstructor
@Getter
public enum EducationLevelEnum {

    BW(List.of(BW_ID), "brak lub niepełne podstawowe", BW_ID),
    PO(List.of(BW_ID, PO_ID), "podstawowe", PO_ID),
    GM(List.of(BW_ID, PO_ID, GM_ID), "gimnazjalne", GM_ID),
    ZZ(List.of(BW_ID, PO_ID, GM_ID, ZZ_ID, ZB_ID), "zasadnicze zawodowe", ZZ_ID),
    ZB(List.of(BW_ID, PO_ID, GM_ID, ZZ_ID, ZB_ID), "zasadnicze branżowe", ZB_ID),
    SZ(List.of(BW_ID, PO_ID, GM_ID, ZZ_ID, ZB_ID, SB_ID, LZ_ID, SZ_ID), "średnie zawodowe", SZ_ID),
    SB(List.of(BW_ID, PO_ID, GM_ID, ZZ_ID, ZB_ID, SB_ID, LZ_ID, SZ_ID), "średnie branżowe", SB_ID),
    LZ(List.of(BW_ID, PO_ID, GM_ID, ZZ_ID, ZB_ID, SB_ID, LZ_ID, SZ_ID), "średnie zawodowe 4-letnie", LZ_ID),
    SO(List.of(BW_ID, PO_ID, GM_ID, ZZ_ID, ZB_ID, SB_ID, LZ_ID, SZ_ID, SO_ID), "średnie ogólnokształcące", SO_ID),
    PP(List.of(BW_ID, PO_ID, GM_ID, ZZ_ID, ZB_ID, SB_ID, LZ_ID, SZ_ID, SO_ID, PP_ID), "pomaturalne/policealne", PP_ID),
    WY(List.of(BW_ID, PO_ID, GM_ID, ZZ_ID, ZB_ID, SB_ID, LZ_ID, SZ_ID, SO_ID, PP_ID, WY_ID), "wyższe (w tym licencjat)", WY_ID);

    private final List<String> poziomyZawierajaceSie;

    private final String nazwa;

    private final String id;

}
