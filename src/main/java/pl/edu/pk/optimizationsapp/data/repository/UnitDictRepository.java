package pl.edu.pk.optimizationsapp.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.pk.optimizationsapp.api.database_3_3.indexes_1.dto.DataFeedStatusUnitDto;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.SlPlacowka;

import java.time.LocalDate;
import java.util.List;

public interface UnitDictRepository extends JpaRepository<SlPlacowka, Long>, JpaSpecificationExecutor<SlPlacowka> {

    @Query(value =
            """
SELECT
    sl_placowka.kod_placowki,
    sl_placowka.opis_skr,
    sl_placowka.typ_podmiotu,
    MAX(CASE WHEN def.typ = 'OPN' THEN komunikat.data_otrzymania END) AS lastJobOffersMessage,
    MAX(CASE WHEN def.typ = 'WYD' THEN komunikat.data_otrzymania END) AS lastEventsMessage
FROM
    slowniki.sl_placowka
        LEFT JOIN
    adm.komunikaty_wejsciowe komunikat ON sl_placowka.id = komunikat.id_placowki
        LEFT JOIN
    adm.def_komunikatow def ON komunikat.id_typu_komunikatu = def.id AND def.typ IN ('WYD', 'OPN') AND def.status = 'obsługiwany'
WHERE
    NOT EXISTS (
        SELECT 1
        FROM adm.komunikaty_wejsciowe k
                 inner join adm.def_komunikatow def ON k.id_typu_komunikatu = def.id
        WHERE
            k.id_placowki = sl_placowka.id
          AND k.data_otrzymania >= :startDate
          AND k.data_otrzymania < :endDate
          AND def.typ IN ('WYD', 'OPN') AND def.status = 'obsługiwany'
    )
  AND NOT (sl_placowka.typ_podmiotu = 'UP' AND sl_placowka.czy_pup = false AND sl_placowka.czy_wup = false)
  and sl_placowka.id = :unitId
GROUP BY
    sl_placowka.kod_placowki,
    sl_placowka.opis_skr,
    sl_placowka.typ_podmiotu
ORDER BY
    lastEventsMessage NULLS LAST;
"""
            , nativeQuery = true)
    List<DataFeedStatusUnitDto> findDataForDataFeedStatusReportOptimizedQuery(@Param("unitId") Long unitId,
                                                                @Param("startDate") LocalDate startDate,
                                                                @Param("endDate") LocalDate endDate);

    @Query(value =
            """
SELECT
    sl_placowka.kod_placowki,
    sl_placowka.opis_skr,
    sl_placowka.typ_podmiotu,
    MAX(CASE WHEN def.typ = 'OPN' THEN komunikat.data_otrzymania END) AS lastJobOffersMessage,
    MAX(CASE WHEN def.typ = 'WYD' THEN komunikat.data_otrzymania END) AS lastEventsMessage
FROM
    slowniki.sl_placowka
        LEFT JOIN
    adm.komunikaty_wejsciowe komunikat ON sl_placowka.id = komunikat.id_placowki
        LEFT JOIN
    adm.def_komunikatow def ON komunikat.id_typu_komunikatu = def.id AND def.typ IN ('WYD', 'OPN') AND def.status = 'obsługiwany'
WHERE
    NOT EXISTS (
        SELECT 1
        FROM adm.komunikaty_wejsciowe k
                 inner join adm.def_komunikatow def ON k.id_typu_komunikatu = def.id
        WHERE
            k.id_placowki = sl_placowka.id
          AND k.data_otrzymania >= :startDate
          AND k.data_otrzymania < :endDate
          AND def.typ IN ('WYD', 'OPN') AND def.status = 'obsługiwany'
    )
  AND NOT (sl_placowka.typ_podmiotu = 'UP' AND sl_placowka.czy_pup = false AND sl_placowka.czy_wup = false)
GROUP BY
    sl_placowka.kod_placowki,
    sl_placowka.opis_skr,
    sl_placowka.typ_podmiotu
ORDER BY
    lastEventsMessage NULLS LAST;
"""
            , nativeQuery = true)
    List<DataFeedStatusUnitDto> findDataForDataFeedStatusReportOptimizedQuery(@Param("startDate") LocalDate startDate,
                                                                @Param("endDate") LocalDate endDate);


