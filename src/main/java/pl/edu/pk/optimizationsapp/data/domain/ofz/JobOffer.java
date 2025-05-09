package pl.edu.pk.optimizationsapp.data.domain.ofz;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.SQLInsert;
import org.hibernate.annotations.SQLUpdate;
import pl.edu.pk.optimizationsapp.data.converter.StatusOfertyEnumConverter;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.SlElemSlowCentr;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.SlElemSlowSys;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.SlGmina;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.SlPlacowka;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@SQLUpdate(sql = """
		UPDATE ofz.oferty_pracy
		       	SET
		       	    adres_aplikowania_opis=?,
		            id_adresu_miejsca_pracy=?,
		            brak_mozl_wykorzyst=?,
		            cele_edukacyjne=?,
		            data_arch_dziedzinowa=?,
		            data_konca_zatrud=?,
		            data_pocz_zatrud=?,
		            data_przyj_zglosz=?,
		            data_wazn_do=?,
		            data_wazn_od=?,
		            data_weryfikacji=?,
		            data_wyslania_na_eures=?,
		            dod_info_aplikowanie=?,
		            dod_info_czas_pracy=?,
		            dod_opis_eures=?,
		            dod_swiadczenia=?,
		            dzialania_zatr=?,
		            edit_mode=?,
		            email_osoby_zgl=?,
		            email_osoby_zgl_pracod=?,
		            eures_krajowa=?,
		            eures_krajowa_wskaz_kraje=?,
		            eures_zagraniczna=?,
		            hash=?,
		            hash_jap=?,
		            id_adresu_aplikowania=?,
		            id_adresu_aplikowania_pracod=?,
		            id_oferty_dziedzinowy=?,
		            id_oferty_pracy_pl=?,
		            id_placowki_zasilajacej=?,
		            id_pracodawcy=?,
		            id_rejestrujacego=?,
		            id_uzytkownika_weryfikujacego=?,
		            id_waluta=?,
		            il_godz_mies=?,
		            il_godz_tydz=?,
		            imie_nazwisko_osoby_zgl=?,
		            imie_nazwisko_osoby_zgl_pracod=?,
		            inne_wymag=?,
		            kod_jezyka=?,
		            kod_kateg_oferty=?,
		            kod_kraju_pochodzenia=?,
		            kod_rodz_zatrud=?,
		            kod_syst_wynagr=?,
		            kod_teryt_gminy=?,
		            kod_zawodu=?,
		            kod_zawodu_2010=?,
		            kod_zawodu_isco=?,
		            kod_zmianowosci=?,
		            koszt_przej_do_polski=?,
		            liczba_cv=?,
		            liczba_cv_skierowania=?,
		            liczba_miejsc_pracy_niepel=?,
		            liczba_miejsc_pracy_ogolem=?,
		            liczba_mies_staz_pzd=?,
		            liczba_zatrudn_skierowania=?,
		            liczba_zatrudnionych=?,
		            lokalizacja=?,
		            lokalizacja_inna=?,
		            mentor_opiekun=?,
		            niepelnosprawni=?,
		            nr_oferty=?,
		            odplatny=?,
		            opis_wynagr=?,
		            pefron=?,
		            pensja_netto=?,
		            potwierdzenie_wiedzy=?,
		            powod_oddelegowania=?,
		            praca_stala=?,
		            praca_tymczasowa=?,
		            praca_wolne_dni=?,
		            premie=?,
		            przyczyna_arch_dziedzinowa=?,
		            przyczyna_odrzucenia=?,
		            publik_dane_pracod=?,
		            rejestracja_bezposrednia=?,
		            spos_przek_dokum=?,
		            sposob_aplikowania=?,
		            stan_zatrudnienia=?,
		            stanow_ofer=?,
		            status=?,
		            status_dziedzinowy=?,
		            status_tlumaczenia=?,
		            staz_wymag_ogol=?,
		            subsydiowana=?,
		            telefon_osoby_zgl=?,
		            telefon_osoby_zgl_pracod=?,
		            tis_il_kand=?,
		            tis_il_kand_nieprzyj=?,
		            tis_il_kand_przyj=?,
		            tis_il_kand_skier=?,
		            tis_kand_nieprzyj_powody=?,
		            tis_liczba_wyd_egzemplarzy=?,
		            tis_nr_dokumentu=?,
		            tis_ocena=?,
		            tis_ocena_pow_nieprz=?,
		            tis_ocena_pow_nieprz_uzas=?,
		            tis_ocena_skroc=?,
		            tis_ocena_skroc_uzas=?,
		            tis_ocena_uzas=?,
		            tis_ocena_wymagan=?,
		            tis_ocena_wymagan_uzas=?,
		            tis_ocena_wynagr=?,
		            tis_ocena_wynagr_uzas=?,
		            tis_okres_waznosci_do=?,
		            tis_okres_waznosci_od=?,
		            tis_rodz_zez_czas=?,
		            tis_rodz_zez_sez=?,
		            tis_rodz_zez_typ_a=?,
		            tis_rodz_zez_wys_kwal=?,
		            tis_skier_kand_zgoda=?,
		            tis_typ_inf_starosty=?,
		            typ_oferty=?,
		            typ_pzd=?,
		            ubezpieczenie_wypadkowe=?,
		            ubezpieczenie_zdrowotne=?,
		            wskazano_kandydata=?,
		            wymag_dokum=?,
		            wymiar_zatrud=?,
		            wynagr_do=?,
		            wynagr_od=?,
		            wyzywienie=?,
		            zainter_zatrud_ua=?,
		            zakres_obowiazkow=?,
		            zakwaterowanie=?,
		            zatr_od_zaraz=?,
		            zgod_na_tlumaczenie_by=?,
		            zgod_na_tlumaczenie_en=?,
		            zgod_na_tlumaczenie_ru=?,
		            zgod_na_tlumaczenie_ua=?
		       	WHERE id=?""")
