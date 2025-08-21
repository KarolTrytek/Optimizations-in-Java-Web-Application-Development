package pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.JobOfferListDto;
import pl.edu.pk.optimizationsapp.data.domain.ofz.Addresses;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOffer;

import java.math.BigDecimal;
import java.util.List;

import static pl.edu.pk.optimizationsapp.utils.MapUtils.getGoogleMapsUrl;
import static pl.edu.pk.optimizationsapp.utils.MapUtils.getOpenStreetMapUrl;
import static pl.edu.pk.optimizationsapp.utils.NumberUtils.format;

@Mapper(componentModel = "spring", uses = {
        JobOfferTupleMapper.class
})
public interface JobOfferListMapper {

    @Mapping(target = "id", source = "hash")
    @Mapping(target = "dbId", source = "id")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "typOferty", expression = "java(jobOffer.getJobType().getNazwa())")
    @Mapping(target = "stanowisko", source = "jobTitle")
    @Mapping(target = "zakresObowiazkow", source = "zakresObowiazkow")
    @Mapping(target = "miejscePracy", expression = "java(mapujAdres(jobOffer.getWorkplaceAddress()))")
    @Mapping(target = "cityId", expression = "java(jobOffer.getWorkplaceAddress() != null && jobOffer.getWorkplaceAddress().getCityDict() != null ? jobOffer.getWorkplaceAddress().getCityDict().getId().toString() : null)")
    @Mapping(target = "miejscowoscNazwa", expression = "java(jobOffer.getWorkplaceAddress() != null ? jobOffer.getWorkplaceAddress().getNazwaMiejscZagr() : null)")
    @Mapping(target = "dodanePrzez", expression = "java(jobOffer.getUnitDict().getTypPlacowki().getNazwaSkrocona())")
    @Mapping(target = "sposobAplikowania", source = "sposobAplikowania")
    @Mapping(target = "pracodawca", expression = "java(mapEmployer(jobOffer.getEmployer().getNazwa(), jobOffer.getPublishEmployerData()))")
    @Mapping(target = "pracodawcaAdres", expression = "java(mapujAdresPracodawcy(jobOffer.getApplicationAddress(), jobOffer.getAdresAplikowaniaOpis()))")
    @Mapping(target = "osobaDoKontaktu", source = "imieNazwiskoOsobyZgl")
    @Mapping(target = "rodzajUmowy", expression = "java(jobOffer.getEmploymentTypeCode() == null ? null : pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.RodzajUmowyEnum.getById(jobOffer.getEmploymentTypeCode().getId()).getNazwa())")
    @Mapping(target = "dataWaznOd", source = "dataWaznOd")
    @Mapping(target = "dataWaznDo", source = "dataWaznDo")
    @Mapping(target = "dataRozpoczecia", source = "dataPoczZatrud")
    @Mapping(target = "wymiarZatrud", expression = "java(mapujWymiarEtatu(jobOffer.getWymiarZatrud()))")
    @Mapping(target = "placowkaOpis", expression = "java(jobOffer.getUnitDict() != null ? jobOffer.getUnitDict().getOpisPlacowki() : null)")
    @Mapping(target = "dateAddedToSystem", expression = "java(jobOffer.getOfertaMetadane() != null ? jobOffer.getOfertaMetadane().getSystemSubmissionDate() : null)")
    @Mapping(target = "wynagrodzenie", expression = "java(mapujWynagrodzenie(jobOffer))")
    @Mapping(target = "telefon", source = "telefonOsobyZgl")
    @Mapping(target = "email", source = "emailOsobyZgl")
    @Mapping(target = "niepelnosprawni", source = "niepelnosprawni")
    @Mapping(target = "dlaOsobZarej", source = "subsydiowana")
    @Mapping(target = "typPropozycji", expression = "java(mapujTypPropozycji(jobOffer))")
    @Mapping(target = "mapaGoogleUrl", expression = "java(mapujMiejscePracyMapaGoogle(jobOffer.getWorkplaceAddress(), jobOffer.getPublishEmployerData()))")
    @Mapping(target = "mapaOsmUrl", expression = "java(mapujMiejscePracyMapaOsm(jobOffer.getWorkplaceAddress(), jobOffer.getPublishEmployerData()))")
    @Mapping(target = "wymagania", expression = "java(JobOfferTupleMapper.mapRequirements(jobOffer))")
    JobOfferListDto toDto(JobOffer jobOffer);

    List<JobOfferListDto> toDtoList(List<JobOffer> offers);

    default String mapujAdres(Addresses addresses) {
        if ("RPd016|134".equals(addresses.getCountryCode().getId())) {
            return addresses.getCityDict().getNazwaMiejsc() + ", " + addresses.getCityDict().getVoivodeshipDict().getName();
        } else {
            return addresses.getNazwaMiejscZagr() + ", " + addresses.getNazwaKraju();
        }
    }

    default String mapEmployer(String employerName, boolean shouldPublishEmployerData) {
        if (shouldPublishEmployerData) {
            return employerName;
        } else {
//            TypZrodla typZrodla = TypZrodla.dajTypZrodla(typPodmiotu, pup, wup);
//            return OfertaSzczegolyMapperV3.zwrocKontaktPrzez(typZrodla, properties);
            return "";
        }
    }

    default String mapujAdresPracodawcy(Addresses applicationAddress,String adresAplikowaniaOpis) {
        if (applicationAddress == null) {
            return adresAplikowaniaOpis;
        }
        return mapujAdres(applicationAddress);
    }

    default String mapujWymiarEtatu(BigDecimal wymiar) {
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
                return "nieDotyczy";
//                return properties.getProperty("cbop.i18n.oferta.lista.ofertaLista.nieDotyczy");
            } else {
                return wymiar.stripTrailingZeros().toString();
            }
        }
        return "nieDotyczy";
