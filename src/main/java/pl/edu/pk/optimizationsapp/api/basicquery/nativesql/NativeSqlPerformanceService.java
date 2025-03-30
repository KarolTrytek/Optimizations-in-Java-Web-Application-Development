package pl.edu.pk.optimizationsapp.api.basicquery.nativesql;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pk.optimizationsapp.data.repository.JobOfferRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NativeSqlPerformanceService {

    private final JobOfferRepository jobOfferRepository;

    public Object[] getOfertaPracyById(Long id) {
        return jobOfferRepository.getByIdNativeQuery(id);
    }

    public List<Object[]> findJobOffersBySalaryFromGreaterThanEqualOrderByJobTitleLimit(BigDecimal salarFrom, int limit) {
        return jobOfferRepository.findBySalaryFromGreaterThanEqualOrderByJobTitleNativeQuery(salarFrom, limit);
    }

}
