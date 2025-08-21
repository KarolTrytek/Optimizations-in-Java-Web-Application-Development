package pl.edu.pk.optimizationsapp.data.domain.ofz;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypOfertyEnum {

	OFERTA_PRACY ("oferta pracy",   "cbop.i18n.typOfertyEnum.ofertaPracy"),
	STAZ ("staż",   "cbop.i18n.typOfertyEnum.workExperience"),
	PRAKTYKI ("praktyka",  "cbop.i18n.typOfertyEnum.praktyki"),
	PRAKT_STUDENT ("praktyka studencka",  "cbop.i18n.typOfertyEnum.praktykaStudent"),
	SPOL_UZYTECZ ("prace społecznie użyteczne","cbop.i18n.typOfertyEnum.spoolUzyteczne"),
	WOLONTARIAT ("wolontariat",   "cbop.i18n.typOfertyEnum.wolontariat"),
	PRZYG_ZAW ("Przygotowanie Zawodowe Dorosłych", "cbop.i18n.typOfertyEnum.przygotowanieZawodoweDoroslych"),
	STAZ_UP ("staż z urzędu pracy",   "cbop.i18n.typOfertyEnum.stazZUrzeduPracy"),;

	private final String nazwa;

	private final String tlumaczenie;


}
