package pl.edu.pk.optimizationsapp.api.tools.scheduler;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.edu.pk.optimizationsapp.api.tools.scheduler.dto.CounterDto;
import pl.edu.pk.optimizationsapp.data.domain.adm.Counters;
import pl.edu.pk.optimizationsapp.data.domain.custion.IJobOfferCounter;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOffer;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOffer_;
import pl.edu.pk.optimizationsapp.data.domain.ofz.LanguageEnum;
import pl.edu.pk.optimizationsapp.data.domain.ofz.StatusOfertyEnum;
import pl.edu.pk.optimizationsapp.data.domain.ofz.StatusTlumaczeniaEnum;
import pl.edu.pk.optimizationsapp.data.domain.ofz.TypOfertyEnum;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.SlPlacowka;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.SlPlacowka_;
import pl.edu.pk.optimizationsapp.data.repository.CountersRepository;
import pl.edu.pk.optimizationsapp.data.repository.JobOfferRepository;
import pl.edu.pk.optimizationsapp.utils.CacheKeys;
import pl.edu.pk.optimizationsapp.utils.JPAUtils;
import pl.edu.pk.optimizationsapp.utils.NumberUtils;

import java.util.ArrayList;
import java.util.List;

import static pl.edu.pk.optimizationsapp.data.domain.slowniki.TypPlacowkiEnum.UP;

@Service
@RequiredArgsConstructor
@Slf4j
public class CounterService {

    public static final String LICZNIK_AKTYWNYCH_PROPOZYCJI = "LICZNIK_AKTYWNYCH_PROPOZYCJI_";
    public static final String LICZNIK_AKTYWNYCH_OFERT_Z_UP = "LICZNIK_AKTYWNYCH_OFERT_Z_UP_";
    public static final String LICZNIK_MIEJSC_PRACY_AKTYWNYCH_OFERT_Z_UP = "LICZNIK_MIEJSC_PRACY_AKTYWNYCH_OFERT_Z_UP_";

    private static final String LICZNIK_AKTYWNYCH_PROPOZYCJI_OPIS = "Liczba aktywnych propozycji bez tis dla języka ";
    private static final String LICZNIK_AKTYWNYCH_OFERT_Z_UP_OPIS = "Liczba aktywnych ofert pracy z urzędu pracy dla języka ";
    private static final String LICZNIK_MIEJSC_PRACY_AKTYWNYCH_OFERT_Z_UP_OPIS = "Licznik miejsc pracy dla aktywnych ofert z urzędu pracy dla języka ";

    private static final String LANGUAGE_NAME_POSTFIX = "ego";

    private final JobOfferRepository jobOfferRepository;

    private final CountersRepository countersRepository;

    public CounterDto getCountersFromSeparateQueries(LanguageEnum language) {

        var proposalsNumber = getProposalsNumber(language);

        var jobOffersNumberFromEO = getJobOffersFromEmploymentOffice(language);

        return new CounterDto(formatNumber(proposalsNumber), formatNumber(jobOffersNumberFromEO), null);
    }

    public CounterDto getCountersFromJoinedQueries(LanguageEnum language) {
        if (language.equals(LanguageEnum.PL)) {
            IJobOfferCounter iJobOfferCounter = jobOfferRepository.getJobOfferCounterForPolish();
            return new CounterDto(
                    formatNumber(iJobOfferCounter.getLicznikAktywnychPropozycjiBezTisPl()),
                    formatNumber(iJobOfferCounter.getLicznikAktywnychOfertPracyUpPl()),
                    formatNumber(iJobOfferCounter.getLicznikMiejscPracyAktywnychOfertPracyUpPl()));
        } else {
            IJobOfferCounter iJobOfferCounter = jobOfferRepository.getJobOfferCounterForLanguage(language);
            return new CounterDto(
                    formatNumber(iJobOfferCounter.getLicznikAktywnychPropozycjiBezTis()),
                    formatNumber(iJobOfferCounter.getLicznikAktywnychOfertPracyUp()),
                    formatNumber(iJobOfferCounter.getLicznikMiejscPracyAktywnychOfertPracyUp()));
        }
    }

