package pl.edu.pk.optimizationsapp.api.basicquery.criteriaapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOffer;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOffer_;
import pl.edu.pk.optimizationsapp.data.repository.JobOfferRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class CriteriaApiPerformanceService {

    private final JobOfferRepository jobOfferRepository;

    public void getOfertaPracyById(Long id) {
        Specification<JobOffer> idEqual = (root, query, cb) -> cb.equal(root.get(JobOffer_.ID), id);

        jobOfferRepository.findAll(idEqual);
    }

}
