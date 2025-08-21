package pl.edu.pk.optimizationsapp.api.tools_3.scheduler_3_4;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pk.optimizationsapp.api.tools_3.scheduler_3_4.dto.CounterDto;
import pl.edu.pk.optimizationsapp.data.domain.ofz.LanguageEnum;

@Slf4j
@RequestMapping(value = "counter")
@Tag(name = "Counter")
@RestController
@RequiredArgsConstructor
public class CounterController {

    private final CounterService counterService;

    @GetMapping("separate-queries")
    @Operation(summary = "Count job offers, proposals and jobs using separate queries", operationId = "getCountersFromSeparateQueries")
    public CounterDto getCountersFromSeparateQueries(@RequestParam(name = "language", required = false, defaultValue = "PL") LanguageEnum language) {
        return counterService.getCountersFromSeparateQueries(language);
    }

    @GetMapping("joined-queries")
    @Operation(summary = "Count job offers, proposals and jobs using joined queries", operationId = "getCountersFromJoinedQueries")
    public CounterDto getCountersFromJoinedQueries(@RequestParam(name = "jezyk", required = false, defaultValue = "PL") LanguageEnum language) {
        return counterService.getCountersFromJoinedQueries(language);
    }

    @GetMapping("with-schedlock")
    @Operation(summary = "Count job offers, proposals and jobs using schedlock", operationId = "getCountersWithSchedlock")
    public CounterDto getCountersWithSchedlock(@RequestParam(name = "jezyk", required = false, defaultValue = "PL") LanguageEnum language) {
        return counterService.getCountersWithSchedlock(language);
    }

    @GetMapping("with-schedlock/from-cache")
    @Operation(summary = "Count job offers, proposals and jobs using schedlock and caching ", operationId = "getCountersUsingCache")
    public CounterDto getCountersWithSchedlockFromCache(@RequestParam(name = "jezyk", required = false, defaultValue = "PL") LanguageEnum language) {
        return counterService.getCountersWithSchedlockFromCache(language);
    }

}
