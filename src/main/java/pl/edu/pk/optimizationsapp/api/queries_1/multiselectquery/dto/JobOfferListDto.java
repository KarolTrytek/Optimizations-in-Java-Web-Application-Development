package pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOfferStatusEnum;
import pl.edu.pk.optimizationsapp.data.domain.ofz.SposobAplikowaniaEnum;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class JobOfferListDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Long dbId;

    @Schema(description = "Identifier")
    private String id;

    @Schema(description = "Status")
    private JobOfferStatusEnum status;

    @Schema(description = "Stanowisko", example = "Spawacz", maxLength = 256)
    private String stanowisko;

    @Schema(description = "Miejsce pracy")
    private String miejscePracy;

    @Schema(description = "Identyfikator miejscowosci, gdy null - city zagraniczna")
    private String cityId;

    @Schema(description = "Nazwa miejscowosci")
    private String miejscowoscNazwa;

    @Schema(description = "Sposob aplikowania")
    private SposobAplikowaniaEnum sposobAplikowania;

    @Schema(description = "Nazwa pracodawcy")
    private String pracodawca;

    @Schema(description = "Adres pracodawcy")
    private String pracodawcaAdres;

    @Schema(description = "Osoba do kontaktu")
    private String osobaDoKontaktu;

    @Schema(description = "Typ oferty", maxLength = 15)
    private String typOferty;

    @Schema(description = "Rodzaj umowy (słownik RPs005)", maxLength = 56)
    private String rodzajUmowy;

    @Schema(description = "Data dodania/dostepne od")
    private LocalDate dataWaznOd;

    @Schema(description = "Data dostepne do")
    private LocalDate dataWaznDo;

    @Schema(description = "Data rozpoczecia")
    private LocalDate dataRozpoczecia;

    @Schema(description = "Wymiar zatrudnienia")
    private String wymiarZatrud;

    @Schema(description = "Opis placowki")
    private String placowkaOpis;

    @Schema(description = "Date added to system")
    private LocalDateTime dateAddedToSystem;

    @Schema(description = "Wynagrodzenie")
    private String wynagrodzenie;

    @Schema(description = "Zakres obowiazkow")
    private String zakresObowiazkow;

    @Schema(description = "Wymagania")
    private String wymagania;

    @Schema(description = "Stopien dopasowania")
    private String stopienDopasowania;

    @Schema(description = "Mapa Google miejsca Pracy")
    private String mapaGoogleUrl;

    @Schema(description = "Mapa Open Street Map miejsca Pracy")
    private String mapaOsmUrl;

    @Schema(description = "Telefon")
    private String telefon;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "Liczba wolnych miejsc dla osób niepełnosprawnych")
    private Integer disabledJobPositions;

    @Schema(description = "Czy oferta dla osób niepełnosprawnych")
    private boolean niepelnosprawni;

    @Schema(description = "Czy oferta dla osoby zarejestrowanej")
    private boolean dlaOsobZarej;

    @Schema(description = "Typ propozycji")
    private String typPropozycji;

    @Schema(description = "Oferta dodana przez")
    private String dodanePrzez;

    @Schema(description = "Nazwa pola do sortowania po popularnosci")
    private Integer popularnosc;

    /**
     * Alias encji PRACODAWCA.
     */
    public static final String PRACODAWCA_ALIAS = "pracodawca";

    /**
     * Alias encji WYNAGRODZENIE.
     */
    public static final String SALARY_ALIAS = "salary";

    /**
     * Alias encji RODZAJ_UMOWY.
     */
    public static final String RODZAJ_UMOWY = "rodzajUmowy";

    /**
     * Alias encji MIEJSCE_PRACY.
     */
    public static final String MIEJSCE_PRACY = "miejscePracy";

    /**
     * Alias encji STANOWISKO.
     */
    public static final String STANOWISKO_ALIAS = "stanowisko";
    /**
     * Alias encji DATA_WAZNOSCI.
     */
    public static final String DATA_WAZNOSCI = "dataWaznDo";

    /**
     * Alias encji POPULARNOSC.
     */
    public static final String POPULARNOSC_ALIAS = "popularnosc";

    /**
     * Alias pola sortujacego STOPIEN_DOPASOWANIA.
     */
    public static final String STOPIEN_DOPASOWANIA = "stopienDopasowania";

    /**
     * Alias pola sortujacego DODANE_PRZEZ.
     */
    public static final String DODANE_PRZEZ = "dodanePrzez";

}