    public void calculateCounters() {

        IJobOfferCounter iJobOfferCounter = jobOfferRepository.getJobOfferCounter();

        for (LanguageEnum language : LanguageEnum.values()) {
            switch (language) {
                case PL -> createAndSaveCountersForPolish(iJobOfferCounter, language);
                case BE -> createAndSaveCountersForBelarusian(iJobOfferCounter, language);
                case UK -> createAndSaveCountersForUkrainian(iJobOfferCounter, language);
                case RU -> createAndSaveCountersForRussian(iJobOfferCounter, language);
                case EN -> createAndSaveCountersForEnglish(iJobOfferCounter, language);
            }
        }

    }

//    @Transactional
    public CounterDto getCountersWithSchedlock(LanguageEnum language) {
        var proposalNumber = countersRepository.findById(LICZNIK_AKTYWNYCH_PROPOZYCJI + language.name());

        if(proposalNumber.isEmpty()){
            calculateCounters();
        }

        return new CounterDto(
                formatNumber(countersRepository.findById(LICZNIK_AKTYWNYCH_PROPOZYCJI + language.name()).map(Counters::getValue).orElse(0L)),
                formatNumber(countersRepository.findById(LICZNIK_AKTYWNYCH_OFERT_Z_UP + language.name()).map(Counters::getValue).orElse(0L)),
                formatNumber(countersRepository.findById(LICZNIK_MIEJSC_PRACY_AKTYWNYCH_OFERT_Z_UP + language.name()).map(Counters::getValue).orElse(0L)));
    }

