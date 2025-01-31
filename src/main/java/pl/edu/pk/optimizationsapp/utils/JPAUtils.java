package pl.edu.pk.optimizationsapp.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.util.StringUtils.hasLength;


/**
 * Klasa pomocnicza do budowania listy predykatów
 *
 */
public class JPAUtils {

	private JPAUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Równe lub zaczynające się jak parametr.
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setEqualOrLikeParam(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field,
			String value) {
		if (hasLength(value)) {
			if (value.contains("%")) {
				predicates.add(cb.like(path.get(field), value));
			} else {
				predicates.add(cb.equal(path.get(field), value));
			}
		}
	}

	/**
	 * Zawierające parametr.
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setContainWithIgnoreCase(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field, String value) {
		if (hasLength(value)) {
			predicates.add(cb.like(cb.upper(path.get(field)), "%"+ value.toUpperCase() + "%"));
		}
	}
	/**
	 * Zawierające parametr.
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param parent     klasa nadrzędna
	 * @param child      klasa potomna
	 * @param value      wartość do porównania
	 */
	public static void setContainWithIgnoreCaseWithChild(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String parent,
			String child, String value) {
		if (hasLength(value)) {
			predicates.add(cb.like(cb.upper(path.get(parent).get(child)), "%"+ value.toUpperCase() + "%"));
		}
	}


	/**
	 * Zaczynające się jak parametr.
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setLikeParam(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field,
			String value) {
		if (hasLength(value)) {
			predicates.add(cb.like(path.get(field), value));
		}
	}

	/**
	 * Równe lub zaczynające się jak parametr ze zignorowaniem wielkości znaków.
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setEqualOrLikeParamWithIgnoreCase(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field,
			String value) {
		if (hasLength(value)) {
			if (value.contains("%")) {
				predicates.add(cb.like(cb.upper(path.get(field)), value.toUpperCase()));
			} else {
				predicates.add(cb.equal(cb.upper(path.get(field)), value.toUpperCase()));
			}
		}
	}

	/**
	 * Równe lub zaczynające się jak parametr ze zignorowaniem wielkości znaków.
	 *
	 * @param expression wyrażenie
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param value      wartość do porównania
	 */
	public static void setEqualOrLikeParamWithIgnoreCase(Expression<String> expression, CriteriaBuilder cb, List<Predicate> predicates, String value) {
		if (hasLength(value)) {
			if (value.contains("%")) {
				predicates.add(cb.like(cb.upper(expression), value.toUpperCase()));
			} else {
				predicates.add(cb.equal(cb.upper(expression), value.toUpperCase()));
			}
		}
	}

	/**
	 * Czy istnieje.
	 *
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param subquery   podzapytanie
	 */
	public static void setExists(CriteriaBuilder cb, List<Predicate> predicates, Subquery<?> subquery) {
		predicates.add(cb.exists(subquery));
	}

	/**
	 * Czy równe
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setEqual(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field, Object value) {
		if (value != null) {
			predicates.add(cb.equal(path.get(field), value));
		}
	}

	/**
	 * Czy różne
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setNotEqual(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field, Object value) {
		if (value != null) {
			predicates.add(cb.notEqual(path.get(field), value));
		}
	}

	/**
	 * Czy zawiera się lub równe.
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param list       lista do porównania
	 */
	public static void setInOrEqual(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field, List<?> list) {
		if (list != null && !list.isEmpty()) {
			if (list.size() == 1) {
				predicates.add(cb.equal(path.get(field), list.getFirst()));
			} else {
				predicates.add(path.get(field).in(list));
			}
		}
	}

	/**
	 * Czy niezawiera się.
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param list       lista do porównania
	 */
	public static void setNotIn(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field, List<?> list) {
		if (list != null && !list.isEmpty()) {
			predicates.add(cb.not(path.get(field).in(list)));
		}
	}

	/**
	 * Czy zawiera się lub równe z klasą potomną.
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param list       lista do porównania
	 * @param parent     klasa nadrzędna
	 * @param child      klasa potomna
	 */
	public static void setInOrEqualWithChild(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String parent,
			String child, List<?> list) {
		if (list != null && !list.isEmpty()) {
			if (list.size() == 1) {
				predicates.add(cb.equal(path.get(parent).get(child), list.getFirst()));
			} else {
				predicates.add(path.get(parent).get(child).in(list));
			}
		}
	}

	/**
	 * Czy zawiera się, jest równe lub nullowe.
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param list       lista do porównania
	 */
	public static void setInOrEqualOrNull(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field,
			List<?> list) {
		if (list != null && !list.isEmpty()) {
			setInOrEqual(path, cb, predicates, field, list);
		} else {
			predicates.add(path.get(field).isNull());
		}
	}

