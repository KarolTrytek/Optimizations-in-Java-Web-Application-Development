package pl.edu.pk.optimizationsapp.api.queries_1.basicquery.criteriaapi;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOffer;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOffer_;
import pl.edu.pk.optimizationsapp.data.domain.ofz.TypOfertyEnum;
import pl.edu.pk.optimizationsapp.data.repository.JobOfferRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static pl.edu.pk.optimizationsapp.utils.Constants.DATA_PRZYJ_ZGLOSZENIA;

@Slf4j
@Service
public class CriteriaApiPerformanceService {

    @PersistenceContext
    private final EntityManager em;

    public CriteriaApiPerformanceService(EntityManager em, JobOfferRepository jobOfferRepository) {
        this.em = em;
        this.jobOfferRepository = jobOfferRepository;
    }

    private final JobOfferRepository jobOfferRepository;

    public void getOfertaPracyById(Long id) {
        Specification<JobOffer> idEqual = (root, query, cb) -> cb.equal(root.get(JobOffer_.ID), id);

        jobOfferRepository.findAll(idEqual);
    }

    public List<JobOffer> findJobOffersBySalaryFromGreaterThanEqualOrderByJobTitlePageable(BigDecimal salarFrom, int limit) {
        return jobOfferRepository.findAll(jobOfferSpec(salarFrom), PageRequest.ofSize(limit)).getContent();
    }

    public List<JobOffer> findJobOffersBySalaryFromGreaterThanEqualOrderByJobTitleLimit(BigDecimal salarFrom, int limit) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<JobOffer> query = cb.createQuery(JobOffer.class);
        Root<JobOffer> root = query.from(JobOffer.class);

        List<Predicate> predicatesList = new ArrayList<>();
        predicatesList.add(cb.greaterThanOrEqualTo(root.get(JobOffer_.SALARY_FROM), salarFrom));

        query.where(cb.and(predicatesList.toArray(new Predicate[0])));
        query.orderBy(cb.asc(root.get(JobOffer_.JOB_TITLE)));

        TypedQuery<JobOffer> typedQuery = em.createQuery(query);
        typedQuery.setMaxResults(limit);

        return typedQuery.getResultList();
    }

    public List<JobOffer> findAllJobOffersWithJobTypeInAnyList(List<TypOfertyEnum> types1, List<TypOfertyEnum> types2) {
        return jobOfferRepository.findAll(specJobTypeInAnyList(types1, types2));
    }

    private Specification<JobOffer> specJobTypeInAnyList(List<TypOfertyEnum> types1, List<TypOfertyEnum> types2) {
        return (root, q, cb) ->
                cb.and(
                        cb.or(
                                root.get(JobOffer_.JOB_TYPE).in(types1),
                                root.get(JobOffer_.JOB_TYPE).in(types2)
                        ),
                cb.greaterThan(root.get(JobOffer_.DATA_PRZYJ_ZGLOSZ), DATA_PRZYJ_ZGLOSZENIA));
    }

    private Specification<JobOffer> jobOfferSpec(BigDecimal salarFrom) {
        return (root, query, cb) -> {
            List<Predicate> predicatesList = new ArrayList<>();

            predicatesList.add(cb.greaterThanOrEqualTo(root.get(JobOffer_.SALARY_FROM), salarFrom));

            query.orderBy(cb.asc(root.get(JobOffer_.jobTitle)));

            return cb.and(predicatesList.toArray(new Predicate[0]));
        };
    }

}