    @Cacheable(cacheNames = CacheKeys.OFFERS_COUNTERS)
    public CounterDto getCountersWithSchedlockFromCache(LanguageEnum language) {
        return getCountersWithSchedlock(language);
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

    private void createAndSaveCountersForRussian(IJobOfferCounter iJobOfferCounter, LanguageEnum language) {
        Counters licznikAktywnychPozycji = countersRepository.findById(LICZNIK_AKTYWNYCH_PROPOZYCJI + language.name())
                .orElse(new Counters(LICZNIK_AKTYWNYCH_PROPOZYCJI + language.name(), LICZNIK_AKTYWNYCH_PROPOZYCJI_OPIS + language.getName() + LANGUAGE_NAME_POSTFIX, iJobOfferCounter.getLicznikAktywnychPropozycjiBezTisRu()));
        licznikAktywnychPozycji.setValue(iJobOfferCounter.getLicznikAktywnychPropozycjiBezTisRu());
        countersRepository.save(licznikAktywnychPozycji);

        Counters licznikAktywnychOfertUp = countersRepository.findById(LICZNIK_AKTYWNYCH_OFERT_Z_UP + language.name())
                .orElse(new Counters(LICZNIK_AKTYWNYCH_OFERT_Z_UP + language.name(), LICZNIK_AKTYWNYCH_OFERT_Z_UP_OPIS + language.getName() + LANGUAGE_NAME_POSTFIX, iJobOfferCounter.getLicznikAktywnychOfertPracyUpRu()));
        licznikAktywnychOfertUp.setValue(iJobOfferCounter.getLicznikAktywnychOfertPracyUpRu());
        countersRepository.save(licznikAktywnychOfertUp);

        Counters licznikMiejscPracyAktywnychOfertUp = countersRepository.findById(LICZNIK_MIEJSC_PRACY_AKTYWNYCH_OFERT_Z_UP + language.name())
                .orElse(new Counters(LICZNIK_MIEJSC_PRACY_AKTYWNYCH_OFERT_Z_UP + language.name(), LICZNIK_MIEJSC_PRACY_AKTYWNYCH_OFERT_Z_UP_OPIS + language.getName() + LANGUAGE_NAME_POSTFIX, iJobOfferCounter.getLicznikMiejscPracyAktywnychOfertPracyUpRu()));
        licznikMiejscPracyAktywnychOfertUp.setValue(iJobOfferCounter.getLicznikMiejscPracyAktywnychOfertPracyUpRu());
        countersRepository.save(licznikMiejscPracyAktywnychOfertUp);
    }

    private void createAndSaveCountersForUkrainian(IJobOfferCounter iLicznikOfert, LanguageEnum language) {
        Counters licznikAktywnychPozycji = countersRepository.findById(LICZNIK_AKTYWNYCH_PROPOZYCJI + language.name())
                .orElse(new Counters(LICZNIK_AKTYWNYCH_PROPOZYCJI + language.name(), LICZNIK_AKTYWNYCH_PROPOZYCJI_OPIS + language.getName() + LANGUAGE_NAME_POSTFIX, iLicznikOfert.getLicznikAktywnychPropozycjiBezTisUa()));
        licznikAktywnychPozycji.setValue(iLicznikOfert.getLicznikAktywnychPropozycjiBezTisUa());
        countersRepository.save(licznikAktywnychPozycji);

        Counters licznikAktywnychOfertUp = countersRepository.findById(LICZNIK_AKTYWNYCH_OFERT_Z_UP + language.name())
                .orElse(new Counters(LICZNIK_AKTYWNYCH_OFERT_Z_UP + language.name(), LICZNIK_AKTYWNYCH_OFERT_Z_UP_OPIS + language.getName() + LANGUAGE_NAME_POSTFIX, iLicznikOfert.getLicznikAktywnychOfertPracyUpUa()));
        licznikAktywnychOfertUp.setValue(iLicznikOfert.getLicznikAktywnychOfertPracyUpUa());
        countersRepository.save(licznikAktywnychOfertUp);

        Counters licznikMiejscPracyAktywnychOfertUp = countersRepository.findById(LICZNIK_MIEJSC_PRACY_AKTYWNYCH_OFERT_Z_UP + language.name())
                .orElse(new Counters(LICZNIK_MIEJSC_PRACY_AKTYWNYCH_OFERT_Z_UP + language.name(), LICZNIK_MIEJSC_PRACY_AKTYWNYCH_OFERT_Z_UP_OPIS + language.getName() + LANGUAGE_NAME_POSTFIX, iLicznikOfert.getLicznikMiejscPracyAktywnychOfertPracyUpUa()));
        licznikMiejscPracyAktywnychOfertUp.setValue(iLicznikOfert.getLicznikMiejscPracyAktywnychOfertPracyUpUa());
        countersRepository.save(licznikMiejscPracyAktywnychOfertUp);
    }

    private void createAndSaveCountersForBelarusian(IJobOfferCounter iLicznikOfert, LanguageEnum language) {
        Counters licznikAktywnychPozycji = countersRepository.findById(LICZNIK_AKTYWNYCH_PROPOZYCJI + language.name())
                .orElse(new Counters(LICZNIK_AKTYWNYCH_PROPOZYCJI + language.name(), LICZNIK_AKTYWNYCH_PROPOZYCJI_OPIS + language.getName() + LANGUAGE_NAME_POSTFIX, iLicznikOfert.getLicznikAktywnychPropozycjiBezTisBy()));
        licznikAktywnychPozycji.setValue(iLicznikOfert.getLicznikAktywnychPropozycjiBezTisBy());
        countersRepository.save(licznikAktywnychPozycji);

        Counters licznikAktywnychOfertUp = countersRepository.findById(LICZNIK_AKTYWNYCH_OFERT_Z_UP + language.name())
                .orElse(new Counters(LICZNIK_AKTYWNYCH_OFERT_Z_UP + language.name(), LICZNIK_AKTYWNYCH_OFERT_Z_UP_OPIS + language.getName() + LANGUAGE_NAME_POSTFIX, iLicznikOfert.getLicznikAktywnychOfertPracyUpBy()));
        licznikAktywnychOfertUp.setValue(iLicznikOfert.getLicznikAktywnychOfertPracyUpBy());
        countersRepository.save(licznikAktywnychOfertUp);

        Counters licznikMiejscPracyAktywnychOfertUp = countersRepository.findById(LICZNIK_MIEJSC_PRACY_AKTYWNYCH_OFERT_Z_UP + language.name())
                .orElse(new Counters(LICZNIK_MIEJSC_PRACY_AKTYWNYCH_OFERT_Z_UP + language.name(), LICZNIK_MIEJSC_PRACY_AKTYWNYCH_OFERT_Z_UP_OPIS + language.getName() + LANGUAGE_NAME_POSTFIX, iLicznikOfert.getLicznikMiejscPracyAktywnychOfertPracyUpBy()));
        licznikMiejscPracyAktywnychOfertUp.setValue(iLicznikOfert.getLicznikMiejscPracyAktywnychOfertPracyUpBy());
        countersRepository.save(licznikMiejscPracyAktywnychOfertUp);
    }

    private void createAndSaveCountersForEnglish(IJobOfferCounter iLicznikOfert, LanguageEnum language) {
        Counters licznikAktywnychPozycji = countersRepository.findById(LICZNIK_AKTYWNYCH_PROPOZYCJI + language.name())
                .orElse(new Counters(LICZNIK_AKTYWNYCH_PROPOZYCJI + language.name(), LICZNIK_AKTYWNYCH_PROPOZYCJI_OPIS + language.getName() + LANGUAGE_NAME_POSTFIX, iLicznikOfert.getLicznikAktywnychPropozycjiBezTisEn()));
        licznikAktywnychPozycji.setValue(iLicznikOfert.getLicznikAktywnychPropozycjiBezTisEn());
        countersRepository.save(licznikAktywnychPozycji);

        Counters licznikAktywnychOfertUp = countersRepository.findById(LICZNIK_AKTYWNYCH_OFERT_Z_UP + language.name())
                .orElse(new Counters(LICZNIK_AKTYWNYCH_OFERT_Z_UP + language.name(), LICZNIK_AKTYWNYCH_OFERT_Z_UP_OPIS + language.getName() + LANGUAGE_NAME_POSTFIX, iLicznikOfert.getLicznikAktywnychOfertPracyUpEn()));
        licznikAktywnychOfertUp.setValue(iLicznikOfert.getLicznikAktywnychOfertPracyUpEn());
        countersRepository.save(licznikAktywnychOfertUp);

        Counters licznikMiejscPracyAktywnychOfertUp = countersRepository.findById(LICZNIK_MIEJSC_PRACY_AKTYWNYCH_OFERT_Z_UP + language.name())
                .orElse(new Counters(LICZNIK_MIEJSC_PRACY_AKTYWNYCH_OFERT_Z_UP + language.name(), LICZNIK_MIEJSC_PRACY_AKTYWNYCH_OFERT_Z_UP_OPIS + language.getName() + LANGUAGE_NAME_POSTFIX, iLicznikOfert.getLicznikMiejscPracyAktywnychOfertPracyUpEn()));
        licznikMiejscPracyAktywnychOfertUp.setValue(iLicznikOfert.getLicznikMiejscPracyAktywnychOfertPracyUpEn());
        countersRepository.save(licznikMiejscPracyAktywnychOfertUp);
    }

    private void createAndSaveCountersForPolish(IJobOfferCounter iLicznikOfert, LanguageEnum language) {
        Counters licznikAktywnychPozycji = countersRepository.findById(LICZNIK_AKTYWNYCH_PROPOZYCJI + language.name())
                .orElse(new Counters(LICZNIK_AKTYWNYCH_PROPOZYCJI + language.name(), LICZNIK_AKTYWNYCH_PROPOZYCJI_OPIS + language.getName() + LANGUAGE_NAME_POSTFIX, iLicznikOfert.getLicznikAktywnychPropozycjiBezTisPl()));
        licznikAktywnychPozycji.setValue(iLicznikOfert.getLicznikAktywnychPropozycjiBezTisPl());
        countersRepository.save(licznikAktywnychPozycji);

        Counters licznikAktywnychOfertUp = countersRepository.findById(LICZNIK_AKTYWNYCH_OFERT_Z_UP + language.name())
                .orElse(new Counters(LICZNIK_AKTYWNYCH_OFERT_Z_UP + language.name(), LICZNIK_AKTYWNYCH_OFERT_Z_UP_OPIS + language.getName() + LANGUAGE_NAME_POSTFIX, iLicznikOfert.getLicznikAktywnychOfertPracyUpPl()));
        licznikAktywnychOfertUp.setValue(iLicznikOfert.getLicznikAktywnychOfertPracyUpPl());
        countersRepository.save(licznikAktywnychOfertUp);

        Counters licznikMiejscPracyAktywnychOfertUp = countersRepository.findById(LICZNIK_MIEJSC_PRACY_AKTYWNYCH_OFERT_Z_UP + language.name())
                .orElse(new Counters(LICZNIK_MIEJSC_PRACY_AKTYWNYCH_OFERT_Z_UP + language.name(), LICZNIK_MIEJSC_PRACY_AKTYWNYCH_OFERT_Z_UP_OPIS + language.getName() + LANGUAGE_NAME_POSTFIX, iLicznikOfert.getLicznikMiejscPracyAktywnychOfertPracyUpPl()));
        licznikMiejscPracyAktywnychOfertUp.setValue(iLicznikOfert.getLicznikMiejscPracyAktywnychOfertPracyUpPl());
        countersRepository.save(licznikMiejscPracyAktywnychOfertUp);
    }

    private static String formatNumber(long number) {
        return NumberUtils.NUMBER_FORMATTER.format(number);
    }

}
