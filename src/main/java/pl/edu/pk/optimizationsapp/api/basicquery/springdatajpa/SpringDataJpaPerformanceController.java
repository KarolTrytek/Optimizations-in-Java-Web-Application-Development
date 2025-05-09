package pl.edu.pk.optimizationsapp.api.basicquery.springdatajpa;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOffer;

import java.math.BigDecimal;
import java.util.List;

import static pl.edu.pk.optimizationsapp.utils.Constants.TYPES1;
import static pl.edu.pk.optimizationsapp.utils.Constants.TYPES2;

@Slf4j
@RequestMapping(value = "spring-data-jpa/basic/")
@Tag(name = "Basic Spring Data JPA")
@RestController
@RequiredArgsConstructor
public class SpringDataJpaPerformanceController {

    private final SpringDataJpaPerformanceService springDataJpaPerformanceService;

    @GetMapping("{id}")
    @Operation(summary = "Returns an offer for the identifier", operationId = "getJobOfferById")
    public String getJobOfferById(@PathVariable() Long id) {
        var time = System.currentTimeMillis();
        log.debug("getOfertyPracyById Spring Data JPA one attempt start");

        springDataJpaPerformanceService.getOfertaPracyById(id);

        log.debug("getOfertyPracyById Spring Data JPA one attempt stop {} ms", System.currentTimeMillis() - time);
        return  "Getting an job offer with id: " + id +" using Spring Data JPA took: " + (System.currentTimeMillis() - time) + "ms";
    }

    @GetMapping("{id}/attempts/{attempts}")
    @Operation(summary = "Searches for an offer for the identifier a specified number of times ", operationId = "getJobOfferByIdForMoreAttempts")
    public String getJobOfferByIdForMoreAttempts(@PathVariable(value = "id") Long id, @PathVariable(value = "attempts") Integer attempts) {
        var time = System.currentTimeMillis();
        log.debug("findOfertyPracyById Spring Data JPA start, attempts {}", attempts);

            springDataJpaPerformanceService.getOfertaPracyById(id, attempts);

        log.debug("getOfertyPracyById stop {} ms, attempts {}", System.currentTimeMillis() - time, attempts);
        return "Getting an job offer with id: " + id + " for " + attempts + " attempts using Spring Data JPA took: " + (System.currentTimeMillis() - time) + "ms";
    }

    @GetMapping("list")
    @Operation(summary = "Returns a list of jobs with salaries greater than the specified salary sorted by jobTitle", operationId = "findJobOffersBySalaryFromGreaterThanEqualOrderByJobTitleLimit")
    public String findJobOffersBySalaryFromGreaterThanEqualOrderByJobTitleLimit(@RequestParam BigDecimal salaryFrom, @RequestParam int limit) {
        var time = System.currentTimeMillis();
        log.debug("findJobOffersBySalaryFromGreaterThanEqualOrderByJobTitleLimit Spring Data JPA start");

        springDataJpaPerformanceService.findJobOffersBySalaryFromGreaterThanEqualOrderByJobTitleLimit(salaryFrom, limit);

        log.debug("findJobOffersBySalaryFromGreaterThanEqualOrderByJobTitleLimit Spring Data JPA stop {} ms", System.currentTimeMillis() - time);
        return  "Getting a list of " + limit + " jobs with salaries greater than: " + salaryFrom + " using Spring Data JPA took: " + (System.currentTimeMillis() - time) + "ms";
    }

    @GetMapping("list/job-type-in")
    @Operation(summary = "Returns a list of jobs with type in any specified types", operationId = "findAllJobOffersWithJobTypeInAnyList")
    public String findAllJobOffersWithJobTypeInAnyList() {
        var time = System.currentTimeMillis();
        log.debug("findAllJobOffersWithJobTypeInAnyList Spring Data JPA start");

        List<JobOffer> resultList = springDataJpaPerformanceService.findAllJobOffersWithJobTypeInAnyList(TYPES1, TYPES2);

        log.debug("findAllJobOffersWithJobTypeInAnyList Spring Data JPA stop {} ms", System.currentTimeMillis() - time);
        return  "Getting a list of jobs with type in any list of : " + TYPES1 +" " + TYPES2 + " using Spring Data JPA took: " + (System.currentTimeMillis() - time) + "ms, list size: " + resultList.size();
    }

}
