package pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery;

import jakarta.persistence.Tuple;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.JobOfferListDto;
import pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.MappingTypeEnum;
import pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.RodzajUmowyEnum;
import pl.edu.pk.optimizationsapp.data.domain.ofz.Addresses_;
import pl.edu.pk.optimizationsapp.data.domain.ofz.Employers_;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOffer;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOfferMetadata_;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOfferStatusEnum;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOffer_;
import pl.edu.pk.optimizationsapp.data.domain.ofz.SposobAplikowaniaEnum;
import pl.edu.pk.optimizationsapp.data.domain.ofz.TypOfertyEnum;
import pl.edu.pk.optimizationsapp.data.domain.ofz.WymaganiaJezyk;
import pl.edu.pk.optimizationsapp.data.domain.ofz.WymaganiaUmiejetnosci;
import pl.edu.pk.optimizationsapp.data.domain.ofz.WymaganiaUprawnienia;
import pl.edu.pk.optimizationsapp.data.domain.ofz.WymaganiaWyksztalcenie;
import pl.edu.pk.optimizationsapp.data.domain.ofz.WymaganiaZawod;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.CityDict_;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.CountyDict_;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.MunicipalityDict_;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.TypPlacowkiEnum;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.UnitDict_;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.VoivodeshipDict_;
import pl.edu.pk.optimizationsapp.utils.JezykPropertiesHelper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static pl.edu.pk.optimizationsapp.utils.Aliases.KOD_KRAJU_ADRES_APLIKOWANIA_ID_ALIAS;
import static pl.edu.pk.optimizationsapp.utils.Aliases.KOD_KRAJU_ADRES_APLIKOWANIA_OPIS_ALIAS;
import static pl.edu.pk.optimizationsapp.utils.Aliases.KOD_KRAJU_ID_ALIAS;
import static pl.edu.pk.optimizationsapp.utils.Aliases.KOD_KRAJU_OPIS_ALIAS;
import static pl.edu.pk.optimizationsapp.utils.Aliases.KOD_RODZAJU_ZATRUDNIENIA_ID_ALIAS;
import static pl.edu.pk.optimizationsapp.utils.Aliases.MIEJSCOWOSC_MIEJSCA_PRACY_ID_ALIAS;
import static pl.edu.pk.optimizationsapp.utils.Aliases.NAZWA_MIEJSC_ADRES_APLIKOWANIA_ALIAS;
import static pl.edu.pk.optimizationsapp.utils.Aliases.NAZWA_MIEJSC_ZAGR_ADRES_APLIKOWANIA_ALIAS;
import static pl.edu.pk.optimizationsapp.utils.Aliases.NAZWA_WOJEW_ADRES_APLIKOWANIA_ALIAS;
import static pl.edu.pk.optimizationsapp.utils.Aliases.VOIVODESHIP_NAME_ALIAS;
import static pl.edu.pk.optimizationsapp.utils.Aliases.WALUTA_KOD_ALIAS;
import static pl.edu.pk.optimizationsapp.utils.MapUtils.getGoogleMapsUrl;
import static pl.edu.pk.optimizationsapp.utils.MapUtils.getOpenStreetMapUrl;
import static pl.edu.pk.optimizationsapp.utils.NumberUtils.format;

/**
 * Interfejs mapera DTO dla encji @{@link pl.edu.pk.optimizationsapp.data.domain.ofz.JobOffer}
 */
@Mapper(componentModel = "spring")
public interface JobOfferTupleMapper {

    String STAZ_PRACY_OGOLEM = "staż pracy ogółem - ";
    String UPRAWNIENIE = "uprawnienie - ";
    String WYMAGANY_STAZ = " (wymagany staż - ";
    String TYP_WYKSZTALCENIA_BRAK_ID_POZYCJI = "RPd052|00";
    String TYP_WYKSZTALCENIA_INNE_ID_POZYCJI = "RPd052|11";
    String WYMAGANY_STAZ_PROPERTY_PATH = "cbop.i18n.szczegolyOfertyMapper.wymaganyStaz";

