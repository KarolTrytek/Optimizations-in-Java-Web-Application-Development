package pl.edu.pk.optimizationsapp.api.queries_1.basicquery.nativesql;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

import static pl.edu.pk.optimizationsapp.utils.Constants.TYPES1;
import static pl.edu.pk.optimizationsapp.utils.Constants.TYPES2;

@Slf4j
@RequestMapping(value = "native-sql/basic/")
@Tag(name = "Basic Native SQL")
@RestController
@RequiredArgsConstructor
public class NativeSqlPerformanceController {

    private final NativeSqlPerformanceService nativeSqlPerformanceService;


    @GetMapping("{id}")
    @Operation(summary = "Returns an offer for the identifier", operationId = "getJobOfferById")
    public String getJobOfferById(@PathVariable() Long id) {
        var time = System.currentTimeMillis();
        log.debug("getJobOfferById one attempt start");

        nativeSqlPerformanceService.getOfertaPracyById(id);

        log.debug("getJobOfferById one attempt stop {} ms", System.currentTimeMillis() - time);
        return  "Getting an job offer with id: " + id +" using Native SQL took: " + (System.currentTimeMillis() - time) + "ms";

    }

    @GetMapping("{id}/attempts/{attempts}")
    @Operation(summary = "Searches for an offer for the identifier a specified number of times ", operationId = "getJobOfferByIdForMoreAttempts")
    public String getJobOfferByIdForMoreAttempts(@PathVariable(value = "id") Long id, @PathVariable(value = "attempts") Integer attempts) {
        var time = System.currentTimeMillis();
        log.debug("findJobOfferById start, attempts {}", attempts);

        for (int i = 0; i < attempts; i++) {
            nativeSqlPerformanceService.getOfertaPracyById(id);
        }

        log.debug("getJobOfferById stop {} ms, attempts {}", System.currentTimeMillis() - time, attempts);
        return "Getting  an job offer with id: " + id + " for " + attempts + " attempts using Native SQL took: " + (System.currentTimeMillis() - time) + "ms";
    }

    @GetMapping("list")
    @Operation(summary = "Returns a list of jobs with salaries greater than the specified salary sorted by jobTitle", operationId = "findJobOffersBySalaryFromGreaterThanEqualOrderByJobTitleLimit")
    public String findJobOffersBySalaryFromGreaterThanEqualOrderByJobTitleLimit(@RequestParam BigDecimal salaryFrom, @RequestParam int limit) {
        var time = System.currentTimeMillis();
        log.debug("findJobOffersBySalaryFromGreaterThanEqualOrderByJobTitleLimit Native SQL start");

        nativeSqlPerformanceService.findJobOffersBySalaryFromGreaterThanEqualOrderByJobTitleLimit(salaryFrom, limit);

        log.debug("findJobOffersBySalaryFromGreaterThanEqualOrderByJobTitleLimit Spring Data JPA stop {} ms", System.currentTimeMillis() - time);
        return  "Getting a list of " + limit + " jobs with salaries greater than: " + salaryFrom + " using Native SQL took: " + (System.currentTimeMillis() - time) + "ms";
    }

    @GetMapping("list/job-type-in")
    @Operation(summary = "Returns a list of jobs with type in any specified types", operationId = "findAllJobOffersWithJobTypeInAnyList")
    public String findAllJobOffersWithJobTypeInAnyList() {
        var time = System.currentTimeMillis();
        log.debug("findAllJobOffersWithJobTypeInAnyList Native SQL start");

        List<Object[]> resultList = nativeSqlPerformanceService.findAllJobOffersWithJobTypeInAnyList(TYPES1, TYPES2);

        log.debug("findAllJobOffersWithJobTypeInAnyList Native SQL stop {} ms", System.currentTimeMillis() - time);
        return  "Getting a list of jobs with type in any list of : " + TYPES1 +" " + TYPES2 + " using Native SQL took: " + (System.currentTimeMillis() - time) + "ms, list size: " + resultList.size();
    }

}
