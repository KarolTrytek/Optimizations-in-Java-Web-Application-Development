package pl.edu.pk.optimizationsapp.api.basicquery.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOffer;
import pl.edu.pk.optimizationsapp.data.domain.ofz.QJobOffer;

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
        QJobOffer ofertyPracy = QJobOffer.jobOffer;

        JobOffer ofertaFromDb = queryFactory.selectFrom(ofertyPracy)
                .where(ofertyPracy.id.eq(id))
                .fetchOne();

        return ofertaFromDb;
    }
}