//        return properties.getProperty("cbop.i18n.oferta.lista.ofertaLista.nieDotyczy");
    }

    default String mapujWynagrodzenie(JobOffer jobOffer) {
        var wynagrodzenieNaGodzine = jobOffer.getSalarySystemCode() != null && (jobOffer.getSalarySystemCode().getKodElem().equals("2") || jobOffer.getSalarySystemCode().getKodElem().equals("4")) ?
//                " " + properties.getProperty("cbop.i18n.oferta.szczegoly.ofertaSzczegoly.naGodz") : "";
                " " + "naGodz" : "";
        var opisWynagrodzeniaString = jobOffer.getOpisWynagr() == null ? "" : " " + jobOffer.getOpisWynagr();
        var idWaluta = jobOffer.getCurrencyDict() == null ? null : jobOffer.getCurrencyDict().getKodElem();

        return format(jobOffer.getSalaryFrom(), jobOffer.getSalaryTo(), idWaluta, jobOffer.getJobType().name(), jobOffer.getKodJezyka())
                + wynagrodzenieNaGodzine + opisWynagrodzeniaString;
    }

    default String mapujTypPropozycji(JobOffer jobOffer) {
        StringBuilder sb = new StringBuilder();

        if (jobOffer.getPracaStala()) {
            sb.append("praca Stała");
//            sb.append(properties
//                    .getProperty("cbop.i18n.szczegolyOfertyMapper.pracaStala"));
        }

        if (jobOffer.getPracaWolneDni() != null && jobOffer.getPracaWolneDni()) {
            if (!sb.isEmpty()) {
                sb.append(", ");
            }
//            sb.append(properties
//                    .getProperty("cbop.i18n.szczegolyOfertyMapper.pracaWDniWolne"));

            sb.append("praca W Dni Wolne");
        }

        if (jobOffer.getEuresKrajowa()) {
            if (!sb.isEmpty()) {
                sb.append(", ");
                sb.append(System.lineSeparator());
            }

            sb.append("pracodawca Szczegolnie Zainteresowany");


//            sb.append(properties
//                    .getProperty("cbop.i18n.szczegolyOfertyMapper.pracodawcaSzczegolnieZainteresowany"));
        }
        return sb.toString();
    }

    default String mapujMiejscePracyMapaGoogle(Addresses addresses, boolean czyPublikowacDanePracodawcy) {
        var miejscowosc = addresses.getCityDict() == null ? addresses.getNazwaMiejscZagr() : addresses.getCityDict().getNazwaMiejsc();
        var municipality = addresses.getCityDict() == null ? "" : addresses.getCityDict().getMunicipalityDict().getNazwaGminy();
        var county = addresses.getCityDict() == null ? "" : addresses.getCityDict().getCountyDict().getName();
        var voivodeship = addresses.getCityDict() == null ? "" : addresses.getCityDict().getVoivodeshipDict().getName();
        return czyPublikowacDanePracodawcy ?
                getGoogleMapsUrl(miejscowosc, municipality, county, voivodeship, addresses.getUlica(), addresses.getNrDomu(), addresses.getNazwaKraju()) :
                getGoogleMapsUrl(miejscowosc, municipality, county, voivodeship, null, null, addresses.getNazwaKraju());
    }

    default String mapujMiejscePracyMapaOsm(Addresses addresses, boolean czyPublikowacDanePracodawcy) {
        var miejscowosc = addresses.getCityDict() == null ? addresses.getNazwaMiejscZagr() : addresses.getCityDict().getNazwaMiejsc();
        var municipality = addresses.getCityDict() == null ? "" : addresses.getCityDict().getMunicipalityDict().getNazwaGminy();
        var county = addresses.getCityDict() == null ? "" : addresses.getCityDict().getCountyDict().getName();
        var voivodeship = addresses.getCityDict() == null ? "" : addresses.getCityDict().getVoivodeshipDict().getName();
        return czyPublikowacDanePracodawcy ?
                getOpenStreetMapUrl(miejscowosc, voivodeship, addresses.getUlica(), addresses.getNrDomu(), addresses.getNazwaKraju()) :
                getOpenStreetMapUrl(miejscowosc, voivodeship, null, null, addresses.getNazwaKraju());
    }

}
