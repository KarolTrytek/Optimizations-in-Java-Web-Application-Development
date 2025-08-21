package pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.JobOfferCriteria;
import pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.JobOfferPage;
import pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.MappingTypeEnum;

@Slf4j
@RequestMapping(value = "job-offer/")
@Tag(name = "Job offer API")
@RestController
@RequiredArgsConstructor
public class JobOfferController {

    private final MultiselectQueryService multiselectQueryService;

    @PostMapping("grid")
    @Operation(summary = "Get filtered, sorted and paged job offer list.", operationId = "getJobOfferGrid")
    public JobOfferPage getJobOfferGrid(@ParameterObject Pageable pageable,
                                              @RequestParam Boolean doCountInSQL,
                                              @RequestParam Boolean resolveCityRangeInMainQuery,
                                              @RequestParam Boolean multiselect,
                                              @RequestParam Boolean fromCache,
                                              @RequestParam MappingTypeEnum mappingType,
                                              @RequestBody JobOfferCriteria criteria) {
        var time = System.currentTimeMillis();
        log.debug("getJobOfferGrid start");

        var jobTitlesDictionary = multiselect ?
                ( fromCache ? multiselectQueryService.findJobOfferMultiselectListFromCache(criteria, pageable, doCountInSQL, mappingType, resolveCityRangeInMainQuery) : multiselectQueryService.findJobOfferMultiselectList(criteria, pageable, doCountInSQL, mappingType, resolveCityRangeInMainQuery))
                : multiselectQueryService.findJobOfferList(criteria, pageable, doCountInSQL, mappingType, resolveCityRangeInMainQuery);

        log.debug("getJobOfferGrid mapping by: {}, limit: {} stop {} ms", mappingType, pageable.getPageSize(), System.currentTimeMillis() - time);

        return jobTitlesDictionary;
    }

    @PostMapping("without-partitioning/grid")
    @Operation(summary = "Get filtered, sorted and paged job offer list from tables without partitioning.", operationId = "getJobOfferWithoutPartitioningGrid")
    public JobOfferPage getJobOfferWithoutPartitioningGrid(@ParameterObject Pageable pageable,
                                                 @RequestParam Boolean doCountInSQL,
                                                 @RequestParam Boolean resolveCityRangeInMainQuery,
                                                 @RequestParam Boolean multiselect,
                                                 @RequestParam MappingTypeEnum mappingType,
                                                 @RequestBody JobOfferCriteria criteria) {
        throw new NotImplementedException();
    }

}
