package pl.edu.pk.optimizationsapp.api.database_3_3.indexes_1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOfferStatusEnum;
import pl.edu.pk.optimizationsapp.data.domain.ofz.LanguageEnum;
import pl.edu.pk.optimizationsapp.data.repository.JobOfferRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DictionaryService {

    private final JobOfferRepository jobOfferRepository;

    public List<String> getJobTitlesAutocomplete(String name, Long limit, JobOfferStatusEnum status, LanguageEnum language) {
        return jobOfferRepository.findJobTitleByJobTitleContainingIgnoreCaseAndLanguageLimit(name, language.getId(), status, limit);
    }

}