    JezykPropertiesHelper helper = new JezykPropertiesHelper();

    default List<JobOfferListDto> fromTuples(List<Tuple> tupleList, MappingTypeEnum parallelMapping) {
        List<JobOfferListDto> list = new ArrayList<>(tupleList.size());

        if(parallelMapping.equals(MappingTypeEnum.PARALLEL_STREAM)) {
            tupleList.parallelStream()
                    .map(this::fromTuple)
                    .forEach(list::add);
        } else if(parallelMapping.equals(MappingTypeEnum.STREAM)){
            tupleList.stream()
                    .map(this::fromTuple)
                    .forEach(list::add);
        } else {
            for (Tuple t : tupleList) {
                list.add(fromTuple(t));
            }
        }
        return list;
    }

    default JobOfferListDto fromTuple(Tuple t) {
        var properties = helper.getProperties((String) t.get(JobOffer_.KOD_JEZYKA));
        return JobOfferListDto.builder()
                .id((String) t.get(JobOffer_.HASH))
                .dbId((Long) t.get(JobOffer_.ID))
                .status((JobOfferStatusEnum) t.get(JobOffer_.STATUS))
                .typOferty(mapujTypOferty((TypOfertyEnum) t.get(JobOffer_.JOB_TYPE)))
                .stanowisko((String) t.get(JobOffer_.JOB_TITLE))
                .zakresObowiazkow((String) t.get(JobOffer_.ZAKRES_OBOWIAZKOW))
                .miejscePracy(mapujAdres(
                        (String) t.get(KOD_KRAJU_ID_ALIAS),
                        (String) t.get(KOD_KRAJU_OPIS_ALIAS),
                        (String) t.get(CityDict_.NAZWA_MIEJSC),
                        (String) t.get(Addresses_.NAZWA_MIEJSC_ZAGR),
                        (String) t.get(VOIVODESHIP_NAME_ALIAS)))
                .cityId((String) t.get(MIEJSCOWOSC_MIEJSCA_PRACY_ID_ALIAS))
                .miejscowoscNazwa((String) t.get(Addresses_.NAZWA_MIEJSC_ZAGR))
                .dodanePrzez(mapujTypPodmiotu((TypPlacowkiEnum) t.get(UnitDict_.TYP_PLACOWKI)))
                .sposobAplikowania((SposobAplikowaniaEnum) t.get(JobOffer_.SPOSOB_APLIKOWANIA))
                .pracodawca((mapujPracodawce(
                        (String) t.get(Employers_.NAZWA),
                        (Boolean) t.get(JobOffer_.PUBLISH_EMPLOYER_DATA),
                        (TypPlacowkiEnum) t.get(UnitDict_.TYP_PLACOWKI),
                        (Boolean) t.get(UnitDict_.PUP),
                        (Boolean) t.get(UnitDict_.WUP),
                        properties
                )))
                .pracodawcaAdres(mapujAdresPracodawcy(
                        (Long) t.get(JobOffer_.APPLICATION_ADDRESS),
                        (String) t.get(KOD_KRAJU_ADRES_APLIKOWANIA_ID_ALIAS),
                        (String) t.get(KOD_KRAJU_ADRES_APLIKOWANIA_OPIS_ALIAS),
                        (String) t.get(NAZWA_MIEJSC_ADRES_APLIKOWANIA_ALIAS),
                        (String) t.get(NAZWA_MIEJSC_ZAGR_ADRES_APLIKOWANIA_ALIAS),
                        (String) t.get(NAZWA_WOJEW_ADRES_APLIKOWANIA_ALIAS),
                        (String) t.get(JobOffer_.ADRES_APLIKOWANIA_OPIS)))
                .osobaDoKontaktu((String) t.get(JobOffer_.IMIE_NAZWISKO_OSOBY_ZGL))
                .rodzajUmowy(mapujRodzajUmowy((String) t.get(KOD_RODZAJU_ZATRUDNIENIA_ID_ALIAS), properties))
                .dataWaznOd((LocalDate) t.get(JobOffer_.DATA_WAZN_OD))
                .dataWaznDo((LocalDate) t.get(JobOffer_.DATA_WAZN_DO))
                .dataRozpoczecia((LocalDate) t.get(JobOffer_.DATA_POCZ_ZATRUD))
                .wymiarZatrud(mapujWymiarEtatu((BigDecimal) t.get(JobOffer_.WYMIAR_ZATRUD), properties))
                .placowkaOpis((String) t.get(JobOffer_.UNIT_DICT))
                .dateAddedToSystem((LocalDateTime) t.get(JobOfferMetadata_.SYSTEM_SUBMISSION_DATE))
                .wynagrodzenie(mapujWynagrodzenie(
                        (BigDecimal) t.get(JobOffer_.SALARY_FROM),
                        (BigDecimal) t.get(JobOffer_.SALARY_TO),
                        (String) t.get(WALUTA_KOD_ALIAS),
                        (TypOfertyEnum) t.get(JobOffer_.JOB_TYPE),
                        (String) t.get(JobOffer_.KOD_JEZYKA),
                        (String) t.get(JobOffer_.SALARY_SYSTEM_CODE),
                        (String) t.get(JobOffer_.OPIS_WYNAGR),
                        properties))
                .telefon((String) t.get(JobOffer_.TELEFON_OSOBY_ZGL))
                .email((String) t.get(JobOffer_.EMAIL_OSOBY_ZGL))
                .disabledJobPositions((Integer) t.get(JobOffer_.DISABLED_JOB_POSITIONS))
                .niepelnosprawni((Boolean) t.get(JobOffer_.NIEPELNOSPRAWNI))
                .dlaOsobZarej((Boolean) t.get(JobOffer_.SUBSYDIOWANA))
                .typPropozycji(mapujTypPropozycji(
                        t.get(JobOffer_.PRACA_STALA) != null && (Boolean) t.get(JobOffer_.PRACA_STALA),
                        t.get(JobOffer_.PRACA_WOLNE_DNI) != null && (Boolean) t.get(JobOffer_.PRACA_WOLNE_DNI),
                        t.get(JobOffer_.EURES_KRAJOWA) != null && (Boolean) t.get(JobOffer_.EURES_KRAJOWA),
                        properties))
                .mapaGoogleUrl(mapujMiejscePracyMapaGoogle(
                        (String) t.get(CityDict_.NAZWA_MIEJSC),
                        (String) t.get(Addresses_.NAZWA_MIEJSC_ZAGR),
                        (String) t.get(MunicipalityDict_.NAZWA_GMINY),
                        (String) t.get(CountyDict_.NAME),
                        (String) t.get(VoivodeshipDict_.NAME),
                        (String) t.get(Addresses_.ULICA),
                        (String) t.get(Addresses_.NR_DOMU),
                        (String) t.get(KOD_KRAJU_OPIS_ALIAS),
                        (Boolean) t.get(JobOffer_.PUBLISH_EMPLOYER_DATA)))
                .mapaOsmUrl(mapujMiejscePracyMapaOsm(
                        (String) t.get(CityDict_.NAZWA_MIEJSC),
                        (String) t.get(Addresses_.NAZWA_MIEJSC_ZAGR),
                        (String) t.get(VoivodeshipDict_.NAME),
                        (String) t.get(Addresses_.ULICA),
                        (String) t.get(Addresses_.NR_DOMU),
                        (String) t.get(KOD_KRAJU_OPIS_ALIAS),
                        (Boolean) t.get(JobOffer_.PUBLISH_EMPLOYER_DATA)))
                .build();
    }

