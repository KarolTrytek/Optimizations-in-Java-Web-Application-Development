package pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import pl.edu.pk.optimizationsapp.data.domain.ofz.Addresses;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOffer;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOfferCounter;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.ElementCentralDict;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.SlElemSlowSys;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.CityDict;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.UnitDict;

import java.math.BigDecimal;

public record JobOfferSortParameters(

        Join<Addresses, CityDict> workplaceCityJoin,
        Join<JobOffer, Addresses> workplaceAddressJoin,
        Join<JobOffer, ElementCentralDict> originCountryJoin,
        Join<JobOffer, UnitDict> unitJoin,
        Join<JobOffer, SlElemSlowSys> employmentTypeJoin,
        Expression<BigDecimal> salarySortExpression,
        Expression<String> employerSortExpression,
        Join<JobOffer, JobOfferCounter> counterJoin
) {
}
