package pl.edu.pk.optimizationsapp.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import java.util.List;
import static org.springframework.util.CollectionUtils.isEmpty;

@Data
public class CriteriaQueryUtils {

	private CriteriaQueryUtils(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	private EntityManager entityManager;

	/**
	 * Tworzy nową instancje CriteriaQueryUtils.
	 *
	 * @param entityManager entityManager
	 * @return instancja CriteriaQueryUtils
	 */
	public static CriteriaQueryUtils newInstance(EntityManager entityManager) {
		return new CriteriaQueryUtils(entityManager);
	}

	/**
	 * Zwraca rezultaty.
	 *
	 * @param <T>       parametr generyczny
	 * @param cq        criteriaQuery
	 * @param orders    obiekt sortowania
	 * @param predicate predykaty
	 * @param pageable  parametr generyczny umożliwiający stronicowanie i sortowanie
	 * @return lista rezultatów
	 */
	public <T> List<T> getResultList(CriteriaQuery<T> cq, List<Order> orders, Predicate predicate, Pageable pageable) {

		if (!isEmpty(orders))
			cq.orderBy(orders);

		return getResultList(cq, predicate, pageable);
	}


	private <T> List<T> getResultList(CriteriaQuery<T> cq, Predicate predicate, Pageable pageable) {
		cq.distinct(true).where(predicate);

		TypedQuery<T> query = entityManager.createQuery(cq);
		if (pageable.isPaged()) {
			query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
			query.setMaxResults(pageable.getPageSize());
		}

		return query.getResultList();
	}

}
