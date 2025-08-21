package pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.EducationDto;
import pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.ForeignLanguageDto;
import pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.JobOfferCriteria;
import pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.JobOfferListDto;
import pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.JobOfferPage;
import pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.JobOfferSortParameters;
import pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.JobOfferSummary;
import pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.LocalityRangeDto;
import pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.MappingTypeEnum;
import pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.EmployerDictionaryDto;
import pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.ProfessionDto;
import pl.edu.pk.optimizationsapp.data.domain.ofz.Addresses;
import pl.edu.pk.optimizationsapp.data.domain.ofz.Addresses_;
import pl.edu.pk.optimizationsapp.data.domain.ofz.Employers;
import pl.edu.pk.optimizationsapp.data.domain.ofz.Employers_;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOffer;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOfferCounter;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOfferCounter_;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOfferMetadata;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOfferMetadata_;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOfferStatusEnum;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOffer_;
import pl.edu.pk.optimizationsapp.data.domain.ofz.LanguageEnum;
import pl.edu.pk.optimizationsapp.data.domain.ofz.StatusTlumaczeniaEnum;
import pl.edu.pk.optimizationsapp.data.domain.ofz.TrybyPracy;
import pl.edu.pk.optimizationsapp.data.domain.ofz.WymaganiaJezyk;
import pl.edu.pk.optimizationsapp.data.domain.ofz.WymaganiaJezyk_;
import pl.edu.pk.optimizationsapp.data.domain.ofz.WymaganiaWyksztalcenie;
import pl.edu.pk.optimizationsapp.data.domain.ofz.WymaganiaWyksztalcenie_;
import pl.edu.pk.optimizationsapp.data.domain.ofz.WymaganiaZawod;
import pl.edu.pk.optimizationsapp.data.domain.ofz.WymaganiaZawod_;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.CityDict;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.CityDict_;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.CountyDict;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.CountyDict_;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.ElementCentralDict;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.MunicipalityDict;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.MunicipalityDict_;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.SlElemSlowBase_;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.SlElemSlowSys;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.SlKodPocztowy_;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.UnitDict;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.UnitDict_;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.VoivodeshipDict;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.VoivodeshipDict_;
import pl.edu.pk.optimizationsapp.data.repository.CityRepositoryImpl;
import pl.edu.pk.optimizationsapp.data.repository.JobOfferRepository;
import pl.edu.pk.optimizationsapp.utils.CacheKeys;
import pl.edu.pk.optimizationsapp.utils.CriteriaQueryUtils;
import pl.edu.pk.optimizationsapp.utils.JPAUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.JobOfferListDto.DATA_WAZNOSCI;
import static pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.JobOfferListDto.DODANE_PRZEZ;
import static pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.JobOfferListDto.MIEJSCE_PRACY;
import static pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.JobOfferListDto.POPULARNOSC_ALIAS;
import static pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.JobOfferListDto.PRACODAWCA_ALIAS;
import static pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.JobOfferListDto.RODZAJ_UMOWY;
import static pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.JobOfferListDto.STANOWISKO_ALIAS;
import static pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.JobOfferListDto.STOPIEN_DOPASOWANIA;
import static pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.JobOfferListDto.SALARY_ALIAS;
import static pl.edu.pk.optimizationsapp.data.domain.ofz.JobOfferStatusEnum.ACTIVE;
import static pl.edu.pk.optimizationsapp.utils.Aliases.COUNT;
import static pl.edu.pk.optimizationsapp.utils.Aliases.KOD_KRAJU_ADRES_APLIKOWANIA_ID_ALIAS;
import static pl.edu.pk.optimizationsapp.utils.Aliases.KOD_KRAJU_ADRES_APLIKOWANIA_OPIS_ALIAS;
import static pl.edu.pk.optimizationsapp.utils.Aliases.KOD_KRAJU_ID_ALIAS;
import static pl.edu.pk.optimizationsapp.utils.Aliases.KOD_KRAJU_OPIS_ALIAS;
import static pl.edu.pk.optimizationsapp.utils.Aliases.KOD_RODZAJU_ZATRUDNIENIA_ID_ALIAS;
import static pl.edu.pk.optimizationsapp.utils.Aliases.KONTAKT;
import static pl.edu.pk.optimizationsapp.utils.Aliases.KRAJ_POCHODZENIA_KOD_ALIAS;
import static pl.edu.pk.optimizationsapp.utils.Aliases.MIEJSCOWOSC_MIEJSCA_PRACY_ID_ALIAS;
import static pl.edu.pk.optimizationsapp.utils.Aliases.NAZWA_MIEJSC_ADRES_APLIKOWANIA_ALIAS;
import static pl.edu.pk.optimizationsapp.utils.Aliases.NAZWA_MIEJSC_ZAGR_ADRES_APLIKOWANIA_ALIAS;
import static pl.edu.pk.optimizationsapp.utils.Aliases.NAZWA_WOJEW_ADRES_APLIKOWANIA_ALIAS;
import static pl.edu.pk.optimizationsapp.utils.Aliases.NUMBER_OF_JOB_POSITIONS;
import static pl.edu.pk.optimizationsapp.utils.Aliases.VOIVODESHIP_NAME_ALIAS;
import static pl.edu.pk.optimizationsapp.utils.Aliases.WALUTA_KOD_ALIAS;

@Service
@RequiredArgsConstructor
@Slf4j
public class MultiselectQueryService {

    private final JobOfferTupleMapper jobOfferTupleMapper;
    private final JobOfferListMapper jobOfferListMapper;

    private final CityRepositoryImpl cityRepositoryImpl;
    private final JobOfferRepository jobOfferRepository;

    private final EntityManager em;
    private CriteriaQueryUtils criteriaQueryUtils;

    /**
     * EntityManager initialization method
     */
    @PostConstruct
    public void init() {
        criteriaQueryUtils = CriteriaQueryUtils.newInstance(em);
    }

    @Cacheable(cacheNames = CacheKeys.JOB_OFFERS)
    public JobOfferPage findJobOfferMultiselectListFromCache(JobOfferCriteria criteria, Pageable pageable, boolean doCountInSQL, MappingTypeEnum parallelMapping, Boolean resolveCityRangeInMainQuery) {
        return findJobOfferMultiselectList(criteria, pageable, doCountInSQL, parallelMapping, resolveCityRangeInMainQuery);
    }

