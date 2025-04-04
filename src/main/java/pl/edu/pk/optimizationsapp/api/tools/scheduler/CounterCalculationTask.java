package pl.edu.pk.optimizationsapp.api.tools.scheduler;

import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CounterCalculationTask {

    private final CounterService counterService;

    @Scheduled(cron = "${optimization-app.proces.calculate-counters.refreschTime}")
    @SchedulerLock(name = "calculateCounters")
    @Transactional
    public void przygotujLiczniki() {
        this.counterService.calculateCounters();
    }

}
