package pl.edu.pk.optimizationsapp.data.domain.ofz;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SposobAplikowaniaEnum {
	P("Bezpośrednio do Pracodawcy", "[cbop.i18n.sposobAplikowaniaEnum.bezposrednioDoPracodawcy]",
			"[cbop.i18n.sposobAplikowaniaEnum.bezposrednioDoPracodawcy]"),
	R("Bezpośrednio do organizacji", "[cbop.i18n.sposobAplikowaniaEnum.bezposrednioDoOrganizacji]",
			"[cbop.i18n.sposobAplikowaniaEnum.bezposrednioDoOrganizacji]"),
	A("Do Agencji", "[cbop.i18n.sposobAplikowaniaEnum.doAgencji]", "[cbop.i18n.sposobAplikowaniaEnum.doAgencji]"),
	E("Do doradcy Eures z EOG", "[cbop.i18n.sposobAplikowaniaEnum.doDoradcyEURESzEOG]",
			"[cbop.i18n.sposobAplikowaniaEnum.doDoradcyEURESzEOG]"),
	W("Do doradcy Eures z WUP", "[cbop.i18n.sposobAplikowaniaEnum.doDoradcyEURESzWUP]",
			"[cbop.i18n.sposobAplikowaniaEnum.doDoradcyEURESzWUP]"),
	O("Kontakt przed OHP", "[cbop.i18n.sposobAplikowaniaEnum.kontaktPrzezOHP]", "[cbop.i18n.sposobAplikowaniaEnum.kontaktPrzezOHP]"),
	U("Kontakt przez PUP", "[cbop.i18n.sposobAplikowaniaEnum.kontaktPrzezPUP]",
			"[cbop.i18n.sposobAplikowaniaEnum.zaPosrednictwemUrzedu]"),
	N("Poprzez uczelnie", "[cbop.i18n.sposobAplikowaniaEnum.poprzezUczelnie]", "[cbop.i18n.sposobAplikowaniaEnum.poprzezUczelnie]");

	private final String nazwa;

	private final String value;

	private final String opisPortal;

}
