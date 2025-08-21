package pl.edu.pk.optimizationsapp.api.tools_3.mapping_3_1.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pk.optimizationsapp.api.tools_3.mapping_3_1.dto.JobOfferDetails;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOffer;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobOfferStreamMapper {

    public List<JobOfferDetails> map(List<JobOffer> jobOffers) {
        return jobOffers.stream()
                .map(entity -> {
                    JobOfferDetails dto = new JobOfferDetails();
                    // dane podstawowe
                    dto.getDanePodstawowe().setId(entity.getHash());
                    dto.getDanePodstawowe().setIdOfertyPl(entity.getIdJobOfferPl() == null ? null : entity.getIdJobOfferPl().getHash());
                    dto.getDanePodstawowe().setDbId(entity.getId());
                    dto.getDanePodstawowe().setStanowisko(entity.getJobTitle());
                    dto.getDanePodstawowe().setNumer(entity.getNrOferty());
                    dto.getDanePodstawowe().setNumerWEures(entity.getNumerWEures());

                    // dane pozostałe
                    dto.getDanePozostale().setDlaOsobZarej(entity.getSubsydiowana());
                    dto.getDanePozostale().setLiczbaWolnychMiejsc(entity.getTotalJobPositions());
                    dto.getDanePozostale().setLiczbaWolnychMiejscDlaNiepeln(entity.getDisabledJobPositions());
                    dto.getDanePozostale().setNiepelnosprawni(entity.getNiepelnosprawni());
                    dto.getDanePozostale().setWykorzystanieOferty(entity.getBrakMozlWykorzyst());

                    // pracodawca
                    dto.getPracodawca().setNrTelefonu(entity.getTelefonOsobyZgl());
                    dto.getPracodawca().setEmail(entity.getEmailOsobyZgl());
                    dto.getPracodawca().setOsobaDoKontaktu(entity.getImieNazwiskoOsobyZgl());
                    dto.getPracodawca().setSposobPrzekazaniaDok(entity.getSposPrzekDokum());
                    dto.getPracodawca().setWymaganeDokumetny(entity.getWymagDokum());
                    dto.getPracodawca().setSposobAplikowaniaEnum(entity.getSposobAplikowania());
                    dto.getPracodawca().setOpisDzialanosciGospodarczej(entity.getEmployer().getProfilDzialalnosci());
                    dto.getPracodawca().setDodatkoweInfDotAplikowania(entity.getDodInfoAplikowanie());

                    // urząd
//                    dto.getUrzad().setId(entity.getIdPlacowkiZasilajacej());
//                    dto.getUrzad().setKod(entity.getIdPlacowkiZasilajacej().getKodPlacowki());
//                    dto.getUrzad().setTyp(entity.getIdPlacowkiZasilajacej().getTypPodmiotu().getNazwa());

                    // warunki
                    dto.getWarunki().setZakresObowiazkow(entity.getZakresObowiazkow());
                    dto.getWarunki().setPracaTymczasowa(entity.getPracaTymczasowa());
                    dto.getWarunki().setZawodId(entity.getKodZawodu().getId());
                    dto.getWarunki().setOpisWynagr(entity.getOpisWynagr());
                    dto.getWarunki().setMiejscowoscId(entity.getWorkplaceAddress().getCityDict().getId());
                    dto.getWarunki().setLiczbaGodzinWTygodniu(entity.getIlGodzTydz());
                    dto.getWarunki().setLiczbaGodzinWMiesiacu(entity.getIlGodzMies());
                    dto.getWarunki().setZapewnienieWyzywienia(entity.getWyzywienie());
                    dto.getWarunki().setZapewnienieZakwaterowania(entity.getZakwaterowanie());
                    dto.getWarunki().setZatrudnienieOdZaraz(entity.getZatrOdZaraz());
                    dto.getWarunki().setPokrywaKosztPrzejDoPolski(entity.getKosztPrzejDoPolski());
                    dto.getWarunki().setDodatkoweSwiadczenia(entity.getDodSwiadczenia());
                    dto.getWarunki().setInneInformacje(entity.getDodInfoCzasPracy());
                    dto.getWarunki().setPremie(entity.getPremie());

                    // licznik ofert
                    dto.setLicznikOferty(entity.getJobOfferCounter().getCounter());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<JobOfferDetails> mapParallel(List<JobOffer> jobOffers) {
        return jobOffers.parallelStream()
                .map(entity -> {
                    JobOfferDetails dto = new JobOfferDetails();
                    // dane podstawowe
                    dto.getDanePodstawowe().setId(entity.getHash());
                    dto.getDanePodstawowe().setIdOfertyPl(entity.getIdJobOfferPl() == null ? null : entity.getIdJobOfferPl().getHash());
                    dto.getDanePodstawowe().setDbId(entity.getId());
                    dto.getDanePodstawowe().setStanowisko(entity.getJobTitle());
                    dto.getDanePodstawowe().setNumer(entity.getNrOferty());
                    dto.getDanePodstawowe().setNumerWEures(entity.getNumerWEures());

                    // dane pozostałe
                    dto.getDanePozostale().setDlaOsobZarej(entity.getSubsydiowana());
                    dto.getDanePozostale().setLiczbaWolnychMiejsc(entity.getTotalJobPositions());
                    dto.getDanePozostale().setLiczbaWolnychMiejscDlaNiepeln(entity.getDisabledJobPositions());
                    dto.getDanePozostale().setNiepelnosprawni(entity.getNiepelnosprawni());
                    dto.getDanePozostale().setWykorzystanieOferty(entity.getBrakMozlWykorzyst());

                    // pracodawca
                    dto.getPracodawca().setNrTelefonu(entity.getTelefonOsobyZgl());
                    dto.getPracodawca().setEmail(entity.getEmailOsobyZgl());
                    dto.getPracodawca().setOsobaDoKontaktu(entity.getImieNazwiskoOsobyZgl());
                    dto.getPracodawca().setSposobPrzekazaniaDok(entity.getSposPrzekDokum());
                    dto.getPracodawca().setWymaganeDokumetny(entity.getWymagDokum());
                    dto.getPracodawca().setSposobAplikowaniaEnum(entity.getSposobAplikowania());
                    dto.getPracodawca().setOpisDzialanosciGospodarczej(entity.getEmployer().getProfilDzialalnosci());
                    dto.getPracodawca().setDodatkoweInfDotAplikowania(entity.getDodInfoAplikowanie());

                    // urząd
//                    dto.getUrzad().setId(entity.getIdPlacowkiZasilajacej());
//                    dto.getUrzad().setKod(entity.getIdPlacowkiZasilajacej().getKodPlacowki());
//                    dto.getUrzad().setTyp(entity.getIdPlacowkiZasilajacej().getTypPodmiotu().getNazwa());

                    // warunki
                    dto.getWarunki().setZakresObowiazkow(entity.getZakresObowiazkow());
                    dto.getWarunki().setPracaTymczasowa(entity.getPracaTymczasowa());
                    dto.getWarunki().setZawodId(entity.getKodZawodu().getId());
                    dto.getWarunki().setOpisWynagr(entity.getOpisWynagr());
                    dto.getWarunki().setMiejscowoscId(entity.getWorkplaceAddress().getCityDict().getId());
                    dto.getWarunki().setLiczbaGodzinWTygodniu(entity.getIlGodzTydz());
                    dto.getWarunki().setLiczbaGodzinWMiesiacu(entity.getIlGodzMies());
                    dto.getWarunki().setZapewnienieWyzywienia(entity.getWyzywienie());
                    dto.getWarunki().setZapewnienieZakwaterowania(entity.getZakwaterowanie());
                    dto.getWarunki().setZatrudnienieOdZaraz(entity.getZatrOdZaraz());
                    dto.getWarunki().setPokrywaKosztPrzejDoPolski(entity.getKosztPrzejDoPolski());
                    dto.getWarunki().setDodatkoweSwiadczenia(entity.getDodSwiadczenia());
                    dto.getWarunki().setInneInformacje(entity.getDodInfoCzasPracy());
                    dto.getWarunki().setPremie(entity.getPremie());

                    // licznik ofert
                    dto.setLicznikOferty(entity.getJobOfferCounter().getCounter());
                    return dto;
                })
                .collect(Collectors.toList());
    }

}
