package pl.edu.pk.optimizationsapp.data.domain.ofz;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public enum StatusOfertyEnum {

	W_PRZYGOTOWANIU("P"),
	ODRZUCONE("O"),
	AKTYWNE("A"),
	ARCHIWALNE( "H");

	private final String code;

	private static final Map<String, StatusOfertyEnum> MAP;

    StatusOfertyEnum(String code) {
        this.code = code;
    }

    /**
	 * Konwertuje tekst na wartość enumeratora.
	 *
	 * @param input the value
	 * @return enumerator
	 */
	public static StatusOfertyEnum parse(String input) {
		return (input != null ? MAP.get(input) : null);
	}

	static {
		Map<String, StatusOfertyEnum> map = new ConcurrentHashMap<>();
		for (StatusOfertyEnum instance : StatusOfertyEnum.values()) {
			map.put(instance.getCode(), instance);
		}
		MAP = Collections.unmodifiableMap(map);
	}

}
