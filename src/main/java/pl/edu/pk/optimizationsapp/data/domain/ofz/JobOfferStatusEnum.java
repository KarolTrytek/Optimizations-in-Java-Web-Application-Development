package pl.edu.pk.optimizationsapp.data.domain.ofz;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public enum JobOfferStatusEnum {

	W_PRZYGOTOWANIU("P"),
	ODRZUCONE("O"),
	ACTIVE("A"),
	ARCHIVE( "H");

	private final String code;

	private static final Map<String, JobOfferStatusEnum> MAP;

    JobOfferStatusEnum(String code) {
        this.code = code;
    }

    /**
	 * Konwertuje tekst na wartość enumeratora.
	 *
	 * @param input the value
	 * @return enumerator
	 */
	public static JobOfferStatusEnum parse(String input) {
		return (input != null ? MAP.get(input) : null);
	}

	static {
		Map<String, JobOfferStatusEnum> map = new ConcurrentHashMap<>();
		for (JobOfferStatusEnum instance : JobOfferStatusEnum.values()) {
			map.put(instance.getCode(), instance);
		}
		MAP = Collections.unmodifiableMap(map);
	}

}
