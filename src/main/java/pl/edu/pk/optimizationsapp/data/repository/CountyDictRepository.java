package pl.edu.pk.optimizationsapp.data.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.CountyDict;

import java.util.List;

public interface CountyDictRepository extends JpaRepository<CountyDict, String>, JpaSpecificationExecutor<CountyDict> {

    @EntityGraph(attributePaths = "voivodeshipCode")
    List<CountyDict> findByNameUpperAsciiStartingWithOrderByName(String countyName);

    @Query("""
            Select countyDict
            From CountyDict countyDict
            WHERE cast(unaccent(countyDict.name) as string ) ilike concat(:countyName, "%")
            """)
    List<CountyDict> findByUnaccentNameStartingWithOrderByName(String countyName);

}