    static String mapRequirements(JobOffer offer) {
        return String.valueOf(mapujWyksztalcenie(offer)) +
                mapujZawod(offer) +
                mapujJezyki(offer) +
                mapujUprawnienia(offer) +
                mapujUmiejetnosci(offer) +
                (StringUtils.isNotBlank(offer.getInneWymag()) ? offer.getInneWymag() + "\n" : "") +
                (offer.getStazWymagOgol() != null && !offer.getStazWymagOgol().equals(BigDecimal.valueOf(0.00)) ? STAZ_PRACY_OGOLEM + getStazBuilder(offer.getStazWymagOgol()) : "");
    }

    private static StringBuilder mapujUprawnienia(JobOffer oferta) {
        StringBuilder uprawieniaSB = new StringBuilder();

        for (WymaganiaUprawnienia wp : oferta.getWymaganeUprawnienia()) {
            if (wp.getUprawnienie() != null) {
                uprawieniaSB.append(UPRAWNIENIE).append(wp.getUprawnienie());
            } else {
                uprawieniaSB.append(UPRAWNIENIE).append(wp.getKodUprawnienia().getOpisElem());
            }
            if (wp.getStazWymagania() != null && !wp.getStazWymagania().equals(BigDecimal.valueOf(0.00))) {
                uprawieniaSB.append(WYMAGANY_STAZ).append(getStazBuilder(wp.getStazWymagania())).append(")");
            }
            uprawieniaSB.append("\n");
        }
        return uprawieniaSB;
    }

