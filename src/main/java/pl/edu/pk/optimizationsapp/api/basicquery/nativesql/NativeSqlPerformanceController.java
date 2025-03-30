package pl.edu.pk.optimizationsapp.api.basicquery.nativesql;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        log.debug("getOfertyPracyById one attempt start");

        nativeSqlPerformanceService.getOfertaPracyById(id);

        log.debug("getOfertyPracyById one attempt stop {} ms", System.currentTimeMillis() - time);
        return  "Getting an job offer with id: " + id +" using Native SQL took: " + (System.currentTimeMillis() - time) + "ms";

    }

    @GetMapping("{id}/attempts/{attempts}")
    @Operation(summary = "Searches for an offer for the identifier a specified number of times ", operationId = "getJobOfferByIdForMoreAttempts")
    public String getJobOfferByIdForMoreAttempts(@PathVariable(value = "id") Long id, @PathVariable(value = "attempts") Integer attempts) {
        var time = System.currentTimeMillis();
        log.debug("findOfertyPracyById start, attempts {}", attempts);

        for (int i = 0; i < attempts; i++) {
            nativeSqlPerformanceService.getOfertaPracyById(id);
        }

        log.debug("getOfertyPracyById stop {} ms, attempts {}", System.currentTimeMillis() - time, attempts);
        return "Getting  an job offer with id: " + id + " for " + attempts + " attempts using Native SQL took: " + (System.currentTimeMillis() - time) + "ms";
    }

}