	/**
	 * Czy zawiera się, jest równe lub nullowe z klasą potomną .
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param parent     klasa nadrzędna
	 * @param child      klasa potomna
	 * @param list       lista do porównania
	 */
	public static void setInOrEqualOrNullWithChild(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates,
			String parent, String child, List<?> list) {
		if (list != null && !list.isEmpty()) {
			setInOrEqualWithChild(path, cb, predicates, parent, child, list);
		} else {
			predicates.add(path.get(parent).get(child).isNull());
		}
	}

	/**
	 * Czy równe ze zignorowaną wielkością znaków.
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setEqualWithIgnoreCase(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field,
			String value) {
		if (value != null) {
			predicates.add(cb.equal(cb.upper(path.get(field)), value.toUpperCase()));
		}
	}

	/**
	 * Czy wieksze lub równe
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setGreaterThanOrEqualTo(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates,
			String field, LocalDateTime value) {
		if (value != null) {
			predicates.add(cb.greaterThanOrEqualTo(path.get(field), value));
		}
	}

	/**
	 * Czy wieksze lub równe
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setGreaterThanOrEqualTo(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates,
			String field, LocalTime value) {
		if (value != null) {
			predicates.add(cb.greaterThanOrEqualTo(path.get(field), value));
		}
	}

	/**
	 * Czy wieksze lub równe
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setGreaterThanOrEqualTo(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates,
			String field, Duration value) {
		if (value != null) {
			predicates.add(cb.greaterThanOrEqualTo(path.get(field), value));
		}
	}

	/**
	 * Czy wieksze lub równe
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setGreaterThanOrEqualTo(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates,
			String field, Integer value) {
		if (value != null) {
			predicates.add(cb.greaterThanOrEqualTo(path.get(field), value));
		}
	}

	/**
	 * Czy wieksze lub równe
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setGreaterThanOrEqualTo(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates,
			String field, Long value) {
		if (value != null) {
			predicates.add(cb.greaterThanOrEqualTo(path.get(field), value));
		}
	}

	/**
	 * Czy wieksze lub równe
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setGreaterThanOrEqualTo(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates,
			String field, BigDecimal value) {
		if (value != null) {
			predicates.add(cb.greaterThanOrEqualTo(path.get(field), value));
		}
	}

	/**
	 * Czy wieksze, równe lub nullowe
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setGreaterThanOrEqualToOrNull(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates,
			String field, LocalDateTime value) {
		if (value != null) {
			predicates.add(cb.or(
					cb.greaterThanOrEqualTo(path.get(field), value),
					cb.isNull(path.get(field))));
		}
	}

	/**
	 * Czy wieksze, równe lub nullowe
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setGreaterThanOrEqualToOrNull(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates,
			String field, LocalDate value) {
		if (value != null) {
			predicates.add(cb.or(
					cb.greaterThanOrEqualTo(path.get(field), value),
					cb.isNull(path.get(field))));
		}
	}

	/**
	 * Czy wieksze
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setGreaterThan(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates,
			String field, LocalDateTime value) {
		if (value != null) {
			predicates.add(cb.greaterThan(path.get(field), value));
		}
	}

	/**
	 * Czy wieksze lub równe
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setGreaterThanOrEqualTo(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates,
			String field, LocalDate value) {
		if (value != null) {
			predicates.add(cb.greaterThanOrEqualTo(path.get(field), value));
		}
	}

	/**
	 * Czy większe, równe lub nullowe z klasą potomną .
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param child      klasa potomna
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setGreaterThanOrEqualToWithChild(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates,
			String child, String field, LocalDate value) {
		if (value != null) {
			predicates.add(cb.greaterThanOrEqualTo(path.get(child).get(field), value));
		}
	}

	/**
	 * Czy wieksze lub równe na początku dnia
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setGreaterThanOrEqualToAtStartOfDay(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates,
			String field, LocalDate value) {
		if (value != null) {
			predicates.add(cb.greaterThanOrEqualTo(path.get(field), value.atStartOfDay()));
		}
	}

	/**
	 * Czy wieksze lub równe na początku dnia
	 *
	 * @param expression wyrażenie
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param value      wartość do porównania
	 */
	public static void setGreaterThanOrEqualToAtStartOfDay(Expression<LocalDateTime> expression, CriteriaBuilder cb, List<Predicate> predicates, LocalDate value) {
		if (value != null) {
			predicates.add(cb.greaterThanOrEqualTo(expression, value.atStartOfDay()));
		}
	}