    private static StringBuilder mapujJezyki(JobOffer oferta) {

        StringBuilder jezykiSB = new StringBuilder();
        for (WymaganiaJezyk wj : oferta.getWymaganeJezyki()) {
            jezykiSB.append("język obcy - ").append(wj.getIdJezykaObcego().getOpisElem());
            jezykiSB.append(", w mowie: ").append(wj.getKodStopZnajWmowie().getOpisElem());
            jezykiSB.append(", w piśmie: ").append(wj.getKodStopZnajWpismie().getOpisElem());
            jezykiSB.append("\n");

        }
        return jezykiSB;
    }

    private static StringBuilder mapujWyksztalcenie(JobOffer oferta) {
        StringBuilder wyksztalcenieSB = new StringBuilder();

        for (WymaganiaWyksztalcenie ww : oferta.getWymaganeWyksztalcenie()) {
            wyksztalcenieSB.append("wykształcenie - ");
            if (!ww.getKodTypuWykszt().getId().equals(TYP_WYKSZTALCENIA_BRAK_ID_POZYCJI) && !ww.getKodTypuWykszt().getId().equals(TYP_WYKSZTALCENIA_INNE_ID_POZYCJI)) {
                wyksztalcenieSB.append(ww.getKodTypuWykszt().getOpisElem());
            }
            wyksztalcenieSB.append(" ").append(ww.getKodPozWykszt().getOpisElem());
            if (StringUtils.isNotBlank(ww.getKierunekWykszt())) {
                wyksztalcenieSB.append(", kierunek: ").append(ww.getKierunekWykszt());
            }
            if (StringUtils.isNotBlank(ww.getSpecZawodowaWykszt())) {
                wyksztalcenieSB.append(", specjalizacja: ").append(ww.getSpecZawodowaWykszt());
            }
            wyksztalcenieSB.append("\n");
        }

        return wyksztalcenieSB;
    }

    private static StringBuilder mapujZawod(JobOffer oferta) {
        StringBuilder zawodSB = new StringBuilder();

        for (WymaganiaZawod wz : oferta.getWymaganyZawod()) {
            zawodSB.append("zawód - ").append(wz.getKodZawodu().getOpisElem());

            if (wz.getStazWymagania() != null && !wz.getStazWymagania().equals(BigDecimal.valueOf(0.00))) {
                zawodSB.append(WYMAGANY_STAZ).append(getStazBuilder(wz.getStazWymagania())).append(")");
            }

            zawodSB.append("\n");
        }

        if (oferta.getWymaganyZawod().stream().noneMatch(wz -> wz.getKodZawodu().getId().equals(oferta.getKodZawodu().getId()))) {
            zawodSB.append("zawód - ").append(oferta.getKodZawodu().getOpisElem()).append("\n");
        }

        return zawodSB;
    }

