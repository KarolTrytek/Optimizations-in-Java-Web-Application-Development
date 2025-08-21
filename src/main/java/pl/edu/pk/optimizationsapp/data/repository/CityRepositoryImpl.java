package pl.edu.pk.optimizationsapp.data.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto.LocalityRangeDto;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.CityDict;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.CityDict_;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class CityRepositoryImpl {

    @PersistenceContext
    private EntityManager em;

    public Set<String> findCitiesForRanges(List<LocalityRangeDto> cities) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<String> query = criteriaBuilder.createQuery(String.class);
        Root<CityDict> rootA = query.from(CityDict.class);
        Root<CityDict> rootB = query.from(CityDict.class);

        String transformFunction = "ST_Transform";
        String distanceFunction = "ST_DWithin";

        List<Predicate> predicatesCityList = new ArrayList<>();

        for (LocalityRangeDto city : cities) {
            Predicate distancePredicate = criteriaBuilder.equal(criteriaBuilder.function(
                            distanceFunction,
                            Boolean.class,
                            criteriaBuilder.function(transformFunction, Point.class, rootA.get(CityDict_.CENTRA), criteriaBuilder.literal(3035)),
                            criteriaBuilder.function(transformFunction, Point.class, rootB.get(CityDict_.CENTRA), criteriaBuilder.literal(3035)),
                            criteriaBuilder.literal(city.range() * 1000)),
                    true);

            predicatesCityList.add(criteriaBuilder.and(
                    criteriaBuilder.equal(rootA.get(CityDict_.ID), city.cityId()),
                    distancePredicate
            ));
        }

        query.select(rootB.get(CityDict_.ID)).distinct(true);
        Predicate predicates = criteriaBuilder.or(predicatesCityList.toArray(new Predicate[0]));

        query.where(predicates);

        return this.em.createQuery(query).getResultStream().collect(Collectors.toSet());
    }

}
