package pl.edu.pk.optimizationsapp.api.basicquery.jpql;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pk.optimizationsapp.data.repository.JobOfferRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class JPQLPerformanceService {

    private final JobOfferRepository jobOfferRepository;

    public void getOfertaPracyById(Long id) {
        jobOfferRepository.getByIdJPQL(id);
    }
}