    @Query(value =
            """
SELECT
    slPlacowka.kod_placowki,
    slPlacowka.opis_skr,
    slPlacowka.typ_podmiotu,
    TO_CHAR(CAST(MAX(CASE
                         WHEN def.typ = 'OPN'
                             THEN komunikat.data_otrzymania
        END) as timestamp(0)), 'YYYY-MM-DD HH24:MI:SS') AS dataOstatniKomunikatOPN,
    TO_CHAR(CAST(MAX(CASE
                         WHEN def.typ = 'WYD'
                             THEN komunikat.data_otrzymania
        END) as timestamp(0)), 'YYYY-MM-DD HH24:MI:SS') AS dataOstatniKomunikatWYD
FROM
    slowniki.sl_placowka slPlacowka
        LEFT JOIN
    adm.komunikaty_wejsciowe komunikat
    ON slPlacowka.id = komunikat.id_placowki
        AND komunikat.data_otrzymania <= :startDate
        LEFT JOIN
    adm.def_komunikatow def
    ON komunikat.id_typu_komunikatu = def.id
        AND def.typ IN ('WYD', 'OPN')
        AND def.status = 'obsługiwany'
WHERE
    NOT (slPlacowka.typ_podmiotu = 'UP'
        AND slPlacowka.czy_pup = false
        AND slPlacowka.czy_wup = false)
        AND slPlacowka.id = :unitId
  AND (
    NOT EXISTS (SELECT
                    1
                FROM
                    adm.komunikaty_wejsciowe kOPN
                        INNER JOIN
                    adm.def_komunikatow def
                    ON kOPN.id_typu_komunikatu = def.id
                WHERE
                    kOPN.id_placowki = slPlacowka.id
                  AND kOPN.data_otrzymania >= :startDate
                  AND kOPN.data_otrzymania < :endDate
                  AND def.typ = 'OPN'
                  AND def.status = 'obsługiwany')
        OR NOT EXISTS (SELECT
                           1
                       FROM
                           adm.komunikaty_wejsciowe kWYD
                               INNER JOIN
                           adm.def_komunikatow def
                           ON kWYD.id_typu_komunikatu = def.id
                       WHERE
                           kWYD.id_placowki = slPlacowka.id
                         AND kWYD.data_otrzymania >= :startDate
                         AND kWYD.data_otrzymania < :endDate
                         AND def.typ = 'WYD'
                         AND def.status = 'obsługiwany')
    )
GROUP BY
    slPlacowka.kod_placowki,
    slPlacowka.opis_skr,
    slPlacowka.typ_podmiotu
ORDER BY
    dataOstatniKomunikatWYD DESC NULLS LAST;
"""
            , nativeQuery = true)
    List<DataFeedStatusUnitDto> findDataForDataFeedStatusReportNotOptimizedQuery(@Param("unitId") Long unitId,
                                                                              @Param("startDate") LocalDate startDate,
                                                                              @Param("endDate") LocalDate endDate);

    @Query(value =
            """
SELECT
    slPlacowka.kod_placowki,
    slPlacowka.opis_skr,
    slPlacowka.typ_podmiotu,
    CAST(MAX(CASE
                         WHEN def.typ = 'OPN'
                             THEN komunikat.data_otrzymania
        END) as timestamp(0)) AS lastJobOffersMessage,
    CAST(MAX(CASE
                         WHEN def.typ = 'WYD'
                             THEN komunikat.data_otrzymania
        END) as timestamp(0)) AS lastEventsMessage
FROM
    slowniki.sl_placowka slPlacowka
        LEFT JOIN
    adm.komunikaty_wejsciowe komunikat
    ON slPlacowka.id = komunikat.id_placowki
        AND komunikat.data_otrzymania <= :startDate
        LEFT JOIN
    adm.def_komunikatow def
    ON komunikat.id_typu_komunikatu = def.id
        AND def.typ IN ('WYD', 'OPN')
        AND def.status = 'obsługiwany'
WHERE
    NOT (slPlacowka.typ_podmiotu = 'UP'
        AND slPlacowka.czy_pup = false
        AND slPlacowka.czy_wup = false)
  AND (
    NOT EXISTS (SELECT
                    1
                FROM
                    adm.komunikaty_wejsciowe kOPN
                        INNER JOIN
                    adm.def_komunikatow def
                    ON kOPN.id_typu_komunikatu = def.id
                WHERE
                    kOPN.id_placowki = slPlacowka.id
                  AND kOPN.data_otrzymania >= :startDate
                  AND kOPN.data_otrzymania < :endDate
                  AND def.typ = 'OPN'
                  AND def.status = 'obsługiwany')
        OR NOT EXISTS (SELECT
                           1
                       FROM
                           adm.komunikaty_wejsciowe kWYD
                               INNER JOIN
                           adm.def_komunikatow def
                           ON kWYD.id_typu_komunikatu = def.id
                       WHERE
                           kWYD.id_placowki = slPlacowka.id
                         AND kWYD.data_otrzymania >= :startDate
                         AND kWYD.data_otrzymania < :endDate
                         AND def.typ = 'WYD'
                         AND def.status = 'obsługiwany')
    )
GROUP BY
    slPlacowka.kod_placowki,
    slPlacowka.opis_skr,
    slPlacowka.typ_podmiotu
ORDER BY
    lastEventsMessage DESC NULLS LAST;
"""
            , nativeQuery = true)
    List<DataFeedStatusUnitDto> findDataForDataFeedStatusReportNotOptimizedQuery(@Param("startDate") LocalDate startDate,
                                                                                 @Param("endDate") LocalDate endDate);

}
