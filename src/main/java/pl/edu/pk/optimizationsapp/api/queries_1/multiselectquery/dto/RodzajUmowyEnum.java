package pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum RodzajUmowyEnum {

	UMOWA_O_PRACE_CZAS_OKRESLONY("RPs005|12", "cbop.i18n.kategoriaRodzajUmowyEnum.umowaOPraceCzasOkreslony"),
	UMOWA_O_PRACE_CZAS_NIEOKRESLONY("RPs005|11", "cbop.i18n.kategoriaRodzajUmowyEnum.umowaOPraceCzasNieokreslony"),
	UMOWA_O_PRACE_OKRES_PROBNY("RPs005|13", "cbop.i18n.kategoriaRodzajUmowyEnum.umowaOPraceOkresProbny"),
	POWOLANIE("RPs005|14", "cbop.i18n.kategoriaRodzajUmowyEnum.powolanie"),
	WYBOR("RPs005|15", "cbop.i18n.kategoriaRodzajUmowyEnum.wybor"),
	MIANOWANIE("RPs005|16", "cbop.i18n.kategoriaRodzajUmowyEnum.mianowanie"),
	SPOLDZIELCZA_UMOWA_O_PRACE("RPs005|17", "cbop.i18n.kategoriaRodzajUmowyEnum.umowaSpoldzielcza"),
	UMOWA_NA_CZAS_WYK_PRACY_ARCHIWALNA("RPs005|18", "cbop.i18n.kategoriaRodzajUmowyEnum.umowaOPraceCzasOkreslony"),
	NIE_DOTYCZY("RPs005|19", "cbop.i18n.kategoriaRodzajUmowyEnum.nieDotyczy"),
	UMOWA_ZLECENIE("RPs005|20", "cbop.i18n.kategoriaRodzajUmowyEnum.umowaZlecenieSwiadczenie"),
	UMOWA_AGENCYJNA("RPs005|21", "cbop.i18n.kategoriaRodzajUmowyEnum.umowaAgencyjna"),
	UMOWA_O_PRACE_ZASTEPWSTWO("RPs005|22", "cbop.i18n.kategoriaRodzajUmowyEnum.umowaZastepstwo"),
	UMOWA_O_DZIELO("RPs005|23", "cbop.i18n.kategoriaRodzajUmowyEnum.umowaDzielo"),
	PRAKTYKA_ABSOLWENCKA("RPs005|24", "cbop.i18n.kategoriaRodzajUmowyEnum.praktykaAbsolwencka"),
	PRAKTYKA_STUDENCKA("RPs005|25", "cbop.i18n.kategoriaRodzajUmowyEnum.praktykaStudencka"),
	WOLONTARIAT("RPs005|26", "cbop.i18n.kategoriaRodzajUmowyEnum.wolontariat"),
	KONTRAKT_MENADZERSKI("RPs005|27", "cbop.i18n.kategoriaRodzajUmowyEnum.kontraktMenadzerski"),
	UMOWA_POMOC_ZBIORY("RPs005|28", "cbop.i18n.kategoriaRodzajUmowyEnum.umowaZbiory"),
	DELEGOWANIE("RPs005|29", "cbop.i18n.kategoriaRodzajUmowyEnum.delegowanie");

	private final String id;
	private final String nazwa;

	public static RodzajUmowyEnum getById(String id) {
		return Arrays.stream(RodzajUmowyEnum.values())
				.filter(rodzajUmowy -> rodzajUmowy.id.equals(id))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Nie ma enumu z id " + id));
	}
}
