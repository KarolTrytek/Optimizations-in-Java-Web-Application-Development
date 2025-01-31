package pl.edu.pk.optimizationsapp.api.tools.scheduler;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pk.optimizationsapp.api.tools.scheduler.dto.CounterDto;
import pl.edu.pk.optimizationsapp.data.domain.custion.IJobOfferCounter;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOffer;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOffer_;
import pl.edu.pk.optimizationsapp.data.domain.ofz.LanguageEnum;
import pl.edu.pk.optimizationsapp.data.domain.ofz.StatusOfertyEnum;
import pl.edu.pk.optimizationsapp.data.domain.ofz.StatusTlumaczeniaEnum;
import pl.edu.pk.optimizationsapp.data.domain.ofz.TypOfertyEnum;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.SlPlacowka;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.SlPlacowka_;
import pl.edu.pk.optimizationsapp.data.repository.JobOfferRepository;
import pl.edu.pk.optimizationsapp.utils.JPAUtils;
import pl.edu.pk.optimizationsapp.utils.NumberUtils;

import java.util.ArrayList;
import java.util.List;

import static pl.edu.pk.optimizationsapp.data.domain.slowniki.TypPlacowkiEnum.UP;

@Service
@RequiredArgsConstructor
@Slf4j
public class CounterService {

    private final JobOfferRepository jobOfferRepository;

    public CounterDto getCountersFromSeparateQueries(LanguageEnum language) {

        var proposalsNumber = getProposalsNumber(language);

        var jobOffersNumberFromEO = getJobOffersFromEmploymentOffice(language);

//        var eventsNumber =

        return new CounterDto(formatNumber(proposalsNumber),formatNumber(jobOffersNumberFromEO), null, null);
    }

    public CounterDto getCountersFromJoinedQueries(LanguageEnum language) {
        if(language.equals(LanguageEnum.PL)){
            IJobOfferCounter iJobOfferCounter = jobOfferRepository.getJobOfferCounterForPolish();
            return new CounterDto(
                    formatNumber(iJobOfferCounter.getLicznikAktywnychPropozycjiBezTisPl()),
                    formatNumber(iJobOfferCounter.getLicznikAktywnychOfertPracyUpPl()),
                    null,
                    formatNumber(iJobOfferCounter.getLicznikMiejscPracyAktywnychOfertPracyUpPl()));
        } else {
            IJobOfferCounter iJobOfferCounter = jobOfferRepository.getJobOfferCounterForLanguage(language);
            return new CounterDto(
                    formatNumber(iJobOfferCounter.getLicznikAktywnychPropozycjiBezTis()),
                    formatNumber(iJobOfferCounter.getLicznikAktywnychOfertPracyUp()),
                    null,
                    formatNumber(iJobOfferCounter.getLicznikMiejscPracyAktywnychOfertPracyUp()));
        }
    }

    private long getProposalsNumber(LanguageEnum language) {
        return jobOfferRepository.count((root, query, cb) -> {
            List<Predicate> predicatesList = new ArrayList<>();

            JPAUtils.setEqual(root, cb, predicatesList, JobOffer_.STATUS, StatusOfertyEnum.AKTYWNE.getCode());
            JPAUtils.setNotEqual(root, cb, predicatesList, JobOffer_.TYP_OFERTY, TypOfertyEnum.SPOL_UZYTECZ);
            JPAUtils.setEqual(root, cb, predicatesList, JobOffer_.KOD_JEZYKA, language.getId());

            if (language != LanguageEnum.PL) {
                JPAUtils.setEqual(root, cb, predicatesList, JobOffer_.STATUS_TLUMACZENIA, StatusTlumaczeniaEnum.P);
            }

            predicatesList.add(
                    cb.or(
                            cb.or(
                                    cb.isNull(root.get(JobOffer_.TIS_TYP_INF_STAROSTY)),
                                    cb.isFalse(root.get(JobOffer_.TIS_TYP_INF_STAROSTY))),
                            cb.isTrue(root.get(JobOffer_.TIS_SKIER_KAND_ZGODA))));

            return cb.and(predicatesList.toArray(new Predicate[0]));
        });
    }

    private long getJobOffersFromEmploymentOffice(LanguageEnum jezyk) {
        return jobOfferRepository.count((root, query, cb) -> {
            List<Predicate> predicatesList = new ArrayList<>();

            JPAUtils.setEqual(root, cb, predicatesList, JobOffer_.STATUS, StatusOfertyEnum.AKTYWNE.getCode());
            JPAUtils.setEqual(root, cb, predicatesList, JobOffer_.TYP_OFERTY, TypOfertyEnum.OFERTA_PRACY);

            Join<JobOffer, SlPlacowka> placowkaJoin = root.join(JobOffer_.ID_PLACOWKI_ZASILAJACEJ, JoinType.INNER);
            JPAUtils.setEqual(placowkaJoin, cb, predicatesList, SlPlacowka_.TYP_PLACOWKI, UP);

            if (jezyk != LanguageEnum.PL) {
                JPAUtils.setEqual(root, cb, predicatesList, JobOffer_.STATUS_TLUMACZENIA, StatusTlumaczeniaEnum.P);
                JPAUtils.setEqual(root, cb, predicatesList, JobOffer_.KOD_JEZYKA, jezyk.getId());
            } else {
                JPAUtils.setEqual(root, cb, predicatesList, JobOffer_.KOD_JEZYKA, jezyk.getId());
            }

            return cb.and(predicatesList.toArray(new Predicate[0]));
        });
    }

//    private long pobierzLiczbeAktywnychWydarzen(JezykEnum jezyk) {
//        return wydarzenieRepository.count((root, query, cb) -> {
//            List<Predicate> predicatesList = new ArrayList<>();
//
//            JPAUtils.setEqual(root, cb, predicatesList, Wydarzenia_.STATUS, StatusWydarzeniaEnum.A.getKod());
//
//            if (jezyk != JezykEnum.PL) {
//                JPAUtils.setEqual(root, cb, predicatesList, Wydarzenia_.STATUS_TLUMACZENIA, StatusTlumaczeniaEnum.P.getId());
//                JPAUtils.setEqual(root, cb, predicatesList, Wydarzenia_.KOD_JEZYKA, jezyk.getId());
//            } else {
//                predicatesList.add(
//                        cb.or(
//                                cb.equal(root.get(Wydarzenia_.KOD_JEZYKA), jezyk.getId()),
//                                cb.isNull(root.get(Wydarzenia_.KOD_JEZYKA))));
//            }
//
//            return cb.and(predicatesList.toArray(new Predicate[0]));
//        });
//    }


    private static String formatNumber(long number) {
        return NumberUtils.NUMBER_FORMATTER.format(number);
    }


}
