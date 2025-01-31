package pl.edu.pk.optimizationsapp.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.edu.pk.optimizationsapp.data.domain.adm.Counters;

public interface CountersRepository extends JpaRepository<Counters, String>, JpaSpecificationExecutor<Counters> {
}