@SQLInsert(sql = """
		insert into ofz.oferty_pracy (adres_aplikowania_opis,id_adresu_miejsca_pracy,brak_mozl_wykorzyst,cele_edukacyjne,data_arch_dziedzinowa,
		data_konca_zatrud,data_pocz_zatrud,data_przyj_zglosz,data_wazn_do,data_wazn_od,data_weryfikacji,data_wyslania_na_eures,dod_info_aplikowanie,
		dod_info_czas_pracy,dod_opis_eures,dod_swiadczenia,dzialania_zatr,edit_mode,email_osoby_zgl,email_osoby_zgl_pracod,eures_krajowa,
		eures_krajowa_wskaz_kraje,eures_zagraniczna,hash,hash_jap,id_adresu_aplikowania,id_adresu_aplikowania_pracod,id_oferty_dziedzinowy,
		id_oferty_pracy_pl,id_placowki_zasilajacej,id_pracodawcy,id_rejestrujacego,id_uzytkownika_weryfikujacego,id_waluta,il_godz_mies,il_godz_tydz,
		imie_nazwisko_osoby_zgl,imie_nazwisko_osoby_zgl_pracod,inne_wymag,kod_jezyka,kod_kateg_oferty,kod_kraju_pochodzenia,kod_rodz_zatrud,
		kod_syst_wynagr,kod_teryt_gminy,kod_zawodu,kod_zawodu_2010,kod_zawodu_isco,kod_zmianowosci,koszt_przej_do_polski,liczba_cv,liczba_cv_skierowania,
		liczba_miejsc_pracy_niepel,liczba_miejsc_pracy_ogolem,liczba_mies_staz_pzd,liczba_zatrudn_skierowania,liczba_zatrudnionych,lokalizacja,
		lokalizacja_inna,mentor_opiekun,niepelnosprawni,nr_oferty,odplatny,opis_wynagr,pefron,pensja_netto,potwierdzenie_wiedzy,powod_oddelegowania,
		praca_stala,praca_tymczasowa,praca_wolne_dni,premie,przyczyna_arch_dziedzinowa,przyczyna_odrzucenia,publik_dane_pracod,rejestracja_bezposrednia,
		spos_przek_dokum,sposob_aplikowania,stan_zatrudnienia,stanow_ofer,status,status_dziedzinowy,status_tlumaczenia,staz_wymag_ogol,subsydiowana,
		telefon_osoby_zgl,telefon_osoby_zgl_pracod,tis_il_kand,tis_il_kand_nieprzyj,tis_il_kand_przyj,tis_il_kand_skier,tis_kand_nieprzyj_powody,
		tis_liczba_wyd_egzemplarzy,tis_nr_dokumentu,tis_ocena,tis_ocena_pow_nieprz,tis_ocena_pow_nieprz_uzas,tis_ocena_skroc,tis_ocena_skroc_uzas,
		tis_ocena_uzas,tis_ocena_wymagan,tis_ocena_wymagan_uzas,tis_ocena_wynagr,tis_ocena_wynagr_uzas,tis_okres_waznosci_do,tis_okres_waznosci_od,
		tis_rodz_zez_czas,tis_rodz_zez_sez,tis_rodz_zez_typ_a,tis_rodz_zez_wys_kwal,tis_skier_kand_zgoda,tis_typ_inf_starosty,typ_oferty,typ_pzd,
		ubezpieczenie_wypadkowe,ubezpieczenie_zdrowotne,wskazano_kandydata,wymag_dokum,wymiar_zatrud,wynagr_do,wynagr_od,wyzywienie,zainter_zatrud_ua,
		zakres_obowiazkow,zakwaterowanie,zatr_od_zaraz,zgod_na_tlumaczenie_by,zgod_na_tlumaczenie_en,zgod_na_tlumaczenie_ru,zgod_na_tlumaczenie_ua,id)
		values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,
		?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
	\t""")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "oferty_pracy", schema = "ofz")
