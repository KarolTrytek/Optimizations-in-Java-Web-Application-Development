package pl.edu.pk.optimizationsapp.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.Properties;

@Slf4j
public class JezykPropertiesHelper {

	public final String domyslnyJezyk = "pl";

	private HttpServletRequest request;

	private String wybranyJezykJS;

	public static HttpServletRequest getCurrentHttpRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes instanceof ServletRequestAttributes) {
			return ((ServletRequestAttributes) requestAttributes).getRequest();
		}
		return null;
	}

	public Properties getProperties() {
		Properties properties = new Properties();
		String wybranyJezyk;
		this.request = getCurrentHttpRequest();
		if (this.request != null) {
			if (WebUtils.getCookie(this.request, "wybranyJezyk") != null) {
				wybranyJezyk = WebUtils.getCookie(this.request, "wybranyJezyk").getValue();
			} else {
				wybranyJezyk = "pl";
			}
		} else {
			wybranyJezyk = this.wybranyJezykJS;
		}
		if (wybranyJezyk == null) {
			wybranyJezyk = "pl";
		}
		try {
			String fileName = choosePropertiesFile(wybranyJezyk);
			if (fileName != null && JezykPropertiesHelper.class.getClassLoader().getResourceAsStream(fileName) != null) {
				properties.load(JezykPropertiesHelper.class.getClassLoader().getResourceAsStream(fileName));
			}
		} catch (IOException ioException) {
			log.error("Błąd przy wczytywaniu propertiesów", ioException);
		}
		return properties;
	}

	public Properties getProperties(String jezyk) {
		Properties properties = new Properties();
		try {
			String fileName;
			fileName = choosePropertiesFile(jezyk);
			properties.load(JezykPropertiesHelper.class.getClassLoader().getResourceAsStream(fileName));
		} catch (IOException ioException) {
			log.error("Błąd przy wczytywaniu propertiesów", ioException);
		}
		return properties;
	}

	private String choosePropertiesFile(String wybranyJezyk) {
		String fileName;
		switch (wybranyJezyk) {
		case "en":
			fileName = "Message_en.properties";
			break;
		case "ru":
			fileName = "Message_ru.properties";
			break;
		case "ua":
			fileName = "Message_uk.properties";
			break;
		case "by":
			fileName = "Message_be.properties";
			break;
		default:
			fileName = "Message_pl.properties";
		}
		return fileName;
	}

	public String getWybranyJezykJS() {
		return this.wybranyJezykJS;
	}

	public void setWybranyJezykJS(String wybranyJezykJS) {
		this.wybranyJezykJS = wybranyJezykJS;
	}

	public String getWybranyJezyk() {
		String wybranyJezyk;
		this.request = getCurrentHttpRequest();
		if (this.request != null) {
			if (WebUtils.getCookie(this.request, "wybranyJezyk") != null) {
				wybranyJezyk = WebUtils.getCookie(this.request, "wybranyJezyk").getValue();
			} else {
				wybranyJezyk = "pl";
			}
		} else {
			wybranyJezyk = this.wybranyJezykJS;
		}
		return wybranyJezyk;
	}
}
