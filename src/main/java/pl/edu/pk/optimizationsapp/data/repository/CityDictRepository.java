package pl.edu.pk.optimizationsapp.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import pl.edu.pk.optimizationsapp.api.database_3_3.indexes_1.dto.CityDto;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.CityDict;

import java.util.List;

public interface CityDictRepository extends JpaRepository<CityDict, String>, JpaSpecificationExecutor<CityDict> {


    @Query("""
            SELECT new pl.edu.pk.optimizationsapp.api.database_3_3.indexes_1.dto.CityDto (m.id, m.nazwaMiejsc, m.countyDict.name, m.countyDict.kodSprawozd, m.VoivodeshipDict.name, "")
            FROM CityDict m
            WHERE m.nazwaMiejscUpperAscii like concat(:name, "%")
            AND m.dataOd <= CURRENT_DATE
            AND (m.dataDo IS NULL OR m.dataDo >= CURRENT_DATE )
            ORDER BY
            CASE
                    WHEN m.miastoWies = 'T' THEN 0
                    ELSE 1
                END,
            m.nazwaMiejsc
            """)
    List<CityDto> findByNameUpperAscii(String name);

    @Query("""
            SELECT new pl.edu.pk.optimizationsapp.api.database_3_3.indexes_1.dto.CityDto (m.id, m.nazwaMiejsc, m.countyDict.name, m.countyDict.kodSprawozd, m.VoivodeshipDict.name, "")
            FROM CityDict m
             WHERE cast(unaccent(m.nazwaMiejsc) as string ) ilike concat(:name, "%")
            AND m.dataOd <= CURRENT_DATE
            AND (m.dataDo IS NULL OR m.dataDo >= CURRENT_DATE )
            ORDER BY
            CASE
                    WHEN m.miastoWies = 'T' THEN 0
                    ELSE 1
                END,
            m.nazwaMiejsc
            """)
    List<CityDto> findByUnaccentName(String name);


}