    public JobOfferPage findJobOfferMultiselectList(JobOfferCriteria criteria, Pageable pageable, boolean doCountInSQL, MappingTypeEnum parallelMapping, Boolean resolveCityRangeInMainQuery) {

        HibernateCriteriaBuilder cb = (HibernateCriteriaBuilder) criteriaQueryUtils.getEntityManager().getCriteriaBuilder();
        JpaCriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);
        Root<JobOffer> root = cq.from(JobOffer.class);

        Join<JobOffer, Addresses> workplaceJoin = root.join(JobOffer_.WORKPLACE_ADDRESS, JoinType.INNER);
        Join<Addresses, CityDict> workplaceCityJoin = workplaceJoin.join(Addresses_.CITY_DICT, JoinType.LEFT);
        Join<CityDict, VoivodeshipDict> workplaceVoivodeshipJoin = workplaceCityJoin.join(CityDict_.VOIVODESHIP_DICT, JoinType.LEFT);
        Join<CityDict, CountyDict> workplaceCountyJoin = workplaceCityJoin.join(CityDict_.COUNTY_DICT, JoinType.LEFT);
        Join<CityDict, MunicipalityDict> workplaceMunicipalityJoin = workplaceCityJoin.join(CityDict_.MUNICIPALITY_DICT, JoinType.LEFT);

        Join<JobOffer, Addresses> applicationAddressJoin = root.join(JobOffer_.APPLICATION_ADDRESS, JoinType.LEFT);
        Join<Addresses, CityDict> applicationAddressCityJoin = applicationAddressJoin.join(Addresses_.CITY_DICT, JoinType.LEFT);
        Join<CityDict, VoivodeshipDict> applicationAddressVoivodeshipJoin = applicationAddressCityJoin.join(CityDict_.VOIVODESHIP_DICT, JoinType.LEFT);
        Join<Addresses, ElementCentralDict> applicationAddressCountryJoin = applicationAddressJoin.join(Addresses_.COUNTRY_CODE, JoinType.LEFT);

        Join<JobOffer, UnitDict> unitJoin = root.join(JobOffer_.UNIT_DICT, JoinType.INNER);
        Join<JobOffer, Employers> employerJoin = root.join(JobOffer_.EMPLOYER, JoinType.INNER);

        Join<JobOffer, ElementCentralDict> currencyJoin = root.join(JobOffer_.CURRENCY_DICT, JoinType.LEFT);
        Join<JobOffer, ElementCentralDict> originCountryJoin = root.join(JobOffer_.ORIGIN_COUNTRY_CODE, JoinType.LEFT);
        Join<JobOffer, ElementCentralDict> salarySystemJoin = root.join(JobOffer_.SALARY_SYSTEM_CODE, JoinType.LEFT);
        Join<JobOffer, SlElemSlowSys> employmentTypeJoin = root.join(JobOffer_.EMPLOYMENT_TYPE_CODE, JoinType.LEFT);

        Join<JobOffer, JobOfferCounter> counterJoin = root.join(JobOffer_.JOB_OFFER_COUNTER);

        Join<JobOffer, JobOfferMetadata> jobOfferMetadataJoin = root.join(JobOffer_.OFERTA_METADANE);

        Expression<BigDecimal> salarySortExpression = cb.coalesce(
                root.get(JobOffer_.SALARY_FROM),
                cb.coalesce(root.get(JobOffer_.SALARY_TO), cb.literal(BigDecimal.ZERO)));

        Expression<Boolean> consentExpression = root.get(JobOffer_.PUBLISH_EMPLOYER_DATA);

        Expression<String> employerSortExpression = cb.selectCase()
                .when(cb.isTrue(consentExpression), employerJoin.get(Employers_.NAZWA))
                .otherwise(cb.literal(KONTAKT)).asString();

