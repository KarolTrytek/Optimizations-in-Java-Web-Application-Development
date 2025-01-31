package pl.edu.pk.optimizationsapp.utils;

import lombok.experimental.UtilityClass;

import java.text.NumberFormat;
import java.util.Locale;

@UtilityClass
public class NumberUtils {

    public static final NumberFormat NUMBER_FORMATTER = NumberFormat.getNumberInstance(Locale.of("pl", "PL"));




}
