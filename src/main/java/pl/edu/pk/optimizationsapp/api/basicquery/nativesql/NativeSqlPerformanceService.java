package pl.edu.pk.optimizationsapp.api.basicquery.nativesql;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOffer;
import pl.edu.pk.optimizationsapp.data.repository.JobOfferRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class NativeSqlPerformanceService {

    private final JobOfferRepository jobOfferRepository;

    public JobOffer getOfertaPracyById(Long id) {
        jobOfferRepository.getByIdNativeQuery(id);
        return null;
    }

}
