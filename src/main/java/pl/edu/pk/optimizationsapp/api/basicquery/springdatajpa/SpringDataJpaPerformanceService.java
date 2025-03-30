package pl.edu.pk.optimizationsapp.api.basicquery.springdatajpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOffer;
import pl.edu.pk.optimizationsapp.data.repository.JobOfferRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class SpringDataJpaPerformanceService {

    @PersistenceContext
    private final EntityManager em;

    public SpringDataJpaPerformanceService(EntityManager em, JobOfferRepository jobOfferRepository) {
        this.em = em;
        this.jobOfferRepository = jobOfferRepository;
    }

    private final JobOfferRepository jobOfferRepository;

    @Transactional
    public JobOffer getOfertaPracyById(Long id) {
        return jobOfferRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void getOfertaPracyById(Long id, int attempts) {
        for (int i = 0; i < attempts; i++) {
            var jobOffer = jobOfferRepository.findById(id).orElseThrow();
            em.refresh(jobOffer);
        }
    }

    @Transactional
    public List<JobOffer> findJobOffersBySalaryFromGreaterThanEqualOrderByJobTitleLimit(BigDecimal wynagrodzenieOd, int limit) {
        return jobOfferRepository.findBySalaryFromGreaterThanEqualOrderByJobTitle(wynagrodzenieOd, Limit.of(limit));
    }

}
