package pl.edu.pk.optimizationsapp.api.database_3_3.indexes_1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pk.optimizationsapp.api.database_3_3.indexes_1.dto.DictionaryDto;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOfferStatusEnum;
import pl.edu.pk.optimizationsapp.data.domain.ofz.LanguageEnum;

import java.util.List;

@Slf4j
@RequestMapping(value = "dictionary/")
@Tag(name = "Dictionary API")
@RestController
@RequiredArgsConstructor
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @GetMapping("autocomplete/job-titles")
    @Operation(summary = "Return Job Titles Dictionary", operationId = "getJobTitlesAutocomplete")
    public List<String> getJobTitlesAutocomplete(@RequestParam("name") @Parameter(description = "Job title", example = "archi") String name,
                                                 @RequestParam("limit") @Parameter(description = "Maximum number of job titles", example = "20") Long limit,
                                                 @RequestParam(defaultValue = "ACTIVE") JobOfferStatusEnum jobOfferStatus,
                                                 @RequestParam(name = "language", defaultValue = "PL") @Parameter(description = "Jezyk wyszukiwania", example = "EN") LanguageEnum language) {
        var time = System.currentTimeMillis();
        log.debug("getJobTitlesAutocomplete start");
        log.debug("using CREATE INDEX idx_job_offer_title_like ON ofz.oferty_pracy USING gist(stanow_ofer gist_trgm_ops); on each partition");
        log.debug("using CREATE INDEX idx_job_offer_status_lang_upper ON ofz.oferty_pracy (status, kod_jezyka, upper(stanow_ofer)); on each partition");

        var jobTitlesDictionary = dictionaryService.getJobTitlesAutocomplete(name, limit, jobOfferStatus, language);

        log.debug("getJobTitlesAutocomplete"
                + " for given name: " + "\"" + name + "\","
                + " status: " + jobOfferStatus.getCode()
                + ", limit: " + limit
                + ", language: " + language.getName()
                + " stop {} ms", System.currentTimeMillis() - time);

        return jobTitlesDictionary;
    }

    @GetMapping("autocomplete/localization")
    @Operation(summary = "Suggests the location of a job offer upon entering a name", operationId = "getLocalizationAutocomplete")
    public List<DictionaryDto> getLocalizationAutocomplete(@RequestParam("name") @Parameter(description = "Name of localization", example = "Warszawa") String name,
                                                           @RequestParam("limit") @Parameter(description = "Maximum number of localizations", example = "20") Long limit,
                                                           @RequestParam(value = "optimized", defaultValue = "true") @Parameter(description = "Optimized query") Boolean optimized) {
        var time = System.currentTimeMillis();
        log.debug("getLocalizationAutocomplete start");

        var jobTitlesDictionary = dictionaryService.getLocalizationAutocomplete(name, limit, optimized);

        log.debug("getLocalizationAutocomplete"
                + (optimized ? "optimized" : "not optimized")
                + ", for given name: " + "\"" + name + "\","
                + ", limit: " + limit
                + " stop {} ms", System.currentTimeMillis() - time);

        return jobTitlesDictionary;
    }

}
