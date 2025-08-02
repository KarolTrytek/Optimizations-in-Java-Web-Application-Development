package pl.edu.pk.optimizationsapp.api.database_3_3.indexes_1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pk.optimizationsapp.api.database_3_3.indexes_1.dto.DataFeedStatusUnitDto;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequestMapping(value = "report/")
@Tag(name = "Report API")
@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/data-feed-status")
    @Operation(summary = "Retur Data Feed Status Report", operationId = "getDataFeedStatusReport")
    public List<DataFeedStatusUnitDto> getDataFeedStatusReport(@RequestParam(required = false) Long unitId,
                                                               @RequestParam(required = false) LocalDate startDate,
                                                               @RequestParam(required = false) LocalDate endDate,
                                                               @RequestParam(defaultValue = "true") boolean optimizedQuery) {
        var time = System.currentTimeMillis();
        log.debug("getDataFeedStatusReport start");

        var report = reportService.getDataFeedStatusReport(unitId, startDate, endDate, optimizedQuery);

        log.debug("getDataFeedStatusReport"
                + (optimizedQuery ? "optimized" : "not optimized")
                + (unitId == null ? "for all units" : "for unit id " + unitId)
                + " date range: " + startDate + " - " + endDate
                + " stop {} ms", System.currentTimeMillis() - time);

        return report;

    }

}
