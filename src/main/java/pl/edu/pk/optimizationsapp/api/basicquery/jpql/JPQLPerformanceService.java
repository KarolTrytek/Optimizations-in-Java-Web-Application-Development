package pl.edu.pk.optimizationsapp.api.basicquery.jpql;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOffer;
import pl.edu.pk.optimizationsapp.data.repository.JobOfferRepository;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JPQLPerformanceService {

    private final JobOfferRepository jobOfferRepository;

    public void getOfertaPracyById(Long id) {
        jobOfferRepository.getByIdJPQL(id);
    }

    public List<JobOffer> findJobOffersBySalaryFromGreaterThanEqualOrderByJobTitleLimit(BigDecimal salarFrom, int limit) {
        return jobOfferRepository.findBySalaryFromGreaterThanEqualOrderByJobTitleJPQL(salarFrom, limit);
    }

}
