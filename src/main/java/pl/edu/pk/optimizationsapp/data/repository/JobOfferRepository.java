package pl.edu.pk.optimizationsapp.data.repository;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import pl.edu.pk.optimizationsapp.data.domain.custion.IJobOfferCounter;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOffer;
import pl.edu.pk.optimizationsapp.data.domain.ofz.LanguageEnum;

import java.math.BigDecimal;
import java.util.List;

public interface JobOfferRepository extends JpaRepository<JobOffer, Long>, JpaSpecificationExecutor<JobOffer> {

    @Query(value = """
            select j from ofz.oferty_pracy j where j.id = :id
            """, nativeQuery = true)
    Object[] getByIdNativeQuery(Long id);

    @Query(value = """
            select j from JobOffer j where j.id = :id
            """)
    JobOffer getByIdJPQL(Long id);

    @Query(
            """
                    SELECT
                        COUNT(CASE WHEN o.kodJezyka = 'pl' THEN 1 END) AS licznikAktywnychPropozycjiBezTisPl,
                        COUNT(CASE WHEN o.kodJezyka = 'en' AND o.statusTlumaczenia = 'P' THEN 1 END) AS licznikAktywnychPropozycjiBezTisEn,
                        COUNT(CASE WHEN o.kodJezyka = 'ua' AND o.statusTlumaczenia = 'P' THEN 1 END) AS licznikAktywnychPropozycjiBezTisUa,
                        COUNT(CASE WHEN o.kodJezyka = 'by' AND o.statusTlumaczenia = 'P' THEN 1 END) AS licznikAktywnychPropozycjiBezTisBy,
                        COUNT(CASE WHEN o.kodJezyka = 'ru' AND o.statusTlumaczenia = 'P' THEN 1 END) AS licznikAktywnychPropozycjiBezTisRu,
                        COUNT(CASE WHEN o.kodJezyka = 'pl' AND o.typOferty = 'OFERTA_PRACY' AND sp.typPlacowki = 'UP' THEN 1 END) AS licznikAktywnychOfertPracyUpPl,
                        COUNT(CASE WHEN o.kodJezyka = 'en' AND o.typOferty = 'OFERTA_PRACY' AND sp.typPlacowki = 'UP' AND o.statusTlumaczenia = 'P' THEN 1 END) AS licznikAktywnychOfertPracyUpEn,
                        COUNT(CASE WHEN o.kodJezyka = 'ua' AND o.typOferty = 'OFERTA_PRACY' AND sp.typPlacowki = 'UP' AND o.statusTlumaczenia = 'P' THEN 1 END) AS licznikAktywnychOfertPracyUpUa,
                        COUNT(CASE WHEN o.kodJezyka = 'by' AND o.typOferty = 'OFERTA_PRACY' AND sp.typPlacowki = 'UP' AND o.statusTlumaczenia = 'P' THEN 1 END) AS licznikAktywnychOfertPracyUpBy,
                        COUNT(CASE WHEN o.kodJezyka = 'ru' AND o.typOferty = 'OFERTA_PRACY' AND sp.typPlacowki = 'UP' AND o.statusTlumaczenia = 'P' THEN 1 END) AS licznikAktywnychOfertPracyUpRu,
                        COALESCE(SUM(CASE WHEN o.kodJezyka = 'pl' AND o.typOferty = 'OFERTA_PRACY' AND sp.typPlacowki = 'UP' THEN o.liczbaMiejscPracyOgolem END), 0) AS licznikMiejscPracyAktywnychOfertPracyUpPl,
                        COALESCE(SUM(CASE WHEN o.kodJezyka = 'en' AND o.typOferty = 'OFERTA_PRACY' AND sp.typPlacowki = 'UP' AND o.statusTlumaczenia = 'P' THEN o.liczbaMiejscPracyOgolem END), 0) AS licznikMiejscPracyAktywnychOfertPracyUpEn,
                        COALESCE(SUM(CASE WHEN o.kodJezyka = 'ua' AND o.typOferty = 'OFERTA_PRACY' AND sp.typPlacowki = 'UP' AND o.statusTlumaczenia = 'P' THEN o.liczbaMiejscPracyOgolem END), 0) AS licznikMiejscPracyAktywnychOfertPracyUpUa,
                        COALESCE(SUM(CASE WHEN o.kodJezyka = 'by' AND o.typOferty = 'OFERTA_PRACY' AND sp.typPlacowki = 'UP' AND o.statusTlumaczenia = 'P' THEN o.liczbaMiejscPracyOgolem END), 0) AS licznikMiejscPracyAktywnychOfertPracyUpBy,
                        COALESCE(SUM(CASE WHEN o.kodJezyka = 'ru' AND o.typOferty = 'OFERTA_PRACY' AND sp.typPlacowki = 'UP' AND o.statusTlumaczenia = 'P' THEN o.liczbaMiejscPracyOgolem END), 0) AS licznikMiejscPracyAktywnychOfertPracyUpRu
                    FROM JobOffer o
                    join o.idPlacowkiZasilajacej sp
                    where o.status = 'A' and
                        (o.tisTypInfStarosty IS NULL OR o.tisTypInfStarosty = false OR o.tisSkierKandZgoda = true)
                    and o.typOferty <> 'SPOL_UZYTECZ'
                    """
    )
    IJobOfferCounter getJobOfferCounter();