	/**
	 * Czy wieksze lub równe na koniec dnia
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setGreaterThanOrEqualToAtEndOfDayOrNull(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates,
			String field, LocalDate value) {
		if (value != null) {
			predicates.add(cb.or(
					cb.greaterThanOrEqualTo(path.get(field), value.atStartOfDay().plusDays(1)),
					cb.isNull(path.get(field))));
		}
	}

	/**
	 * Czy wieksze
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setGreaterThan(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates,
			String field, LocalDate value) {
		if (value != null) {
			predicates.add(cb.greaterThan(path.get(field), value));
		}
	}


	/**
	 * Czy wieksze
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setGreaterThan(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates,
			String field, Integer value) {
		if (value != null) {
			predicates.add(cb.greaterThan(path.get(field), value));
		}
	}

	/**
	 * Czy wieksze
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setGreaterThan(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates,
			String field, LocalTime value) {
		if (value != null) {
			predicates.add(cb.greaterThan(path.get(field), value));
		}
	}

	/**
	 * Czy mniejsze lub równe
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setLessThanOrEqualTo(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field,
			LocalDateTime value) {
		if (value != null) {
			predicates.add(cb.lessThanOrEqualTo(path.get(field), value));
		}
	}

	/**
	 * Czy mniejsze lub równe
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setLessThanOrEqualTo(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field,
			Duration value) {
		if (value != null) {
			predicates.add(cb.lessThanOrEqualTo(path.get(field), value));
		}
	}

	/**
	 * Czy mniejsze lub równe
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setLessThanOrEqualTo(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field,
			BigDecimal value) {
		if (value != null) {
			predicates.add(cb.lessThanOrEqualTo(path.get(field), value));
		}
	}

	/**
	 * Czy mniejsze lub równe
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setLessThanOrEqualTo(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field,
			LocalTime value) {
		if (value != null) {
			predicates.add(cb.lessThanOrEqualTo(path.get(field), value));
		}
	}

	/**
	 * Czy mniejsze lub równe
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setLessThanOrEqualTo(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field,
			Integer value) {
		if (value != null) {
			predicates.add(cb.lessThanOrEqualTo(path.get(field), value));
		}
	}

	/**
	 * Czy mniejsze lub równe
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setLessThanOrEqualTo(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field,
			Long value) {
		if (value != null) {
			predicates.add(cb.lessThanOrEqualTo(path.get(field), value));
		}
	}

	/**
	 * Czy mniejsze
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setLessThan(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field,
			LocalDateTime value) {
		if (value != null) {
			predicates.add(cb.lessThan(path.get(field), value));
		}
	}

	/**
	 * Czy mniejsze
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setLessThan(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field,
			Integer value) {
		if (value != null) {
			predicates.add(cb.lessThan(path.get(field), value));
		}
	}

	/**
	 * Czy mniejsze
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setLessThan(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field,
			LocalTime value) {
		if (value != null) {
			predicates.add(cb.lessThan(path.get(field), value));
		}
	}

	/**
	 * Czy mniejsze
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setLessThanForTimestamp(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field,
			LocalDateTime value) {
		if (value != null) {
			predicates.add(cb.lessThan(path.get(field), value.plusSeconds(1)));
		}
	}

	/**
	 * Czy w podanym przedziale czasowym
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param fieldFrom  zakres od
	 * @param fieldTo    zakres do
	 * @param yearMonth  rok i miesiąc
	 */
	public static void setRangeBetweenTo(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String fieldFrom,
			String fieldTo, YearMonth yearMonth) {

		if (yearMonth != null) {
			final LocalDate dateFrom = yearMonth.atDay(1);
			final LocalDate dateTo = yearMonth.atEndOfMonth();
			predicates.add(cb.or(
					cb.and(cb.lessThanOrEqualTo(path.get(fieldFrom), dateFrom),
							cb.greaterThanOrEqualTo(path.get(fieldTo), dateTo)),
					cb.and(cb.lessThanOrEqualTo(path.get(fieldFrom), dateTo),
							cb.greaterThanOrEqualTo(path.get(fieldTo), dateFrom))));
		}
	}

