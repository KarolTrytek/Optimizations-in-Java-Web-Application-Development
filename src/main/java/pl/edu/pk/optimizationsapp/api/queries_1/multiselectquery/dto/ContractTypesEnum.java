package pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum ContractTypesEnum {
	UMOWA_O_PRACE(new String[] { "RPs005|11", "RPs005|12", "RPs005|13", "RPs005|18", "RPs005|22" }, "Umowa o prace", false,
			true),
	UMOWA_ZLECENIE(new String[] { "RPs005|20" }, "umowa zlecenie", true, true),
	UMOWA_O_DZIELO(new String[] { "RPs005|23" }, "umowaDzielo", true, true),
	INNY(new String[] { "RPs005|14", "RPs005|15", "RPs005|16", "RPs005|17", "RPs005|19", "RPs005|21", "RPs005|24", "RPs005|25", "RPs005|26", "RPs005|27",
			"RPs005|28", "RPs005|29" }, "Inny", false, true),
	WSZYSTKIE(new String[] { "RPs005|14", "RPs005|15", "RPs005|16", "RPs005|17", "RPs005|19", "RPs005|21", "RPs005|11", "RPs005|12", "RPs005|13", "RPs005|18",
			"RPs005|22", "RPs005|20", "RPs005|23", "RPs005|24", "RPs005|25", "RPs005|26", "RPs005|27", "RPs005|28", "RPs005|29", },
			"Wszystkie", false, true),
	UMOWA_O_PRACE_CZAS_OKRESLONY(new String[] { "RPs005|12" }, "umowa o Prace Czas Okreslony", true, false),
	UMOWA_O_PRACE_CZAS_NIEOKRESLONY(new String[] { "RPs005|11" }, "umowa o PraceCzasNieokreslony", true, false),
	UMOWA_O_PRACE_OKRES_PROBNY(new String[] { "RPs005|13" }, "umowa o Prace Okres Probny", true, false),
	POWOLANIE(new String[] { "RPs005|14" }, "powolanie", true, false),
	WYBOR(new String[] { "RPs005|15" }, "wybor", true, false),
	MIANOWANIE(new String[] { "RPs005|16" }, "mianowanie", true, false),
	SPOLDZIELCZA_UMOWA_O_PRACE(new String[] { "RPs005|17" }, "umowa Spoldzielcza", true, false),
	NIE_DOTYCZY(new String[] { "RPs005|19" }, "nie Dotyczy", true, false),
	UMOWA_AGENCYJNA(new String[] { "RPs005|21" }, "umowa Agencyjna", true, false),
	UMOWA_O_PRACE_ZASTEPWSTWO(new String[] { "RPs005|22" }, "Zastepstwo", true, false),
	PRAKTYKA_ABSOLWENCKA(new String[] { "RPs005|24" }, "praktyka Absolwencka", true, false),
	PRAKTYKA_STUDENCKA(new String[] { "RPs005|25" }, "praktyka Studencka", true, false),
	WOLONTARIAT(new String[] { "RPs005|26" }, "wolontariat", true, false),
	KONTRAKT_MENADZERSKI(new String[] { "RPs005|27" }, "kontrakt Menadzerski", true, false),
	UMOWA_POMOC_ZBIORY(new String[] { "RPs005|28" }, "umowa Zbiory", true, false),
	DELEGOWANIE(new String[] { "RPs005|29" }, "delegowanie", true, false);

	private final List<String> odpowiadajaceId;

	private final String nazwa;

	private final Boolean wiecejPozycji;

	private final Boolean mniejPozycji;

	ContractTypesEnum(String[] odpowiadajaceId, String nazwa, Boolean wiecejPozycji, Boolean mniejPozycji) {
		this.odpowiadajaceId = Arrays.asList(odpowiadajaceId);
		this.nazwa = nazwa;
		this.wiecejPozycji = wiecejPozycji;
		this.mniejPozycji = mniejPozycji;
	}


}