        cq.multiselect(
                root.get(JobOffer_.ID).alias(JobOffer_.ID),
                root.get(JobOffer_.HASH).alias(JobOffer_.HASH),
                root.get(JobOffer_.DATA_WAZN_OD).alias(JobOffer_.DATA_WAZN_OD),
                root.get(JobOffer_.DATA_WAZN_DO).alias(JobOffer_.DATA_WAZN_DO),
                root.get(JobOffer_.DATA_POCZ_ZATRUD).alias(JobOffer_.DATA_POCZ_ZATRUD),
                root.get(JobOffer_.STATUS).alias(JobOffer_.STATUS),
                root.get(JobOffer_.JOB_TITLE).alias(JobOffer_.JOB_TITLE),
                root.get(JobOffer_.TELEFON_OSOBY_ZGL).alias(JobOffer_.TELEFON_OSOBY_ZGL),
                root.get(JobOffer_.EMAIL_OSOBY_ZGL).alias(JobOffer_.EMAIL_OSOBY_ZGL),
                root.get(JobOffer_.KOD_JEZYKA).alias(JobOffer_.KOD_JEZYKA),
                root.get(JobOffer_.SALARY_FROM).alias(JobOffer_.SALARY_FROM),
                root.get(JobOffer_.SALARY_TO).alias(JobOffer_.SALARY_TO),
                root.get(JobOffer_.JOB_TYPE).alias(JobOffer_.JOB_TYPE),
                root.get(JobOffer_.ZAKRES_OBOWIAZKOW).alias(JobOffer_.ZAKRES_OBOWIAZKOW),
                root.get(JobOffer_.PUBLISH_EMPLOYER_DATA).alias(JobOffer_.PUBLISH_EMPLOYER_DATA),
                root.get(JobOffer_.DISABLED_JOB_POSITIONS).alias(JobOffer_.DISABLED_JOB_POSITIONS),
                root.get(JobOffer_.NIEPELNOSPRAWNI).alias(JobOffer_.NIEPELNOSPRAWNI),
                root.get(JobOffer_.SUBSYDIOWANA).alias(JobOffer_.SUBSYDIOWANA),
                root.get(JobOffer_.PRACA_STALA).alias(JobOffer_.PRACA_STALA),
                root.get(JobOffer_.PRACA_WOLNE_DNI).alias(JobOffer_.PRACA_WOLNE_DNI),
                root.get(JobOffer_.EURES_KRAJOWA).alias(JobOffer_.EURES_KRAJOWA),
                root.get(JobOffer_.EURES_ZAGRANICZNA).alias(JobOffer_.EURES_ZAGRANICZNA),
                root.get(JobOffer_.WYMIAR_ZATRUD).alias(JobOffer_.WYMIAR_ZATRUD),
                root.get(JobOffer_.OPIS_WYNAGR).alias(JobOffer_.OPIS_WYNAGR),
                root.get(JobOffer_.ADRES_APLIKOWANIA_OPIS).alias(JobOffer_.ADRES_APLIKOWANIA_OPIS),
                root.get(JobOffer_.SPOSOB_APLIKOWANIA).alias(JobOffer_.SPOSOB_APLIKOWANIA),
                root.get(JobOffer_.IMIE_NAZWISKO_OSOBY_ZGL).alias(JobOffer_.IMIE_NAZWISKO_OSOBY_ZGL),
                jobOfferMetadataJoin.get(JobOfferMetadata_.SYSTEM_SUBMISSION_DATE).alias(JobOfferMetadata_.SYSTEM_SUBMISSION_DATE),
                employerJoin.get(Employers_.NAZWA).alias(Employers_.NAZWA),
                employerJoin.get(Employers_.PRACODAWCA_ZAGRANICZNY).alias(Employers_.PRACODAWCA_ZAGRANICZNY),
                salarySystemJoin.get(SlElemSlowBase_.KOD_ELEM).alias(JobOffer_.SALARY_SYSTEM_CODE),
                employmentTypeJoin.get(SlElemSlowBase_.ID).alias(KOD_RODZAJU_ZATRUDNIENIA_ID_ALIAS),
                employmentTypeJoin.get(SlElemSlowBase_.OPIS_ELEM),
                unitJoin.get(UnitDict_.TYP_PLACOWKI).alias(UnitDict_.TYP_PLACOWKI),
                unitJoin.get(UnitDict_.OPIS_PLACOWKI).alias(JobOffer_.UNIT_DICT),
                unitJoin.get(UnitDict_.PUP).alias(UnitDict_.PUP),
                unitJoin.get(UnitDict_.WUP).alias(UnitDict_.WUP),
                workplaceJoin.get(Addresses_.COUNTRY_CODE).get(SlElemSlowBase_.ID).alias(KOD_KRAJU_ID_ALIAS),
                workplaceJoin.get(Addresses_.COUNTRY_CODE).get(SlElemSlowBase_.OPIS_ELEM).alias(KOD_KRAJU_OPIS_ALIAS),
                workplaceJoin.get(Addresses_.NAZWA_MIEJSC_ZAGR).alias(Addresses_.NAZWA_MIEJSC_ZAGR),
                workplaceJoin.get(Addresses_.ULICA).alias(Addresses_.ULICA),
                workplaceJoin.get(Addresses_.NR_DOMU).alias(Addresses_.NR_DOMU),
                workplaceVoivodeshipJoin.get(VoivodeshipDict_.NAME).alias(VOIVODESHIP_NAME_ALIAS),
                workplaceCityJoin.get(CityDict_.NAZWA_MIEJSC).alias(CityDict_.NAZWA_MIEJSC),
                workplaceCityJoin.get(CityDict_.ID).alias(MIEJSCOWOSC_MIEJSCA_PRACY_ID_ALIAS),
                workplaceCountyJoin.get(CountyDict_.NAME).alias(CountyDict_.NAME),
                workplaceMunicipalityJoin.get(MunicipalityDict_.NAZWA_GMINY).alias(MunicipalityDict_.NAZWA_GMINY),
                applicationAddressJoin.get(Addresses_.ID).alias(JobOffer_.APPLICATION_ADDRESS),
                applicationAddressJoin.get(Addresses_.NAZWA_MIEJSC_ZAGR).alias(NAZWA_MIEJSC_ZAGR_ADRES_APLIKOWANIA_ALIAS),
                applicationAddressCountryJoin.get(SlElemSlowBase_.ID).alias(KOD_KRAJU_ADRES_APLIKOWANIA_ID_ALIAS),
                applicationAddressCountryJoin.get(SlElemSlowBase_.OPIS_ELEM).alias(KOD_KRAJU_ADRES_APLIKOWANIA_OPIS_ALIAS),
                applicationAddressCityJoin.get(CityDict_.NAZWA_MIEJSC).alias(NAZWA_MIEJSC_ADRES_APLIKOWANIA_ALIAS),
                applicationAddressVoivodeshipJoin.get(VoivodeshipDict_.NAME).alias(NAZWA_WOJEW_ADRES_APLIKOWANIA_ALIAS),
                currencyJoin.get(SlElemSlowBase_.KOD_ELEM).alias(WALUTA_KOD_ALIAS),
                counterJoin.get(JobOfferCounter_.COUNTER),
                salarySortExpression,
                employerSortExpression,
                originCountryJoin.get(SlElemSlowBase_.KOD_ELEM).alias(KRAJ_POCHODZENIA_KOD_ALIAS)
        );

        JobOfferSortParameters sortParameters = new JobOfferSortParameters(
                workplaceCityJoin,
                workplaceJoin,
                originCountryJoin,
                unitJoin,
                employmentTypeJoin,
                salarySortExpression,
                employerSortExpression,
                counterJoin
        );

        List<Order> orders = mapSort(cb, root, pageable, sortParameters);

        Predicate predicates = getFilteredJobOffer(criteria, root, cq, cb, workplaceJoin, applicationAddressJoin, employerJoin, workplaceCityJoin, jobOfferMetadataJoin, resolveCityRangeInMainQuery);

        List<Tuple> list = criteriaQueryUtils.getResultList(cq, orders, predicates, pageable);

        if (list.isEmpty()) {
            return new JobOfferPage(0, new PageImpl<>(Collections.emptyList(), pageable, 0));
        }

        var countingTime = System.currentTimeMillis();
        log.debug("count job offer and number of positions in {} start", doCountInSQL ? "SQL" : "java");
        JobOfferSummary summary = countSummary(root, cq, cb, predicates, doCountInSQL);
        log.debug("count job offer and number of positions stop {} ms", System.currentTimeMillis() - countingTime);

        var mappingTime = System.currentTimeMillis();
        log.debug("mapping by {} start", parallelMapping.name());
        List<JobOfferListDto> resultList = jobOfferTupleMapper.fromTuples(list, parallelMapping);
        log.debug("mapping stop {} ms", System.currentTimeMillis() - mappingTime);

        List<JobOfferListDto> resultListWithRequirementsMapped = mapRequirements(resultList,
                criteria.getJobOfferStatusList().equals(List.of(ACTIVE)) ?
                        jobOfferRepository.findByIdInAndStatus(resultList.stream().map(JobOfferListDto::getDbId).toList(), ACTIVE)
                        : jobOfferRepository.findByIdIn(resultList.stream().map(JobOfferListDto::getDbId).toList()));

