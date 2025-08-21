package pl.edu.pk.optimizationsapp.utils;

import lombok.experimental.UtilityClass;
import pl.edu.pk.optimizationsapp.data.domain.ofz.TypOfertyEnum;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

@UtilityClass
public class NumberUtils {

    public static final NumberFormat NUMBER_FORMATTER = NumberFormat.getNumberInstance(Locale.of("pl", "PL"));



    public static String format(BigDecimal wynagOd, BigDecimal wynagDo, String waluta, String typOferty, String kodJezyka) {
        JezykPropertiesHelper jezykPropertiesHelper = new JezykPropertiesHelper();
        String wynagrodzenieOd = zwrocKwote(wynagOd);
        String wynagrodzenieDo = zwrocKwote(wynagDo);
        StringBuilder wynagrodzenieSB = new StringBuilder();
        if ((TypOfertyEnum.STAZ_UP.name().equals(typOferty)
                || TypOfertyEnum.PRZYG_ZAW.name().equals(typOferty))) {
            if (wynagrodzenieDo != null) {
                wynagrodzenieSB.append(wynagrodzenieDo);
            }
        } else if (TypOfertyEnum.PRAKT_STUDENT.name().equals(typOferty) || TypOfertyEnum.WOLONTARIAT.name().equals(typOferty)) {
            wynagrodzenieSB.append(jezykPropertiesHelper.getProperties(kodJezyka).getProperty("cbop.i18n.wynagrodzenieOfertyFormatter.nieodplatny"));
        } else {
            pozostalyTypOferty(kodJezyka, wynagrodzenieOd, wynagrodzenieDo, wynagrodzenieSB);
        }
        if (!wynagrodzenieSB.isEmpty() && !TypOfertyEnum.PRAKT_STUDENT.name().equals(typOferty) && !TypOfertyEnum.WOLONTARIAT.name().equals(typOferty)) {
            wynagrodzenieSB.append(" ").append(waluta != null ? waluta : "PLN");
        }
        return wynagrodzenieSB.toString();
    }

    private static void pozostalyTypOferty(String kodJezyka, String wynagrodzenieOd, String wynagrodzenieDo, StringBuilder wynagrodzenieSB) {
        if (wynagrodzenieOd != null && wynagrodzenieDo != null && !wynagrodzenieOd.equals(wynagrodzenieDo)) {
            zmapujKwotyOdDo(wynagrodzenieOd, wynagrodzenieDo, wynagrodzenieSB, kodJezyka);
        } else if (wynagrodzenieOd != null && wynagrodzenieOd.equals(wynagrodzenieDo)) {
            zmapujKwote(wynagrodzenieOd, wynagrodzenieSB);
        } else if (wynagrodzenieOd != null) {
            zmapujKwoteOd(wynagrodzenieOd, wynagrodzenieSB, kodJezyka);
        } else if (wynagrodzenieDo != null) {
            zmapujKwoteDo(wynagrodzenieDo, wynagrodzenieSB);
        }
    }

    private static String zwrocKwote(BigDecimal kwota) {
        String kwotaString = null;
        if (kwota != null && kwota.compareTo(BigDecimal.ZERO) > 0) {
            kwotaString = formatAmount(kwota);
        }
        return kwotaString;
    }

    public static String formatAmount(BigDecimal amount) {
        DecimalFormat decimalFormatter = (DecimalFormat) NumberFormat.getNumberInstance(Locale.of("pl", "pl"));
        decimalFormatter.setGroupingUsed(true);

        if (amount.compareTo(new BigDecimal("1000")) >= 0 && amount.stripTrailingZeros().scale() <= 0) {
            decimalFormatter.setMinimumFractionDigits(0);
            decimalFormatter.setMaximumFractionDigits(0);
        } else {
            decimalFormatter.setMinimumFractionDigits(2);
            decimalFormatter.setMaximumFractionDigits(2);
        }

        return decimalFormatter.format(amount);
    }

    private static void zmapujKwotyOdDo(String wynagrodzenieOd, String wynagrodzenieDo, StringBuilder wynagrodzenieSB, String kodJezyka) {
        JezykPropertiesHelper jezykPropertiesHelper = new JezykPropertiesHelper();
        wynagrodzenieSB.append(jezykPropertiesHelper.getProperties(kodJezyka).getProperty("cbop.i18n.wynagrodzenieOfertyFormatter.od")).append(" ");
        wynagrodzenieSB.append(wynagrodzenieOd);
        wynagrodzenieSB.append(" ");
        wynagrodzenieSB.append(jezykPropertiesHelper.getProperties(kodJezyka).getProperty("cbop.i18n.wynagrodzenieOfertyFormatter.do")).append(" ");
        wynagrodzenieSB.append(wynagrodzenieDo);
    }

    private static void zmapujKwoteOd(String kwota, StringBuilder wynagrodzenieSB, String kodJezyka) {
        JezykPropertiesHelper jezykPropertiesHelper = new JezykPropertiesHelper();
        wynagrodzenieSB.append(jezykPropertiesHelper.getProperties(kodJezyka).getProperty("cbop.i18n.wynagrodzenieOfertyFormatter.od")).append(" ");
        wynagrodzenieSB.append(kwota);
    }

    private static void zmapujKwote(String kwota, StringBuilder wynagrodzenieSB) {
        wynagrodzenieSB.append(kwota);
    }

    private static void zmapujKwoteDo(String kwota, StringBuilder wynagrodzenieSB) {
        wynagrodzenieSB.append(kwota);
    }
}