public class JobOffer implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oferty_pracy_id_gen")
	@SequenceGenerator(name = "oferty_pracy_id_gen", sequenceName = "seq_oferty_pracy", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_placowki_zasilajacej", nullable = false)
	private SlPlacowka idPlacowkiZasilajacej;

	@Size(max = 20)
	@NotNull
	@Column(name = "id_oferty_dziedzinowy", nullable = false, length = 20)
	private String idOfertyDziedzinowy;

	@Size(max = 50)
	@NotNull
	@Column(name = "nr_oferty", nullable = false, length = 50)
	private String nrOferty;

	@NotNull
	@Convert(converter = StatusOfertyEnumConverter.class)
//	@Enumerated(EnumType.ORDINAL)
	@Column(name = "status", nullable = false)
	private StatusOfertyEnum status;

	@NotNull
	@Column(name = "data_przyj_zglosz", nullable = false)
	private LocalDate dataPrzyjZglosz;

	@Column(name = "data_wazn_od")
	private LocalDate dataWaznOd;

	@Column(name = "data_wazn_do")
	private LocalDate dataWaznDo;

	@Size(max = 256)
	@NotNull
	@Column(name = "stanow_ofer", nullable = false, length = 256)
	private String jobTitle;

	@Column(name = "zakres_obowiazkow", length = Integer.MAX_VALUE)
	private String zakresObowiazkow;

	@NotNull
	@Column(name = "liczba_miejsc_pracy_ogolem", nullable = false)
	private Integer liczbaMiejscPracyOgolem = 0;

	@NotNull
	@Column(name = "liczba_miejsc_pracy_niepel", nullable = false)
	private Integer liczbaMiejscPracyNiepel = 0;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "kod_zawodu", nullable = false)
	private SlElemSlowCentr kodZawodu;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kod_zawodu_isco")
	private SlElemSlowCentr kodZawoduIsco;

	@Column(name = "staz_wymag_ogol", precision = 9, scale = 2)
	private BigDecimal stazWymagOgol;

	@Column(name = "inne_wymag", length = Integer.MAX_VALUE)
	private String inneWymag;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_adresu_miejsca_pracy", nullable = false)
	private Adresy adresMiejscaPracy;

	@Column(name = "data_pocz_zatrud")
	private LocalDate dataPoczZatrud;

	@Column(name = "data_konca_zatrud")
	private LocalDate dataKoncaZatrud;

	@Column(name = "wynagr_od", precision = 10, scale = 2)
	private BigDecimal salaryFrom = BigDecimal.ZERO;

	@Column(name = "wynagr_do", precision = 10, scale = 2)
	private BigDecimal salaryTo = BigDecimal.ZERO;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_waluta")
	private SlElemSlowCentr idWaluta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kod_syst_wynagr")
	private SlElemSlowCentr kodSystWynagr;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kod_rodz_zatrud")
	private SlElemSlowSys kodRodzZatrud;

	@Column(name = "wymiar_zatrud", precision = 9, scale = 3)
	private BigDecimal wymiarZatrud;

	@Column(name = "il_godz_tydz", precision = 9, scale = 2)
	private BigDecimal ilGodzTydz;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kod_zmianowosci")
	private SlElemSlowCentr kodZmianowosci;

	@Column(name = "praca_wolne_dni")
	private Boolean pracaWolneDni = false;

	@Column(name = "zatr_od_zaraz")
	private Boolean zatrOdZaraz;

	@NotNull
	@Column(name = "zakwaterowanie", nullable = false)
	@Builder.Default
	private Boolean zakwaterowanie = false;

	@NotNull
	@Column(name = "wyzywienie", nullable = false)
	@Builder.Default
	private Boolean wyzywienie = false;

	@NotNull
	@Column(name = "koszt_przej_do_polski", nullable = false)
	@Builder.Default
	private Boolean kosztPrzejDoPolski = false;

	@NotNull
	@Column(name = "publik_dane_pracod", nullable = false)
	private Boolean publikDanePracod;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_pracodawcy", nullable = false)
	private Pracodawcy idPracodawcy;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SposobAplikowaniaEnum sposobAplikowania;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_adresu_aplikowania")
	private Adresy idAdresuAplikowania;

	@Column(name = "adres_aplikowania_opis", length = Integer.MAX_VALUE)
	private String adresAplikowaniaOpis;

	@Column(name = "wymag_dokum", length = Integer.MAX_VALUE)
	private String wymagDokum;

	@Column(name = "spos_przek_dokum", length = Integer.MAX_VALUE)
	private String sposPrzekDokum;

	@Size(max = 150)
	@Column(name = "imie_nazwisko_osoby_zgl", length = 150)
	private String imieNazwiskoOsobyZgl;

	@Size(max = 100)
	@Column(name = "telefon_osoby_zgl", length = 100)
	private String telefonOsobyZgl;

	@Size(max = 100)
	@Column(name = "email_osoby_zgl", length = 100)
	private String emailOsobyZgl;

	@NotNull
	@Column(name = "brak_mozl_wykorzyst", nullable = false)
	@Builder.Default
	private Boolean brakMozlWykorzyst = false;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "typ_oferty", nullable = false)
	private TypOfertyEnum jobType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kod_kateg_oferty")
	private SlElemSlowCentr kodKategOferty;

	@NotNull
	@Column(name = "subsydiowana", nullable = false)
	@Builder.Default
	private Boolean subsydiowana = false;

	@NotNull
	@Column(name = "pefron", nullable = false)
	@Builder.Default
	private Boolean pefron = false;

	@NotNull
	@Column(name = "eures_krajowa", nullable = false)
	@Builder.Default
	private Boolean euresKrajowa = false;

	@NotNull
	@Column(name = "eures_zagraniczna", nullable = false)
	@Builder.Default
	private Boolean euresZagraniczna = false;

	@NotNull
	@Column(name = "praca_tymczasowa", nullable = false)
	@Builder.Default
	private Boolean pracaTymczasowa = false;

	@Column(name = "praca_stala")
	private Boolean pracaStala;

	@NotNull
	@Column(name = "rejestracja_bezposrednia", nullable = false)
	@Builder.Default
	private Boolean rejestracjaBezposrednia = false;

	@Column(name = "data_wyslania_na_eures")
	private LocalDate dataWyslaniaNaEures;

	@Column(name = "dod_opis_eures", length = Integer.MAX_VALUE)
	private String dodOpisEures;

	@Column(name = "liczba_cv")
	private Integer liczbaCv;

	@Column(name = "liczba_zatrudnionych")
	private Integer liczbaZatrudnionych;

	@Column(name = "stan_zatrudnienia")
	private Integer stanZatrudnienia;

	@Column(name = "id_rejestrujacego")
	private Integer idRejestrujacego;

	@Column(name = "data_weryfikacji")
	private LocalDateTime dataWeryfikacji;

	@Column(name = "id_uzytkownika_weryfikujacego")
	private Integer idUzytkownikaWeryfikujacego;

	@Column(name = "przyczyna_odrzucenia", length = Integer.MAX_VALUE)
	private String przyczynaOdrzucenia;

	@Enumerated(EnumType.STRING)
	@Column(name = "status_dziedzinowy", length = 1)
	private StatusDziedzinowyEnum statusDziedzinowy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "przyczyna_arch_dziedzinowa")
	private SlElemSlowSys przyczynaArchDziedzinowa;

	@Column(name = "data_arch_dziedzinowa")
	private LocalDate dataArchDziedzinowa;

	@NotNull
	@Column(name = "niepelnosprawni", nullable = false)
	@Builder.Default
	private Boolean niepelnosprawni = false;

	@NotNull
	@Column(name = "wskazano_kandydata", nullable = false)
	@Builder.Default
	private Boolean wskazanoKandydata = false;

	@NotNull
	@Column(name = "eures_krajowa_wskaz_kraje", nullable = false)
	@Builder.Default
	private Boolean euresKrajowaWskazKraje = false;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kod_kraju_pochodzenia")
	private SlElemSlowCentr kodKrajuPochodzenia;

	@Column(name = "lokalizacja", length = 1)
	@Enumerated(EnumType.STRING)
	private TypLokalizacjaEnum lokalizacja;

	@Column(name = "lokalizacja_inna", length = Integer.MAX_VALUE)
	private String lokalizacjaInna;

	@Column(name = "powod_oddelegowania", length = Integer.MAX_VALUE)
	private String powodOddelegowania;

	@Column(name = "pensja_netto", precision = 10, scale = 2)
	private BigDecimal pensjaNetto;

	@Column(name = "premie")
	private Boolean premie;

	@Column(name = "dod_swiadczenia", length = Integer.MAX_VALUE)
	private String dodSwiadczenia;

	@Column(name = "dod_info_czas_pracy", length = Integer.MAX_VALUE)
	private String dodInfoCzasPracy;

	@Column(name = "dod_info_aplikowanie", length = Integer.MAX_VALUE)
	private String dodInfoAplikowanie;

	@Column(name = "dzialania_zatr", length = Integer.MAX_VALUE)
	private String dzialaniaZatr;

	@Column(name = "liczba_zatrudn_skierowania")
	private Integer liczbaZatrudnSkierowania;

	@Column(name = "liczba_cv_skierowania")
	private Integer liczbaCvSkierowania;

	@Size(max = 32)
	@NotNull
	@Column(name = "hash", nullable = false, length = 32)
	private String hash;

	@Column(name = "liczba_mies_staz_pzd")
	private Integer liczbaMiesStazPzd;

	@NotNull
	@Column(name = "edit_mode", nullable = false)
	@Builder.Default
	private Boolean editMode = false;

	@Size(max = 1500)
	@Column(name = "opis_wynagr", length = 1500)
	private String opisWynagr;

	@Column(name = "cele_edukacyjne", length = Integer.MAX_VALUE)
	private String celeEdukacyjne;

	@Column(name = "potwierdzenie_wiedzy", length = Integer.MAX_VALUE)
	private String potwierdzenieWiedzy;

	@Size(max = 150)
	@Column(name = "mentor_opiekun", length = 150)
	private String mentorOpiekun;

	@Column(name = "ubezpieczenie_zdrowotne")
	private Boolean ubezpieczenieZdrowotne;

	@Column(name = "ubezpieczenie_wypadkowe")
	private Boolean ubezpieczenieWypadkowe;

	@Column(name = "odplatny")
	private Boolean odplatny;

	@Size(max = 50)
	@Column(name = "kod_zawodu_2010", length = 50)
	private String kodZawodu2010;

	@Size(max = 32)
	@Column(name = "hash_jap", length = 32)
	private String hashJap;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kod_teryt_gminy")
	private SlGmina kodTerytGminy;

	@Size(max = 150)
	@Column(name = "imie_nazwisko_osoby_zgl_pracod", length = 150)
	private String imieNazwiskoOsobyZglPracod;

	@Size(max = 100)
	@Column(name = "telefon_osoby_zgl_pracod", length = 100)
	private String telefonOsobyZglPracod;

	@Size(max = 100)
	@Column(name = "email_osoby_zgl_pracod", length = 100)
	private String emailOsobyZglPracod;

	@OneToOne
	@JoinColumn(name = "id_adresu_aplikowania_pracod")
	private Adresy idAdresuAplikowaniaPracod;

	@Column(name = "tis_typ_inf_starosty")
	private Boolean tisTypInfStarosty;

	@Column(name = "tis_rodz_zez_typ_a")
	private Boolean tisRodzZezTypA;

	@Column(name = "tis_rodz_zez_czas")
	private Boolean tisRodzZezCzas;

	@Column(name = "tis_rodz_zez_wys_kwal")
	private Boolean tisRodzZezWysKwal;

	@Column(name = "tis_rodz_zez_sez")
	private Boolean tisRodzZezSez;

	@Column(name = "tis_skier_kand_zgoda")
	private Boolean tisSkierKandZgoda;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tis_ocena_wynagr")
	private SlElemSlowSys tisOcenaWynagr;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tis_ocena_wymagan")
	private SlElemSlowSys tisOcenaWymagan;

	@Column(name = "tis_ocena_wymagan_uzas", length = Integer.MAX_VALUE)
	private String tisOcenaWymaganUzas;

	@Column(name = "tis_il_kand")
	private Integer tisIlKand;

	@Column(name = "tis_il_kand_skier")
	private Integer tisIlKandSkier;

	@Column(name = "tis_il_kand_przyj")
	private Integer tisIlKandPrzyj;

	@Column(name = "tis_il_kand_nieprzyj")
	private Integer tisIlKandNieprzyj;

	@Column(name = "tis_kand_nieprzyj_powody", length = Integer.MAX_VALUE)
	private String tisKandNieprzyjPowody;

	@Column(name = "tis_ocena")
	private Boolean tisOcena;

	@Column(name = "tis_ocena_uzas", length = Integer.MAX_VALUE)
	private String tisOcenaUzas;

	@Column(name = "tis_ocena_skroc")
	private Boolean tisOcenaSkroc;

	@Column(name = "tis_ocena_skroc_uzas", length = Integer.MAX_VALUE)
	private String tisOcenaSkrocUzas;

	@Size(max = 50)
	@Column(name = "tis_nr_dokumentu", length = 50)
	private String tisNrDokumentu;

	@Column(name = "tis_ocena_wynagr_uzas", length = Integer.MAX_VALUE)
	private String tisOcenaWynagrUzas;

	@Column(name = "il_godz_mies", precision = 9, scale = 2)
	private BigDecimal ilGodzMies;

	@Column(name = "tis_okres_waznosci_od")
	private LocalDate tisOkresWaznosciOd;

	@Column(name = "tis_okres_waznosci_do")
	private LocalDate tisOkresWaznosciDo;

	@Column(name = "tis_liczba_wyd_egzemplarzy")
	private Integer tisLiczbaWydEgzemplarzy;

	@Size(max = 2)
	@Column(name = "kod_jezyka", length = 2)
	private String kodJezyka;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_oferty_pracy_pl")
	private JobOffer idJobOfferPl;

	@Column(name = "status_tlumaczenia")
	@Enumerated(EnumType.STRING)
	private StatusTlumaczeniaEnum statusTlumaczenia = StatusTlumaczeniaEnum.D;

	@Column(name = "zainter_zatrud_ua")
	private Boolean zainterZatrudUa;

	@Column(name = "zgod_na_tlumaczenie_en")
	private Boolean zgodNaTlumaczenieEn;

	@Column(name = "zgod_na_tlumaczenie_by")
	private Boolean zgodNaTlumaczenieBy;

	@Column(name = "zgod_na_tlumaczenie_ua")
	private Boolean zgodNaTlumaczenieUa;

	@Column(name = "zgod_na_tlumaczenie_ru")
	private Boolean zgodNaTlumaczenieRu;

	@Enumerated(EnumType.STRING)
	@Column(name = "tis_ocena_pow_nieprz", length = 1)
	private OcenaPowodowNieprzyjeciaKandydatowEnum tisOcenaPowNieprz;

	@Column(name = "tis_ocena_pow_nieprz_uzas", length = Integer.MAX_VALUE)
	private String tisOcenaPowNieprzUzas;

	@Formula("id_placowki_zasilajacej || '@' || nr_oferty")
	private String numerWEures;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "hash", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none"))
	private LicznikOfert licznikOfert;

	@OneToOne(mappedBy = "jobOffer", cascade = CascadeType.ALL)
	//@PrimaryKeyJoinColumn
	private OfertaMetadane ofertaMetadane;

	@OneToMany
	@JoinColumn(name = "id_oferty_centralny")
	@OrderBy("id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@BatchSize(size = 50)
	private Set<JezykiAplikowania> jezykiAplikowania;

	@OneToMany
	@JoinColumn(name = "id_oferty_centralny")
	@OrderBy("id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@BatchSize(size = 50)
	private Set<WymaganiaZawod> wymaganyZawod;

	@OneToMany
	@JoinColumn(name = "id_oferty_centralny")
	@OrderBy("id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@BatchSize(size = 50)
	private Set<WymaganiaWyksztalcenie> wymaganeWyksztalcenie;

	@OneToMany
	@JoinColumn(name = "id_oferty_centralny")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@BatchSize(size = 50)
	private Set<WymaganiaJezyk> wymaganeJezyki;

	@OneToMany
	@JoinColumn(name = "id_oferty_centralny")
	@OrderBy("id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@BatchSize(size = 50)
	private Set<WymaganiaUprawnienia> wymaganeUprawnienia;

	@OneToMany
	@JoinColumn(name = "id_oferty_centralny")
	@OrderBy("id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@BatchSize(size = 50)
	private Set<WymaganiaUmiejetnosci> wymaganeUmiejetnosci;

	@OneToMany
	@JoinColumn(name = "id_oferty_centralny")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@BatchSize(size = 20)
	public Set<TrybyPracy> trybyPracy;

	@OneToMany
	@JoinColumn(name = "id_oferty_centralny")
	@OrderBy("id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@BatchSize(size = 50)
	private Set<WymaganiaPredyspozycje> wymaganePredyspozycje;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
			return false;
		JobOffer that = (JobOffer) o;
		return id != null && Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	@Transient
	public BigDecimal getWynagrodzenie() {
		if (salaryFrom != null) {
			return salaryFrom;
		} else {
			return salaryTo != null ? salaryTo : BigDecimal.ZERO;
		}
	}
}