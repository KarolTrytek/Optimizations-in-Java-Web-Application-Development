package pl.edu.pk.optimizationsapp.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriUtils;

import java.util.ArrayList;
import java.util.List;

public class MapUtils {

	private MapUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static final String POLSKA = "Polska";


	public static String getGoogleMapsUrl(String miejscowosc, String gmina, String powiat, String wojewodztwo, String ulica, String nrBudynku,
			String nazwaKraju) {
		List<String> urlParams = new ArrayList<>();
		if (StringUtils.isNotBlank(miejscowosc)) {
			urlParams.add(miejscowosc);
			if (StringUtils.isNotBlank(ulica)) {
				urlParams.add(ulica);
			}
			if (StringUtils.isNotBlank(nrBudynku)) {
				urlParams.add(" " + nrBudynku);
			}
		}
		if (StringUtils.isNotBlank(powiat)) {
			urlParams.add(powiat.replaceAll("(?i)m\\.st\\. |m\\. ", ""));
		}
		if (StringUtils.isNotBlank(wojewodztwo)) {
			urlParams.add(wojewodztwo);
		}
		if (StringUtils.isNotBlank(gmina)) {
			urlParams.add(powiat.replaceAll("(?i)m\\.st\\. |m\\. ", ""));
		}
		if (StringUtils.isNotBlank(nazwaKraju)) {
			urlParams.add(nazwaKraju);
		}
		if (CollectionUtils.isEmpty(urlParams)) {
			return null;
		}
		return "https://maps.google.pl/maps?q=" + UriUtils.encode(StringUtils.join(urlParams, ", "), "utf-8");
	}

	public static String getOpenStreetMapUrl(String miejscowosc, String wojewodztwo, String ulica, String nrBudynku, String nazwaKraju) {
		List<String> urlParams = new ArrayList<>();
		if (StringUtils.isNotBlank(ulica)) {
			urlParams.add(ulica);
			if (StringUtils.isNotBlank(nrBudynku)) {
				urlParams.add(" " + nrBudynku);
			}
		}
		if (StringUtils.isNotBlank(miejscowosc)) {
			urlParams.add(miejscowosc);
			if (!StringUtils.isNotBlank(ulica) && StringUtils.isNotBlank(nrBudynku)) {
				urlParams.add(" " + nrBudynku);
			}
		}
		if (StringUtils.isNotBlank(wojewodztwo)) {
			urlParams.add(wojewodztwo);
		}
		if (StringUtils.isNotBlank(nazwaKraju)) {
			urlParams.add(nazwaKraju);
		}
		if (CollectionUtils.isEmpty(urlParams)) {
			return null;
		}
		return "https://www.openstreetmap.org/search?query=" + UriUtils.encode(StringUtils.join(urlParams, ", "), "utf-8");
	}

}