	/**
	 * Czy mniejsze lub równe
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setLessThanOrEqualTo(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field,
			LocalDate value) {
		if (value != null) {
			predicates.add(cb.lessThanOrEqualTo(path.get(field), value));
		}
	}

	/**
	 * Czy mniejsze lub równe z klasą potomną
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param child      klasa potomna
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setLessThanOrEqualToWithChild(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String child, String field,
			LocalDate value) {
		if (value != null) {
			predicates.add(cb.lessThanOrEqualTo(path.get(child).get(field), value));
		}
	}

	/**
	 * Czy mniejsze lub równe na koniec dnia
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setLessThanOrEqualToAtEndOfDay(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field,
			LocalDate value) {
		if (value != null) {
			predicates.add(cb.lessThan(path.get(field), value.atStartOfDay().plusDays(1)));
		}
	}

	/**
	 * Czy mniejsze lub równe na koniec dnia
	 *
	 * @param expression wyrazenie
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param value      wartość do porównania
	 */
	public static void setLessThanOrEqualToAtEndOfDay(Expression<LocalDateTime> expression, CriteriaBuilder cb, List<Predicate> predicates, LocalDate value) {
		if (value != null) {
			predicates.add(cb.lessThan(expression, value.atStartOfDay().plusDays(1)));
		}
	}

	/**
	 * Czy mniejsze, równe lub nullowe
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setLessThanOrEqualToOrNull(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates,
			String field, LocalDate value) {
		if (value != null) {
			predicates.add(cb.or(
					cb.lessThanOrEqualTo(path.get(field), value),
					cb.isNull(path.get(field))));
		}
	}

	/**
	 * Czy mniejsze, równe lub nullowe
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setLessThanOrEqualToOrNull(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates,
			String field, Integer value) {
		if (value != null) {
			predicates.add(cb.or(
					cb.lessThanOrEqualTo(path.get(field), value),
					cb.isNull(path.get(field))));
		}
	}

	/**
	 * Czy mniejsze
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setLessThan(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field,
			LocalDate value) {
		if (value != null) {
			predicates.add(cb.lessThan(path.get(field), value));
		}
	}

	/**
	 * Czy równe z klasą potomną
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param child      klasa potomna
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setEqualWithChild(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates,
			String child, String field, Object value) {
		if (value != null) {
			predicates.add(cb.equal(path.get(child).get(field), value));
		}
	}

	/**
	 * Czy równe lub zaczynające się jak ze zignorowaną wielkością znaków z klasą potomną
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param child      klasa potomna
	 * @param field      pole do porównania
	 * @param value      wartość do porównania
	 */
	public static void setEqualOrLikeParamWithIgnoreCaseWithChild(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates,
			String child, String field, String value) {
		if (hasLength(value)) {
			if (value.contains("%")) {
				predicates.add(cb.like(cb.upper(path.get(child).get(field)), value.toUpperCase()));
			} else {
				predicates.add(cb.equal(cb.upper(path.get(child).get(field)), value.toUpperCase()));
			}
		}
	}

	/**
	 * Czy równe lub zaczynające się jak ze zignorowaną wielkością znaków z dwiema polami
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param fieldOne   pole pierwsze do porownania
	 * @param fieldTwo   pole drugie do porownania
	 * @param value      wartość do porownania
	 */
	public static void setEqualWithIgnoreCaseOneOrTwo(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates,
			String fieldOne, String fieldTwo, String value) {
		if (hasLength(value)) {
			predicates.add(cb.or(cb.equal(cb.upper(path.get(fieldOne)), value.toUpperCase()),
					cb.equal(cb.upper(path.get(fieldTwo)), value.toUpperCase())));
		}
	}

	/**
	 * Czy nullowe.
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porownania
	 */
	public static void isNull(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field) {
		predicates.add(cb.isNull(path.get(field)));
	}

	/**
	 * Czy nie nullowe.
	 *
	 * @param path       ścieżka
	 * @param cb         criteria buildier
	 * @param predicates lista predykatów
	 * @param field      pole do porownania
	 */
	public static void isNotNull(Path<?> path, CriteriaBuilder cb, List<Predicate> predicates, String field) {
		predicates.add(cb.isNotNull(path.get(field)));
	}

	/**
	 * Zastosuj kolumny do sortowania.
	 *
	 * @param pageable       parametr generyczny umożliwiający stronicowanie i sortowanie
	 * @param sortfieldAlias aliasy
	 * @return parametr generyczny umożliwiający stronicowanie i sortowanie
	 */
	public static Pageable applyColumnsToSort(Pageable pageable, Map<String, String> sortfieldAlias) {
		Optional<Sort.Order> firstColumn = pageable.getSort().get().findFirst();
		if (firstColumn.isPresent()) {
			String property = firstColumn.get().getProperty();
			String propertyAlias = sortfieldAlias.get(property);
			Sort.Direction direction = firstColumn.get().getDirection();
			if (hasLength(propertyAlias)) {
				List<Sort.Order> orders = Arrays.stream(propertyAlias.split(", "))
						.map(a -> new Sort.Order(direction, a)).toList();
				pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
						Sort.by(orders));
			}
		}
		return pageable;
	}
}
