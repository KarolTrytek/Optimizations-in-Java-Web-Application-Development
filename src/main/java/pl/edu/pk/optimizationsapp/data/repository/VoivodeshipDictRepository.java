package pl.edu.pk.optimizationsapp.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.VoivodeshipDict;

import java.util.List;

public interface VoivodeshipDictRepository extends JpaRepository<VoivodeshipDict, String>, JpaSpecificationExecutor<VoivodeshipDict> {

    List<VoivodeshipDict> findByNameUpperAsciiStartingWithOrderByName(String voivodeshipName);

    @Query("""
            Select voivodeshipDict
            From VoivodeshipDict voivodeshipDict
            WHERE cast(unaccent(voivodeshipDict.name) as string ) ilike concat(:voivodeshipName, "%")
            """)
    List<VoivodeshipDict> findByUnaccentNameStartingWithOrderByName(String voivodeshipName);
}
