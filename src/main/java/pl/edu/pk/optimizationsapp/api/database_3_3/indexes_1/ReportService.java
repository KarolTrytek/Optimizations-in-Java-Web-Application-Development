package pl.edu.pk.optimizationsapp.api.database_3_3.indexes_1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pk.optimizationsapp.api.database_3_3.indexes_1.dto.DataFeedStatusUnitDto;
import pl.edu.pk.optimizationsapp.data.repository.UnitDictRepository;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {

    private final UnitDictRepository unitDictRepository;

    public List<DataFeedStatusUnitDto> getDataFeedStatusReport(Long unitId, LocalDate startDate, LocalDate endDate, boolean optimizedQuery) {
        return optimizedQuery ? getDataFeedStatusReportOptimized(unitId, startDate, endDate) : getDataFeedStatusReportNotOptimized(unitId, startDate, endDate);
    }

    private List<DataFeedStatusUnitDto> getDataFeedStatusReportOptimized(Long unitId, LocalDate startDate, LocalDate endDate){
        return unitId == null ?
                unitDictRepository.findDataForDataFeedStatusReportOptimizedQuery(startDate, endDate)
                : unitDictRepository.findDataForDataFeedStatusReportOptimizedQuery(unitId, startDate, endDate);
    }

    private List<DataFeedStatusUnitDto> getDataFeedStatusReportNotOptimized(Long unitId, LocalDate startDate, LocalDate endDate){
        return unitId == null ?
                unitDictRepository.findDataForDataFeedStatusReportNotOptimizedQuery(startDate, endDate)
                : unitDictRepository.findDataForDataFeedStatusReportNotOptimizedQuery(unitId, startDate, endDate);
    }

}
