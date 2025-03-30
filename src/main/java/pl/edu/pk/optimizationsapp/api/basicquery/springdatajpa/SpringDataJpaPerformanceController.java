package pl.edu.pk.optimizationsapp.api.basicquery.springdatajpa;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
