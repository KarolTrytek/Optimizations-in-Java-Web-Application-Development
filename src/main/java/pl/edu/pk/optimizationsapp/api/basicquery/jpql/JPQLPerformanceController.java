package pl.edu.pk.optimizationsapp.api.basicquery.jpql;

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

@Slf4j
@RequestMapping(value = "jpgl/basic/")
@Tag(name = "Basic JPQL")
@RestController
@RequiredArgsConstructor
public class JPQLPerformanceController {

    private final JPQLPerformanceService jpqlPerformanceService;

    @GetMapping("{id}")
    @Operation(summary = "Returns an offer for the identifier", operationId = "getJobOfferById")
    public String getJobOfferById(@PathVariable() Long id) {
        var time = System.currentTimeMillis();
        log.debug("getOfertyPracyById one attempt start");

        jpqlPerformanceService.getOfertaPracyById(id);

        log.debug("getOfertyPracyById one attempt stop {} ms", System.currentTimeMillis() - time);
        return  "Getting an job offer with id: " + id +" using JPQL took: " + (System.currentTimeMillis() - time) + "ms";

    }

    @GetMapping("{id}/attempts/{attempts}")
    @Operation(summary = "Searches for an offer for the identifier a specified number of times ", operationId = "getJobOfferByIdForMoreAttempts")
    public String getJobOfferByIdForMoreAttempts(@PathVariable(value = "id") Long id, @PathVariable(value = "attempts") Integer attempts) {
        var time = System.currentTimeMillis();
        log.debug("findOfertyPracyById start, attempts {}", attempts);

        for (int i = 0; i < attempts; i++) {
            jpqlPerformanceService.getOfertaPracyById(id);
        }

        log.debug("getOfertyPracyById stop {} ms, attempts {}", System.currentTimeMillis() - time, attempts);
        return "Getting  an job offer with id: " + id + " for " + attempts + " attempts using JPQL took: " + (System.currentTimeMillis() - time) + "ms";
    }

    @GetMapping("list")
    @Operation(summary = "Returns a list of jobs with salaries greater than the specified salary sorted by jobTitle", operationId = "findJobOffersBySalaryFromGreaterThanEqualOrderByJobTitleLimit")
    public String findJobOffersBySalaryFromGreaterThanEqualOrderByJobTitleLimit(@RequestParam BigDecimal salaryFrom, @RequestParam int limit) {
        var time = System.currentTimeMillis();
        log.debug("findJobOffersBySalaryFromGreaterThanEqualOrderByJobTitleLimit JPQL start");

        jpqlPerformanceService.findJobOffersBySalaryFromGreaterThanEqualOrderByJobTitleLimit(salaryFrom, limit);

        log.debug("findJobOffersBySalaryFromGreaterThanEqualOrderByJobTitleLimit Spring Data JPA stop {} ms", System.currentTimeMillis() - time);
        return  "Getting a list of " + limit + " jobs with salaries greater than: " + salaryFrom + " using JPQL took: " + (System.currentTimeMillis() - time) + "ms";
    }

}
