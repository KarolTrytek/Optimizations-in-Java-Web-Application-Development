package pl.edu.pk.optimizationsapp.api.queries_1.basicquery.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOffer;
import pl.edu.pk.optimizationsapp.data.domain.ofz.QJobOffer;
import pl.edu.pk.optimizationsapp.data.domain.ofz.TypOfertyEnum;

import java.math.BigDecimal;
import java.util.List;

import static pl.edu.pk.optimizationsapp.utils.Constants.DATA_PRZYJ_ZGLOSZENIA;

@Service
@Slf4j
public class QueryDslPerformanceService {

    @PersistenceContext
    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    public QueryDslPerformanceService(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public JobOffer getOfertaPracyById(Long id) {
        QJobOffer jobOffers = QJobOffer.jobOffer;

        JobOffer ofertaFromDb = queryFactory.selectFrom(jobOffers)
                .where(jobOffers.id.eq(id))
                .fetchOne();

        return ofertaFromDb;
    }

    public List<JobOffer> findJobOffersBySalaryFromGreaterThanEqualOrderByJobTitleLimit(BigDecimal wynagrodzenieOd, int limit) {
        QJobOffer jobOffers = QJobOffer.jobOffer;
        return queryFactory.selectFrom(jobOffers)
                .where(jobOffers.salaryFrom.gt(wynagrodzenieOd))
                .orderBy(jobOffers.jobTitle.asc()).limit(limit)
                .fetch();
    }

    public List<JobOffer> findAllJobOffersWithJobTypeInAnyList(List<TypOfertyEnum> types1, List<TypOfertyEnum> types2) {
        QJobOffer jobOffers = QJobOffer.jobOffer;
        return queryFactory.selectFrom(jobOffers)
                .where((jobOffers.jobType.in(types1).or(jobOffers.jobType.in(types2)))
                        .and(jobOffers.dataPrzyjZglosz.gt(DATA_PRZYJ_ZGLOSZENIA)))
                .fetch();
    }

}
