package pl.edu.pk.optimizationsapp.api.tools.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pk.optimizationsapp.api.tools.scheduler.dto.CounterDto;
import pl.edu.pk.optimizationsapp.data.domain.ofz.LanguageEnum;

@Slf4j
@RequestMapping(value = "counter")
//@Tag(name = "Oferta")
@RestController
//@Validated
@RequiredArgsConstructor
public class CounterController {

    private final CounterService counterService;

    @GetMapping("separate-queries")
//    @Operation(summary = "Licznik ofert pracy, wydarzeń, propozycji, miejsc pracy", operationId = "getCountersFromSeparateQueries")
    public CounterDto getCountersFromSeparateQueries(@RequestParam(name = "language", required = false, defaultValue = "PL") LanguageEnum language) {
        return counterService.getCountersFromSeparateQueries(language);
    }

    @GetMapping("joined-queries")
//    @Operation(summary = "Licznik ofert pracy, wydarzeń, propozycji, miejsc pracy", operationId = "getCountersFromJoinedQueries")
    public CounterDto getCountersFromJoinedQueries(@RequestParam(name = "jezyk", required = false, defaultValue = "PL") LanguageEnum language) {
        return counterService.getCountersFromJoinedQueries(language);
    }


    @GetMapping("with-schedlock")
//    @Operation(summary = "Licznik ofert pracy, wydarzeń, propozycji, miejsc pracy", operationId = "getCountersWithSchedlock")
    public CounterDto getCountersWithSchedlock(@RequestParam(name = "jezyk", required = false, defaultValue = "PL") LanguageEnum language) {
        return counterService.getCountersWithSchedlock(language);
    }

}
