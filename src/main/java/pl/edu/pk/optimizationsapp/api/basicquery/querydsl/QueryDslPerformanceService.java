package pl.edu.pk.optimizationsapp.api.basicquery.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOffer;
import pl.edu.pk.optimizationsapp.data.domain.ofz.QJobOffer;

import java.math.BigDecimal;
import java.util.List;

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

}
