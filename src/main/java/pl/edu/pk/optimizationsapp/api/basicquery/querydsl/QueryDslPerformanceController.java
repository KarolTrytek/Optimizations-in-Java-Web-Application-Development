package pl.edu.pk.optimizationsapp.api.basicquery.querydsl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping(value = "oferta/")
//@Tag(name = "Oferta")
@RestController
@RequiredArgsConstructor
public class QueryDslPerformanceController {

    private final QueryDslPerformanceService queryDslPerformanceService;


    @GetMapping("{id}")
//    @Operation(summary = "Zwraca listę ofert", operationId = "getOfertyPracyById")
    public long getOfertyPracyById(@PathVariable() Long id) {
        var time = System.currentTimeMillis();
        log.debug("getOfertyPracyById one attempt start");

        var response = queryDslPerformanceService.getOfertaPracyById(id);

        log.debug("getOfertyPracyById one attempt stop {} ms", System.currentTimeMillis() - time);
        return response.getId();
    }

    @GetMapping("{id}/attempts/{attempts}")
//    @Operation(summary = "Zwraca listę ofert", operationId = "getOfertyPracyById")
    public String getOfertyPracyByIdForMoreAttempts(@PathVariable(value = "id") Long id, @PathVariable(value = "attempts") Integer attempts) {
        var time = System.currentTimeMillis();
        log.debug("findOfertyPracyById start, attempts {}", attempts);

        for (int i = 0; i < attempts; i++) {
            queryDslPerformanceService.getOfertaPracyById(id);
        }

        log.debug("getOfertyPracyById stop {} ms, attempts {}", System.currentTimeMillis() - time, attempts);
        return "Getting  an job offer with id: " + id + " for " + attempts + " attempts using Querydsl took: " + (System.currentTimeMillis() - time) + "ms";
    }

}