    private static StringBuilder mapujUmiejetnosci(JobOffer oferta) {
        StringBuilder umiejetnosciSB = new StringBuilder();

        for (WymaganiaUmiejetnosci wu : oferta.getWymaganeUmiejetnosci()) {
            umiejetnosciSB.append("umiejetności - ").append(wu.getUmiejetnosc());

            if (wu.getStazWymagania() != null && !wu.getStazWymagania().equals(BigDecimal.valueOf(0.00))) {
                umiejetnosciSB.append(WYMAGANY_STAZ).append(getStazBuilder(wu.getStazWymagania())).append(")");
            }

            umiejetnosciSB.append("\n");
        }

        return umiejetnosciSB;
    }

    private static String getStazBuilder(BigDecimal staz) {
        StringBuilder sb = new StringBuilder();
        String[] stazDetail = staz.toString().split("\\.");
        int decimal = Integer.parseInt(stazDetail[0]);
        int fractional = Integer.parseInt(stazDetail[1]);
        sb.append("lata: ").append(decimal);
        if (fractional > 0) {
            sb.append(", miesiące: ").append(fractional);
        }
        return sb.toString();
    }

    default String mapujTypPodmiotu(TypPlacowkiEnum typPodmiotuEnum) {
        return typPodmiotuEnum.getNazwaSkrocona();
    }

    default String mapujWymiarEtatu(BigDecimal wymiar, Properties properties) {
        if (wymiar != null) {
            if (wymiar.compareTo(new BigDecimal("0.250")) == 0) {
                return "1/4";
            } else if (wymiar.compareTo(new BigDecimal("0.500")) == 0) {
                return "1/2";
            } else if (wymiar.compareTo(new BigDecimal("0.600")) == 0) {
                return "3/5";
            } else if (wymiar.compareTo(new BigDecimal("0.750")) == 0) {
                return "3/4";
            } else if (wymiar.compareTo(new BigDecimal("0.800")) == 0) {
                return "4/5";
            } else if (wymiar.compareTo(new BigDecimal("0.875")) == 0) {
                return "7/8";
            } else if (wymiar.compareTo(new BigDecimal("0.900")) == 0) {
                return "9/10";
            } else if (wymiar.compareTo(new BigDecimal("1.000")) == 0) {
                return "1";
            } else if (wymiar.compareTo(new BigDecimal("0")) == 0) {
                return properties.getProperty("cbop.i18n.oferta.lista.ofertaLista.nieDotyczy");
            } else {
                return wymiar.stripTrailingZeros().toString();
            }
        }
        return properties.getProperty("cbop.i18n.oferta.lista.ofertaLista.nieDotyczy");
    }


    default String mapujTypOferty(TypOfertyEnum rodzajPropozycjiEnum) {
        return rodzajPropozycjiEnum.getNazwa();
    }

    default String mapujMiejscePracyMapaGoogle(String miejscowosc, String miejscowoscZagraniczna, String gmina, String powiat, String wojewodztwo, String ulica, String nrBudynku,
                                               String nazwaKraju, boolean czyPublikowacDanePracodawcy) {
        miejscowosc = miejscowosc == null ? miejscowoscZagraniczna : miejscowosc;
        return czyPublikowacDanePracodawcy ?
                getGoogleMapsUrl(miejscowosc, gmina, powiat, wojewodztwo, ulica, nrBudynku, nazwaKraju) :
                getGoogleMapsUrl(miejscowosc, gmina, powiat, wojewodztwo, null, null, nazwaKraju);
    }