        return new JobOfferPage(summary.numberOfJobPositions(), new PageImpl<>(resultListWithRequirementsMapped, pageable, summary.count()));
    }

    private JobOfferSummary countSummary(Root<JobOffer> root, JpaCriteriaQuery<Tuple> cq, HibernateCriteriaBuilder cb, Predicate predicates, boolean doCountInSQL) {
        Long count;
        int numberOfJobPositions;

        if (doCountInSQL) {
            cq.multiselect(
                            cb.count(root.get(JobOffer_.ID)).alias(COUNT),
                            cb.sum(root.get(JobOffer_.TOTAL_JOB_POSITIONS)).alias(NUMBER_OF_JOB_POSITIONS)
                    ).where(predicates)
                    .orderBy(Collections.emptyList());

            Tuple row = em.createQuery(cq).getSingleResult();

            count = (Long) row.get(COUNT);
            numberOfJobPositions = (int) row.get(NUMBER_OF_JOB_POSITIONS);

        } else {
            var distinctJobOfferWithNumberOfJobPositions = em.createQuery(cq
                    .distinct(true)
                    .multiselect(
                            root.get(JobOffer_.ID),
                            root.get(JobOffer_.TOTAL_JOB_POSITIONS).alias(JobOffer_.TOTAL_JOB_POSITIONS))
                    .where(predicates)
                    .orderBy(Collections.emptyList())).getResultList();

            count = (long) distinctJobOfferWithNumberOfJobPositions.size();
            numberOfJobPositions = distinctJobOfferWithNumberOfJobPositions.stream().mapToInt(o -> (int) o.get(JobOffer_.TOTAL_JOB_POSITIONS)).sum();
        }
        return new JobOfferSummary(count, numberOfJobPositions);
    }

    public JobOfferPage findJobOfferList(JobOfferCriteria criteria, Pageable pageable, boolean doCountInSQL, MappingTypeEnum parallelMapping, Boolean resolveCityRangeInMainQuery) {

        HibernateCriteriaBuilder cb = (HibernateCriteriaBuilder) criteriaQueryUtils.getEntityManager().getCriteriaBuilder();
        JpaCriteriaQuery<JobOffer> cq = cb.createQuery(JobOffer.class);
        Root<JobOffer> root = cq.from(JobOffer.class);

        Join<JobOffer, Addresses> workplaceJoin = root.join(JobOffer_.WORKPLACE_ADDRESS, JoinType.INNER);
        Join<Addresses, CityDict> workplaceCityJoin = workplaceJoin.join(Addresses_.CITY_DICT, JoinType.LEFT);

        Join<JobOffer, Addresses> applicationAddressJoin = root.join(JobOffer_.APPLICATION_ADDRESS, JoinType.LEFT);

        Join<JobOffer, UnitDict> unitJoin = root.join(JobOffer_.UNIT_DICT, JoinType.INNER);
        Join<JobOffer, Employers> employerJoin = root.join(JobOffer_.EMPLOYER, JoinType.INNER);

        Join<JobOffer, ElementCentralDict> originCountryJoin = root.join(JobOffer_.ORIGIN_COUNTRY_CODE, JoinType.LEFT);
        Join<JobOffer, SlElemSlowSys> employmentTypeJoin = root.join(JobOffer_.EMPLOYMENT_TYPE_CODE, JoinType.LEFT);

        Join<JobOffer, JobOfferCounter> counterJoin = root.join(JobOffer_.JOB_OFFER_COUNTER);

        Join<JobOffer, JobOfferMetadata> ofertaMetadaneJoin = root.join(JobOffer_.OFERTA_METADANE);

        Expression<BigDecimal> salarySortExpression = cb.coalesce(
                root.get(JobOffer_.SALARY_FROM),
                cb.coalesce(root.get(JobOffer_.SALARY_TO), cb.literal(BigDecimal.ZERO)));

        Expression<Boolean> consentExpression = root.get(JobOffer_.PUBLISH_EMPLOYER_DATA);

        Expression<String> employerSortExpression = cb.selectCase()
                .when(cb.isTrue(consentExpression), employerJoin.get(Employers_.NAZWA))
                .otherwise(cb.literal(KONTAKT)).asString();

        cq.select(root);

        JobOfferSortParameters sortParameters = new JobOfferSortParameters(
                workplaceCityJoin,
                workplaceJoin,
                originCountryJoin,
                unitJoin,
                employmentTypeJoin,
                salarySortExpression,
                employerSortExpression,
                counterJoin
        );

        List<Order> orders = mapSort(cb, root, pageable, sortParameters);

        Predicate predicates = getFilteredJobOffer(criteria, root, cq, cb, workplaceJoin, applicationAddressJoin, employerJoin, workplaceCityJoin, ofertaMetadaneJoin, resolveCityRangeInMainQuery);

        List<JobOffer> list = criteriaQueryUtils.getResultList(cq, orders, predicates, pageable);

        if (list.isEmpty()) {
            return new JobOfferPage(0, new PageImpl<>(Collections.emptyList(), pageable, 0));
        }

        long count;
        int numberOfJobPositions;

        if (doCountInSQL) {
            HibernateCriteriaBuilder cbCount = (HibernateCriteriaBuilder) criteriaQueryUtils.getEntityManager().getCriteriaBuilder();
            JpaCriteriaQuery<Tuple> cqCount = cbCount.createQuery(Tuple.class);
            Root<JobOffer> rootCount = cqCount.from(JobOffer.class);

            Predicate predicatesForCount = getFilteredJobOffer(criteria, rootCount, cqCount, cbCount, workplaceJoin, applicationAddressJoin, employerJoin, workplaceCityJoin, ofertaMetadaneJoin, resolveCityRangeInMainQuery);

            cqCount.multiselect(
                            cbCount.countDistinct(rootCount.get(JobOffer_.ID)).alias(COUNT),
                            cbCount.sum(rootCount.get(JobOffer_.TOTAL_JOB_POSITIONS)).alias(NUMBER_OF_JOB_POSITIONS)
                    ).where(predicatesForCount)
                    .orderBy(Collections.emptyList());

            Tuple row = em.createQuery(cqCount).getSingleResult();

            count = (long) row.get(COUNT);
            numberOfJobPositions = (int) row.get(NUMBER_OF_JOB_POSITIONS);
        } else {
            var distinctJobOfferWithLiczbaMiejscPracyQuery = em.createQuery(cq
                    .distinct(true)
                    .where(predicates)
                    .orderBy(Collections.emptyList())).getResultList();

            count = distinctJobOfferWithLiczbaMiejscPracyQuery.size();

            numberOfJobPositions = distinctJobOfferWithLiczbaMiejscPracyQuery.stream().mapToInt(JobOffer::getTotalJobPositions).sum();
        }

        var time = System.currentTimeMillis();
        log.debug("mapping by {} start", parallelMapping.name());
        List<JobOfferListDto> resultList = jobOfferListMapper.toDtoList(list);
        log.debug("mapping stop {} ms", System.currentTimeMillis() - time);

        return new JobOfferPage(numberOfJobPositions, new PageImpl<>(resultList, pageable, count));
    }

    private List<JobOfferListDto> mapRequirements(
            List<JobOfferListDto> jobOfferList,
            List<JobOffer> jobOfferListFromDb) {
        jobOfferList.forEach(jobOffer -> {
            var ofertaFromDB = jobOfferListFromDb.stream()
                    .filter(op -> op.getId().equals(jobOffer.getDbId()))
                    .findFirst()
                    .orElse(null);
            jobOffer.setWymagania(JobOfferTupleMapper.mapRequirements(ofertaFromDB));
        });
        return jobOfferList;
    }

    private Predicate getFilteredJobOffer(JobOfferCriteria criteria,
                                          Root<JobOffer> root,
                                          JpaCriteriaQuery<?> cq,
                                          CriteriaBuilder cb,
                                          Join<JobOffer, Addresses> miejscePracyJoin,
                                          Join<JobOffer, Addresses> adresAplikowaniaJoin,
                                          Join<JobOffer, Employers> pracodawcyJoin,
                                          Join<Addresses, CityDict> workplaceCityJoin,
                                          Join<JobOffer, JobOfferMetadata> ofertaMetadaneJoin, Boolean resolveCityRangeInMainQuery) {

        List<Predicate> predicatesList = new ArrayList<>();
        List<Predicate> predicatesLocationList = new ArrayList<>();

        addPredicatesForStatus(criteria, root, cb, predicatesList, miejscePracyJoin, adresAplikowaniaJoin);

        JPAUtils.setEqual(root, cb, predicatesList, JobOffer_.KOD_JEZYKA, criteria.getLanguage().getId());

        if (criteria.getLanguage() != LanguageEnum.PL) {
            JPAUtils.setEqual(root, cb, predicatesList, JobOffer_.STATUS_TLUMACZENIA, StatusTlumaczeniaEnum.P);
        }

        JPAUtils.setInOrEqual(root, cb, predicatesList, JobOffer_.JOB_TYPE, criteria.getJobOfferTypes());
        JPAUtils.setLessThanOrEqualTo(root, cb, predicatesList, JobOffer_.STAZ_WYMAG_OGOL, criteria.getWorkExperience());

        if (!CollectionUtils.isEmpty(criteria.getContractTypes())) {
            JPAUtils.setInOrEqualWithChild(root, cb, predicatesList, JobOffer_.EMPLOYMENT_TYPE_CODE, SlElemSlowBase_.ID, criteria.getContractTypes()
                    .stream().flatMap(rodzaj -> rodzaj.getOdpowiadajaceId().stream()).toList());
        }

        if (criteria.getEmploymentFraction() != null) {
            if (criteria.getEmploymentFraction().compareTo(BigDecimal.ONE) < 0) {
                JPAUtils.setLessThanOrEqualTo(root, cb, predicatesList, JobOffer_.WYMIAR_ZATRUD, criteria.getEmploymentFraction());
            } else {
                JPAUtils.setEqual(root, cb, predicatesList, JobOffer_.WYMIAR_ZATRUD, criteria.getEmploymentFraction());
            }
        }

        JPAUtils.setInOrEqualWithChild(workplaceCityJoin, cb, predicatesLocationList, CityDict_.VOIVODESHIP_DICT, VoivodeshipDict_.ID, criteria.getVoivodeshipIds());

        JPAUtils.setInOrEqualWithChild(workplaceCityJoin, cb, predicatesLocationList, CityDict_.COUNTY_DICT, CountyDict_.ID, criteria.getCountyIds());
        JPAUtils.setInOrEqualWithChild(miejscePracyJoin, cb, predicatesLocationList, Addresses_.KOD_POCZTOWY, SlKodPocztowy_.ID, criteria.getPostalCodes());
        JPAUtils.setEqual(root, cb, predicatesLocationList, JobOffer_.EURES_ZAGRANICZNA, criteria.getAbroad());
        JPAUtils.setInOrEqualWithChild(root, cb, predicatesLocationList, JobOffer_.ORIGIN_COUNTRY_CODE, SlElemSlowBase_.ID, criteria.getForeignCountryCodes());

        Join<Addresses, ElementCentralDict> adresRegionZagranicaJoin = miejscePracyJoin.join(Addresses_.KOD_EURO_REGIONU, JoinType.LEFT);
        JPAUtils.setInOrEqual(adresRegionZagranicaJoin, cb, predicatesLocationList, SlElemSlowBase_.ID, criteria.getForeignRegionsCodes());
        JPAUtils.setInOrEqual(adresRegionZagranicaJoin, cb, predicatesLocationList, SlElemSlowBase_.KOD_POZ_NADRZ, criteria.getForeignRegionsCodes());
        JPAUtils.setInOrEqual(miejscePracyJoin, cb, predicatesLocationList, Addresses_.NAZWA_MIEJSC_ZAGR, criteria.getForeignCityNames());

        if (criteria.getDateAddedFrom() != null) {
            JPAUtils.setGreaterThanOrEqualTo(ofertaMetadaneJoin, cb, predicatesList, JobOfferMetadata_.SYSTEM_SUBMISSION_DATE, criteria.getDateAddedFrom().atStartOfDay());
        }

        if (criteria.getDateAddedTo() != null) {
            JPAUtils.setLessThanOrEqualTo(ofertaMetadaneJoin, cb, predicatesList, JobOfferMetadata_.SYSTEM_SUBMISSION_DATE, criteria.getDateAddedTo().plusDays(1).atStartOfDay());
        }

        if (!CollectionUtils.isEmpty(criteria.getCities())) {

            var resolveCityRangeTime = System.currentTimeMillis();
            log.debug("resolve city ranges in {} start", resolveCityRangeInMainQuery ? "one main query" : "second prefilter query");

            if (resolveCityRangeInMainQuery) {
                var withRange = criteria.getCities().stream()
                        .filter(c -> c.range() != null && c.range() > 0)
                        .toList();

                var withoutRangeIds = criteria.getCities().stream()
                        .filter(c -> c.range() == null || c.range() == 0)
                        .map(LocalityRangeDto::cityId)
                        .toList();

                List<Predicate> cityPredicates = new ArrayList<>();

                if (!withoutRangeIds.isEmpty()) {
                    cityPredicates.add(
                            workplaceCityJoin.get(CityDict_.ID).in(withoutRangeIds)
                    );
                }

                if (!withRange.isEmpty()) {
                    List<Predicate> rangeOrs = new ArrayList<>();

                    for (LocalityRangeDto c : withRange) {
                        var sub = cq.subquery(Integer.class);
                        var cityB = sub.from(CityDict.class);

                        var within = cb.function(
                                "ST_DWithin",
                                Boolean.class,
                                cb.function("ST_Transform", Object.class,
                                        workplaceCityJoin.get(CityDict_.CENTRA),
                                        cb.literal(3035)),
                                cb.function("ST_Transform", Object.class,
                                        cityB.get(CityDict_.CENTRA),
                                        cb.literal(3035)),
                                cb.literal(c.range() * 1000d)
                        );

                        sub.select(cb.literal(1))
                                .where(
                                        cb.and(
                                                cb.equal(cityB.get(CityDict_.ID), c.cityId()),
                                                cb.isTrue(within)
                                        )
                                );

                        rangeOrs.add(cb.exists(sub));
                    }

                    cityPredicates.add(cb.or(rangeOrs.toArray(Predicate[]::new)));

                    predicatesList.add(cb.or(cityPredicates.toArray(new Predicate[0])));
                }

            } else {
                var wszystkieMiejscowosci = cityRepositoryImpl.findCitiesForRanges(criteria.getCities().stream().filter(m -> m.range() != null && m.range() != 0).toList());
                wszystkieMiejscowosci.addAll(criteria.getCities().stream().map(LocalityRangeDto::cityId).toList());

                JPAUtils.setInOrEqual(workplaceCityJoin, cb, predicatesLocationList, CityDict_.ID, wszystkieMiejscowosci.stream().toList());
            }

            log.debug("resolve city ranges stop {} ms", System.currentTimeMillis() - resolveCityRangeTime);
        }

        if (criteria.getForDisabledPersons() != null && criteria.getForDisabledPersons()) {
            predicatesList.add(cb.or(
                    cb.isTrue(root.get(JobOffer_.NIEPELNOSPRAWNI)),
                    cb.greaterThan(root.get(JobOffer_.DISABLED_JOB_POSITIONS), 0)));
        }

        if (!CollectionUtils.isEmpty(criteria.getWorkModes())) {
            Join<JobOffer, TrybyPracy> trybyPracyJoin = root.join(JobOffer_.TRYBY_PRACY, JoinType.INNER);
            JPAUtils.setInOrEqualWithChild(trybyPracyJoin, cb, predicatesList, JobOffer_.TRYBY_PRACY, SlElemSlowBase_.ID, criteria.getWorkModes());
        }

        if (!CollectionUtils.isEmpty(criteria.getEmployers())) {
            List<Predicate> predicatesEmployerList = new ArrayList<>();

            Expression<Boolean> zgodaExpression = root.get(JobOffer_.PUBLISH_EMPLOYER_DATA);

            for (EmployerDictionaryDto pracodawca : criteria.getEmployers()) {
                JPAUtils.setContainWithIgnoreCase(pracodawcyJoin, cb, predicatesEmployerList, Employers_.NAZWA, pracodawca.employerName());
                JPAUtils.setEqual(pracodawcyJoin, cb, predicatesEmployerList, Employers_.NIP, pracodawca.employerNip());
            }

            if (!predicatesEmployerList.isEmpty()) {
                Predicate pracodawcyOr = cb.or(predicatesEmployerList.toArray(new Predicate[0]));
                Predicate filteredPracodawcy = cb.and(cb.isTrue(zgodaExpression), pracodawcyOr);
                predicatesList.add(filteredPracodawcy);
            }
        }

        addPredicatesForJobTitle(root, cb, criteria.getJobTitles(), predicatesList);
        addPredicatesForProfession(root, cb, criteria.getProfessions(), predicatesList);
        addPredicatesForForeignLanguages(root, cb, criteria.getForeignLanguages(), predicatesList);
        addPredicatesForEducation(root, cb, criteria.getEducations(), predicatesList);
        addPredicatesForEmployerType(cb, criteria, predicatesList, pracodawcyJoin);
        addPredicatesForSalary(root, cb, criteria, predicatesList);

        if (!predicatesLocationList.isEmpty()) {
            Predicate locationOr = cb.or(predicatesLocationList.toArray(new Predicate[0]));
            predicatesList.add(locationOr);
        }

        return cb.and(predicatesList.toArray(new Predicate[0]));
    }

    private void addPredicatesForForeignLanguages(Root<JobOffer> root, CriteriaBuilder cb, List<ForeignLanguageDto> foreignLanguages, List<Predicate> predicatesList) {
        if (!CollectionUtils.isEmpty(foreignLanguages)) {
            Join<JobOffer, WymaganiaJezyk> wymaganeJezykiJoin = root.join(JobOffer_.WYMAGANE_JEZYKI, JoinType.LEFT);
            List<Predicate> predicateJezykList = new ArrayList<>();

            for (ForeignLanguageDto jezyk : foreignLanguages) {
                List<Predicate> predicateJezyk = new ArrayList<>();
                JPAUtils.setEqualWithChild(wymaganeJezykiJoin, cb, predicateJezyk, WymaganiaJezyk_.ID_JEZYKA_OBCEGO, SlElemSlowBase_.ID, jezyk.jezykId());
                if (jezyk.stopienZnajomosciWMowie() != null) {
                    JPAUtils.setInOrEqualWithChild(wymaganeJezykiJoin, cb, predicateJezyk, WymaganiaJezyk_.KOD_STOP_ZNAJ_WMOWIE, SlElemSlowBase_.ID, jezyk.stopienZnajomosciWMowie().getPoziomyZawierajaceSie());
                }
                if (jezyk.stopienZnajomosciWPismie() != null) {
                    JPAUtils.setInOrEqualWithChild(wymaganeJezykiJoin, cb, predicateJezyk, WymaganiaJezyk_.KOD_STOP_ZNAJ_WPISMIE, SlElemSlowBase_.ID, jezyk.stopienZnajomosciWPismie().getPoziomyZawierajaceSie());
                }
                predicateJezykList.add(cb.and(predicateJezyk.toArray(new Predicate[0])));
            }

            Predicate jezykiOr = cb.or(predicateJezykList.toArray(new Predicate[0]));
            predicatesList.add(jezykiOr);
        }
    }

    private void addPredicatesForEducation(Root<JobOffer> root, CriteriaBuilder cb, List<EducationDto> wyksztalcenieList, List<Predicate> predicatesList) {
        if (!CollectionUtils.isEmpty(wyksztalcenieList)) {
            Join<JobOffer, WymaganiaWyksztalcenie> wymaganeWyksztalcenieJoin = root.join(JobOffer_.WYMAGANE_WYKSZTALCENIE, JoinType.LEFT);
            List<Predicate> predicateWyksztalcenieList = new ArrayList<>();

            for (EducationDto wyksztalcenie : wyksztalcenieList) {
                List<Predicate> predicateWyksztalcenie = new ArrayList<>();
                JPAUtils.setInOrEqualWithChild(wymaganeWyksztalcenieJoin, cb, predicateWyksztalcenie, WymaganiaWyksztalcenie_.KOD_POZ_WYKSZT, SlElemSlowBase_.ID, wyksztalcenie.educationLevel().getPoziomyZawierajaceSie());
                JPAUtils.setEqualWithChild(wymaganeWyksztalcenieJoin, cb, predicateWyksztalcenie, WymaganiaWyksztalcenie_.KOD_TYPU_WYKSZT, SlElemSlowBase_.ID, wyksztalcenie.typeId());

                predicateWyksztalcenieList.add(cb.and(predicateWyksztalcenie.toArray(new Predicate[0])));
            }

            Predicate wyksztalcenieOr = cb.or(predicateWyksztalcenieList.toArray(new Predicate[0]));
            predicatesList.add(wyksztalcenieOr);
        }
    }

    private void addPredicatesForProfession(Root<JobOffer> root, CriteriaBuilder cb, List<ProfessionDto> professions, List<Predicate> predicatesList) {
        if (!CollectionUtils.isEmpty(professions)) {
            Join<JobOffer, WymaganiaZawod> wymaganeZawodyJoin = root.join(JobOffer_.WYMAGANY_ZAWOD, JoinType.LEFT);
            Join<WymaganiaZawod, ElementCentralDict> kodZawoduJoin = wymaganeZawodyJoin.join(WymaganiaZawod_.KOD_ZAWODU, JoinType.LEFT);
            List<Predicate> predicatesZawodList = new ArrayList<>();
            for (ProfessionDto zawod : professions) {
                JPAUtils.setContainWithIgnoreCaseWithChild(root, cb, predicatesZawodList, JobOffer_.KOD_ZAWODU, SlElemSlowBase_.OPIS_ELEM, zawod.profesionName());
                JPAUtils.setEqualWithChild(root, cb, predicatesZawodList, JobOffer_.KOD_ZAWODU, SlElemSlowBase_.ID, zawod.professionId());
                if (zawod.workExperience() == null || zawod.workExperience().equals(BigDecimal.ZERO)) {
                    JPAUtils.setContainWithIgnoreCase(kodZawoduJoin, cb, predicatesZawodList, SlElemSlowBase_.OPIS_ELEM, zawod.profesionName());
                    JPAUtils.setEqual(kodZawoduJoin, cb, predicatesZawodList, SlElemSlowBase_.ID, zawod.professionId());
                } else {
                    if (zawod.professionId() != null) {
                        predicatesZawodList.add(cb.and(
                                cb.or(
                                        cb.equal(kodZawoduJoin.get(SlElemSlowBase_.ID), zawod.professionId()),
                                        (zawod.profesionName() != null && !zawod.profesionName().trim().isEmpty() ?
                                                cb.like(cb.upper(kodZawoduJoin.get(SlElemSlowBase_.OPIS_ELEM)), "%" + zawod.profesionName().toUpperCase() + "%")
                                                : cb.conjunction()),
                                        cb.lessThanOrEqualTo(wymaganeZawodyJoin.get(WymaganiaZawod_.STAZ_WYMAGANIA), zawod.workExperience()))));
                    } else if (zawod.profesionName() != null) {
                        predicatesZawodList.add(cb.and(
                                cb.like(cb.upper(kodZawoduJoin.get(SlElemSlowBase_.OPIS_ELEM)), "%" + zawod.profesionName().toUpperCase() + "%"),
                                cb.lessThanOrEqualTo(wymaganeZawodyJoin.get(WymaganiaZawod_.STAZ_WYMAGANIA), zawod.workExperience())));
                    }
                }
            }
            Predicate zawodyOr = cb.or(predicatesZawodList.toArray(new Predicate[0]));
            predicatesList.add(zawodyOr);
        }
    }


    private void addPredicatesForJobTitle(Root<JobOffer> root, CriteriaBuilder cb, List<String> jobTitles, List<Predicate> predicatesList) {
        if (!CollectionUtils.isEmpty(jobTitles)) {
            List<Predicate> predicatesJobTitleList = new ArrayList<>();

            for (String jobTitle : jobTitles) {
                JPAUtils.setContainWithIgnoreCase(root, cb, predicatesJobTitleList, JobOffer_.JOB_TITLE, jobTitle.trim());
            }
            Predicate jobTitlesOr = cb.or(predicatesJobTitleList.toArray(new Predicate[0]));
            predicatesList.add(jobTitlesOr);
        }
    }

    private void addPredicatesForEmployerType(CriteriaBuilder cb, JobOfferCriteria criteria, List<Predicate> predicatesList, Join<JobOffer, Employers> employersJoin) {

        List<Predicate> employerTypePredicateList = new ArrayList<>();
        JPAUtils.setEqual(employersJoin, cb, employerTypePredicateList, Employers_.PRACODAWCA, criteria.getIsEmployer());
        JPAUtils.setEqual(employersJoin, cb, employerTypePredicateList, Employers_.OSOBA_FIZYCZNA, criteria.getIsNaturalPerson());
        JPAUtils.setEqual(employersJoin, cb, employerTypePredicateList, Employers_.PRZEDSIEBIORCA, criteria.getIsEntrepreneur());
        if (!employerTypePredicateList.isEmpty()) {
            Predicate employerTypeOr = cb.or(employerTypePredicateList.toArray(new Predicate[0]));
            predicatesList.add(employerTypeOr);
        }

    }


    private void addPredicatesForStatus(JobOfferCriteria criteria, Root<JobOffer> root, CriteriaBuilder cb, List<Predicate> predicatesList,
                                        Join<JobOffer, Addresses> workplaceAddressJoin, Join<JobOffer, Addresses> applicationAddressJoin) {

        if (criteria.getJobOfferStatusList().size() == 1 && criteria.getJobOfferStatusList().getFirst().equals(ACTIVE)) {
            JPAUtils.setEqual(root, cb, predicatesList, JobOffer_.STATUS, ACTIVE.getCode());
            workplaceAddressJoin.on(cb.equal(workplaceAddressJoin.get(Addresses_.STATUS), ACTIVE.getCode()));
            if (applicationAddressJoin != null) {
                applicationAddressJoin.on(cb.equal(applicationAddressJoin.get(Addresses_.STATUS), ACTIVE.getCode()));
            }
        } else {

            if (criteria.getReportedDateFrom() == null || criteria.getReportedDateTo() == null) {
                throw new RuntimeException("Submission dates must be provided for archived offers");
            }

            JPAUtils.setInOrEqual(root, cb, predicatesList, JobOffer_.STATUS, criteria.getJobOfferStatusList().stream().map(JobOfferStatusEnum::getCode).toList());
            workplaceAddressJoin.on(cb.and(
                    cb.greaterThanOrEqualTo(workplaceAddressJoin.get(Addresses_.DATA_PRZYJ_ZGLOSZ), criteria.getReportedDateFrom()),
                    cb.lessThanOrEqualTo(workplaceAddressJoin.get(Addresses_.DATA_PRZYJ_ZGLOSZ), criteria.getReportedDateTo()),
                    workplaceAddressJoin.get(Addresses_.STATUS).in(criteria.getJobOfferStatusList().stream().map(JobOfferStatusEnum::getCode).toList())
            ));
            if (applicationAddressJoin != null) {
                applicationAddressJoin.on(cb.and(
                        cb.greaterThanOrEqualTo(applicationAddressJoin.get(Addresses_.DATA_PRZYJ_ZGLOSZ), criteria.getReportedDateFrom()),
                        cb.lessThanOrEqualTo(applicationAddressJoin.get(Addresses_.DATA_PRZYJ_ZGLOSZ), criteria.getReportedDateTo()),
                        applicationAddressJoin.get(Addresses_.STATUS).in(criteria.getJobOfferStatusList().stream().map(JobOfferStatusEnum::getCode).toList())
                ));
            }
        }

    }


    private void addPredicatesForSalary(Root<JobOffer> root, CriteriaBuilder cb, JobOfferCriteria criteria, List<Predicate> predicatesList) {
        if (criteria.getSalaryFrom() != null) {
            predicatesList.add(cb.or(cb.greaterThan(root.get(JobOffer_.SALARY_TO), criteria.getSalaryFrom()), cb.greaterThan(root.get(JobOffer_.SALARY_TO), criteria.getSalaryFrom())));
        }

        if (criteria.getSalaryTo() != null) {
            predicatesList.add(cb.or(cb.lessThanOrEqualTo(root.get(JobOffer_.SALARY_FROM), criteria.getSalaryTo()), cb.lessThanOrEqualTo(root.get(JobOffer_.SALARY_TO), criteria.getSalaryTo())));
        }
    }

    private List<Order> mapSort(HibernateCriteriaBuilder cb, Root<JobOffer> root, Pageable pageable,
                                JobOfferSortParameters sortParameters) {
        List<Order> orders = new ArrayList<>();
        if (pageable.getSort().isSorted()) {

            Sort.Order sort = pageable.getSort().get().findFirst().orElse(Sort.Order.by(DATA_WAZNOSCI));
            switch (sort.getProperty()) {

                case DATA_WAZNOSCI -> orders.add(getOrder(root.get(JobOffer_.DATA_WAZN_DO), cb, sort.getDirection()));

                case PRACODAWCA_ALIAS ->
                        orders.add(getOrder(sortParameters.employerSortExpression(), cb, sort.getDirection()));

                case SALARY_ALIAS ->
                        orders.add(getOrder(sortParameters.salarySortExpression(), cb, sort.getDirection()));

                case MIEJSCE_PRACY -> {
                    orders.add(getOrder(sortParameters.workplaceCityJoin().get(CityDict_.NAZWA_MIEJSC), cb, sort.getDirection()));
                    orders.add(getOrder(sortParameters.workplaceAddressJoin().get(Addresses_.NAZWA_MIEJSC_ZAGR), cb, sort.getDirection()));
                    orders.add(getOrder(sortParameters.originCountryJoin().get(SlElemSlowBase_.KOD_ELEM), cb, sort.getDirection()));
                }

                case STANOWISKO_ALIAS -> orders.add(getOrder(root.get(JobOffer_.JOB_TITLE), cb, sort.getDirection()));

                case RODZAJ_UMOWY ->
                        orders.add(getOrder(sortParameters.employmentTypeJoin().get(SlElemSlowBase_.OPIS_ELEM), cb, sort.getDirection()));

                case POPULARNOSC_ALIAS ->
                        orders.add(getOrder(sortParameters.counterJoin().get(JobOfferCounter_.COUNTER), cb, sort.getDirection()));

                case DODANE_PRZEZ ->
                        orders.add(getOrder(sortParameters.unitJoin().get(UnitDict_.TYP_PLACOWKI), cb, sort.getDirection()));

                case STOPIEN_DOPASOWANIA -> orders = Collections.emptyList();

                default -> orders.add(getOrder(root.get(JobOffer_.ID), cb, sort.getDirection()));
            }

        } else {
            orders.add(getOrder(root.get(JobOffer_.ID), cb, Sort.Direction.DESC));
        }
        return orders;
    }

    private Order getOrder(Expression<?> expression, CriteriaBuilder cb, Sort.Direction kierunek) {
        return kierunek.isDescending() ? cb.desc(expression) : cb.asc(expression);
    }

}
