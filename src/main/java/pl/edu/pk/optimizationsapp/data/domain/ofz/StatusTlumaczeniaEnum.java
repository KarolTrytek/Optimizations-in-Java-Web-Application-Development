package pl.edu.pk.optimizationsapp.data.domain.ofz;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusTlumaczeniaEnum {
	D("do weryfikacji", "cbop.i18n.statusTlumaczeniaEnum.doWeryfikacji"),
	Z("zweryfikowana", "cbop.i18n.statusTlumaczeniaEnum.zweryfikowana"),
	P("do publikacji", "cbop.i18n.statusTlumaczeniaEnum.doPublikacji");

	private final String opis;

	private final String kodTlumaczenia;

}