    default String mapujMiejscePracyMapaOsm(String miejscowosc, String miejscowoscZagraniczna, String wojewodztwo, String ulica, String nrBudynku,
                                            String nazwaKraju, boolean czyPublikowacDanePracodawcy) {
        miejscowosc = miejscowosc == null ? miejscowoscZagraniczna : miejscowosc;
        return czyPublikowacDanePracodawcy ?
                getOpenStreetMapUrl(miejscowosc, wojewodztwo, ulica, nrBudynku, nazwaKraju) :
                getOpenStreetMapUrl(miejscowosc, wojewodztwo, null, null, nazwaKraju);
    }

    default String mapujPracodawce(String nazwaPracodawcy, boolean czyPublikowacDanePracodawcy,
                                   TypPlacowkiEnum typPodmiotu, boolean pup, boolean wup, Properties properties) {
        if (czyPublikowacDanePracodawcy) {
            return nazwaPracodawcy;
        } else {
//            TypZrodla typZrodla = TypZrodla.dajTypZrodla(typPodmiotu, pup, wup);
//            return OfertaSzczegolyMapperV3.zwrocKontaktPrzez(typZrodla, properties);
            return "";
        }
    }

    default String mapujRodzajUmowy(String kodRodzajuZatrudnenia, Properties properties) {
        return kodRodzajuZatrudnenia == null ? null : properties.getProperty(RodzajUmowyEnum.getById(kodRodzajuZatrudnenia).getNazwa());
    }

    default String mapujAdres(String kodKrajuId, String krajZagraniczny, String miejscowoscPolska, String miejscowoscZagraniczna, String wojewodztwo) {
        if ("RPd016|134".equals(kodKrajuId)) {
            return miejscowoscPolska + ", " + wojewodztwo;
        } else {
            return miejscowoscZagraniczna + ", " + krajZagraniczny;
        }
    }

    default String mapujWynagrodzenie(BigDecimal wynagrodzenieOd, BigDecimal wynagrodzenieDo, String waluta, TypOfertyEnum typOferty,
                                      String kodJezyka, String kodSystemRodzajuWynagrodzenia, String opisWynagrdzenia, Properties properties) {
        var wynagrodzenieNaGodzine = kodSystemRodzajuWynagrodzenia != null && (kodSystemRodzajuWynagrodzenia.equals("2") || kodSystemRodzajuWynagrodzenia.equals("4")) ?
                " " + properties.getProperty("cbop.i18n.oferta.szczegoly.ofertaSzczegoly.naGodz") : "";
        var opisWynagrodzeniaString = opisWynagrdzenia == null ? "" : " " + opisWynagrdzenia;

        return format(wynagrodzenieOd, wynagrodzenieDo, waluta, typOferty.name(), kodJezyka)
                + wynagrodzenieNaGodzine + opisWynagrodzeniaString;
    }


    default String mapujTypPropozycji(boolean pracaStala, boolean pracaWolneDni, boolean euresKrajowa, Properties properties) {
        StringBuilder sb = new StringBuilder();

        if (pracaStala) {
            sb.append(properties
                    .getProperty("cbop.i18n.szczegolyOfertyMapper.pracaStala"));
        }

        if (pracaWolneDni) {
            if (!sb.isEmpty()) {
                sb.append(", ");
            }
            sb.append(properties
                    .getProperty("cbop.i18n.szczegolyOfertyMapper.pracaWDniWolne"));
        }

        if (euresKrajowa) {
            if (!sb.isEmpty()) {
                sb.append(", ");
                sb.append(System.lineSeparator());
            }

            sb.append(properties
                    .getProperty("cbop.i18n.szczegolyOfertyMapper.pracodawcaSzczegolnieZainteresowany"));
        }
        return sb.toString();
    }

    default String mapujAdresPracodawcy(Long adresAplikowaniaId, String kodKrajuId, String krajZagraniczny, String miejscowoscPolska, String miejscowoscZagraniczna, String wojewodztwo, String adresAplikowaniaOpis) {
        if (adresAplikowaniaId == null) {
            return adresAplikowaniaOpis;
        }
        return mapujAdres(kodKrajuId, krajZagraniczny, miejscowoscPolska, miejscowoscZagraniczna, wojewodztwo);
    }


}