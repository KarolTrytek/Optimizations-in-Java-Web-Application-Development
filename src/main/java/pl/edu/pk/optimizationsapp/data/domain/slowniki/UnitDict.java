package pl.edu.pk.optimizationsapp.data.domain.slowniki;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "sl_placowka", schema = "slowniki")
@DynamicInsert
@DynamicUpdate
public class UnitDict implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "placowka_id_gen")
	@SequenceGenerator(name = "placowka_id_gen", sequenceName = "seq_sl_placowka", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "typ_podmiotu")
	@Enumerated(EnumType.STRING)
	private TypPlacowkiEnum typPlacowki;

	@Size(max = 50)
	@NotNull
	@Column(name = "kod_placowki", nullable = false, length = 50)
	private String kodPlacowki;

	@Column(name = "data_do")
	private LocalDate dataDo;

	@Column(name = "data_od")
	private LocalDate dataOd;

	@Size(max = 70)
	@Column(name = "email")
	private String email;

	@Size(max = 20)
	@Column(name = "fax")
	private String fax;

	@Size(max = 8)
	@Column(name = "id_plac_mpips")
	private String idPlacMpips;

	@Size(max = 30)
	@Column(name = "imie_kierownika")
	private String imieKierownika;

	@Size(max = 50)
	@Column(name = "nazwisko_kierownika")
	private String nazwiskoKierownika;

	@Size(max = 1)
	@Column(name = "kategoria_filii")
	private String kategoriaFilii;

	@Size(max = 6)
	@Column(name = "kod_poczty")
	private String kodPoczty;

	@Size(max = 15)
	@Column(name = "kod_sprawozd")
	private String kodSprawozd;

	@Column(name = "kod_teryt_miejsc")
	private String miejscowosc;

	@Column(name = "kod_teryt_gminy")
	private String gmina;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kod_teryt_wojew")
	private VoivodeshipDict wojewodztwo;

	@Size(max = 50)
	@Column(name = "nazwa_miejsc")
	private String nazwaMiejsc;

	@Size(max = 20)
	@Column(name = "nip")
	private String nip;

	@Size(max = 10)
	@Column(name = "nr_domu")
	private String nrDomu;

	@Size(max = 6)
	@Column(name = "nr_lokalu")
	private String nrLokalu;

	@Size(max = 9)
	@Column(name = "nr_skr_poczt")
	private String nrSkrPoczt;

	@Size(max = 500)
	@Column(name = "opis_placowki")
	private String opisPlacowki;

	@Size(max = 70)
	@Column(name = "opis_skr")
	private String opisSkr;

	@Size(max = 14)
	@Column(name = "regon")
	private String regon;

	@Size(max = 80)
	@Column(name = "stanowisko")
	private String stanowisko;

	@Size(max = 25)
	@Column(name = "telefon1")
	private String telefon1;

	@Size(max = 25)
	@Column(name = "telefon2")
	private String telefon2;

	@Size(max = 40)
	@Column(name = "ulica")
	private String ulica;

	@Column(name = "czy_pup")
	private Boolean pup = false;

	@Column(name = "czy_wup")
	private Boolean wup = false;

}
