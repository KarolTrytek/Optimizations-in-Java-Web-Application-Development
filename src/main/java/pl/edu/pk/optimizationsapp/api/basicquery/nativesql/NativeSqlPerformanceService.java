package pl.edu.pk.optimizationsapp.api.basicquery.nativesql;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pk.optimizationsapp.data.domain.ofz.TypOfertyEnum;
import pl.edu.pk.optimizationsapp.data.repository.JobOfferRepository;

import java.math.BigDecimal;
import java.util.List;

import static pl.edu.pk.optimizationsapp.utils.Constants.DATA_PRZYJ_ZGLOSZENIA;

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

    public List<Object[]> findAllJobOffersWithJobTypeInAnyList(List<TypOfertyEnum> types1, List<TypOfertyEnum> types2) {
        return jobOfferRepository.findAllJobOffersWithJobTypeInAnyListNativeQuery(types1.stream().map(Enum::toString).toList(), types2.stream().map(Enum::toString).toList(), DATA_PRZYJ_ZGLOSZENIA);
    }

}