    @Query(
            """
                    SELECT
                        COUNT(*) AS licznikAktywnychPropozycjiBezTis,
                        COUNT(CASE WHEN o.typOferty = 'OFERTA_PRACY' AND sp.typPlacowki = 'UP' THEN 1 END) AS licznikAktywnychOfertPracyUp,
                        COALESCE(SUM(CASE WHEN  o.typOferty = 'OFERTA_PRACY' AND sp.typPlacowki = 'UP' THEN o.liczbaMiejscPracyOgolem END), 0) AS licznikMiejscPracyAktywnychOfertPracyUp
                    FROM JobOffer o
                    join o.idPlacowkiZasilajacej sp
                    where o.status = 'A'
                    and o.kodJezyka = :language
                    and o.statusTlumaczenia = 'P'
                    and (o.tisTypInfStarosty IS NULL OR o.tisTypInfStarosty = false OR o.tisSkierKandZgoda = true)
                    and o.typOferty <> 'SPOL_UZYTECZ'
                    """
    )
    IJobOfferCounter getJobOfferCounterForLanguage(LanguageEnum language);


    @Query(
            """
                    SELECT
                         COUNT(*) AS licznikAktywnychPropozycjiBezTisPl,
                         COUNT(CASE WHEN o.typOferty = 'OFERTA_PRACY' AND sp.typPlacowki = 'UP' THEN 1 END) AS licznikAktywnychOfertPracyUpPl,
                         COALESCE(SUM(CASE WHEN o.typOferty = 'OFERTA_PRACY' AND sp.typPlacowki = 'UP' THEN o.liczbaMiejscPracyOgolem END), 0) AS licznikMiejscPracyAktywnychOfertPracyUpPl
                    FROM JobOffer o
                    join o.idPlacowkiZasilajacej sp
                    where o.status = 'A'
                    and o.kodJezyka = 'pl'
                    and     (o.tisTypInfStarosty IS NULL OR o.tisTypInfStarosty = false OR o.tisSkierKandZgoda = true)
                    and o.typOferty <> 'SPOL_UZYTECZ'
                    """
    )
    IJobOfferCounter getJobOfferCounterForPolish();


    List<JobOffer> findBySalaryFromGreaterThanEqualOrderByJobTitle(BigDecimal salaryFrom, Limit limit);

    @Query(value = """
            select j from ofz.oferty_pracy j
            where j.wynagr_od >= :salaryFrom
            order by j.stanow_ofer
            limit :limit
            """, nativeQuery = true)
    List<Object[]> findBySalaryFromGreaterThanEqualOrderByJobTitleNativeQuery(BigDecimal salaryFrom, int limit);

    @Query(value = """
            select j from JobOffer j
            where j.salaryFrom >= :salaryFrom
            order by j.jobTitle
            limit :limit
            """)
    List<JobOffer> findBySalaryFromGreaterThanEqualOrderByJobTitleJPQL(BigDecimal salaryFrom, int limit);

}