package pl.edu.pk.optimizationsapp.data.domain.ofz;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.SlElemSlowCentr;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@DynamicUpdate
@Table(name = "pracodawcy", schema = "ofz")
public class Pracodawcy implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pracodawcy_id_gen")
	@SequenceGenerator(name = "pracodawcy_id_gen", sequenceName = "seq_pracodawcy", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Long id;

	@Size(max = 200)
	@NotNull
	@Column(name = "nazwa", nullable = false, length = 200)
	private String nazwa;

	@Size(max = 13)
	@Column(name = "nip", length = 13)
	private String nip;

	@Size(max = 11)
	@Column(name = "pesel", length = 11)
	private String pesel;

	@Size(max = 15)
	@Column(name = "regon", length = 15)
	private String regon;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kod_pkd")
	private SlElemSlowCentr kodPkd;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kod_pkd2007")
	private SlElemSlowCentr kodPkd2007;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_adresu", nullable = false)
	private Adresy idAdresu;

	@NotNull
	@Column(name = "agencja_zatrudnienia", nullable = false)
	@Builder.Default
	private Boolean agencjaZatrudnienia = false;

	@NotNull
	@Column(name = "pracodawca_zagraniczny", nullable = false)
	@Builder.Default
	private Boolean pracodawcaZagraniczny = false;

	@Size(max = 100)
	@Column(name = "www", length = 100)
	private String www;

	@Size(max = 15)
	@Column(name = "nr_kraz", length = 15)
	private String nrKraz;

	@Column(name = "profil_dzialalnosci", length = Integer.MAX_VALUE)
	private String profilDzialalnosci;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "forma_prawna")
	private SlElemSlowCentr formaPrawna;

	@Column(name = "ukarany_skazany")
	private Boolean ukaranySkazany;

	@Size(max = 10)
	@Column(name = "wielkosc_przed", length = 10)
	private String wielkoscPrzed;

	@NotNull
	@Column(name = "pracodawca", nullable = false)
	@Builder.Default
	private Boolean pracodawca = false;

	@NotNull
	@Column(name = "przedsiebiorca", nullable = false)
	@Builder.Default
	private Boolean przedsiebiorca = false;

	@NotNull
	@Column(name = "osoba_fizyczna", nullable = false)
	@Builder.Default
	private Boolean osobaFizyczna = false;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kod_obywatelstwa")
	private SlElemSlowCentr kodObywatelstwa;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kod_rodz_dokum_tozsam")
	private SlElemSlowCentr kodRodzDokumTozsam;

	@Size(max = 20)
	@Column(name = "nr_dokum_tozsam", length = 20)
	private String nrDokumTozsam;

	@Size(max = 50)
	@Column(name = "opis_rodz_dokum_tozsam", length = 50)
	private String opisRodzDokumTozsam;

	@Size(max = 50)
	@Column(name = "organ_wyd_dokum_tozsam", length = 50)
	private String organWydDokumTozsam;

	@Column(name = "data_wyd_dokum_tozsam")
	private LocalDate dataWydDokumTozsam;

	@Column(name = "data_wazn_dokum_tozsam")
	private LocalDate dataWaznDokumTozsam;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Pracodawcy that = (Pracodawcy) o;
		return Objects.equals(id, that.id) && Objects.equals(nazwa, that.nazwa) && Objects.equals(nip, that.nip) && Objects.equals(pesel, that.pesel)
				&& Objects.equals(regon, that.regon) && Objects.equals(kodPkd, that.kodPkd) && Objects.equals(kodPkd2007, that.kodPkd2007) && Objects.equals(
				idAdresu, that.idAdresu) && Objects.equals(agencjaZatrudnienia, that.agencjaZatrudnienia) && Objects.equals(pracodawcaZagraniczny,
				that.pracodawcaZagraniczny) && Objects.equals(www, that.www) && Objects.equals(nrKraz, that.nrKraz) && Objects.equals(profilDzialalnosci,
				that.profilDzialalnosci) && Objects.equals(formaPrawna, that.formaPrawna) && Objects.equals(ukaranySkazany, that.ukaranySkazany)
				&& Objects.equals(wielkoscPrzed, that.wielkoscPrzed) && Objects.equals(pracodawca, that.pracodawca) && Objects.equals(przedsiebiorca, that.przedsiebiorca)
				&& Objects.equals(osobaFizyczna, that.osobaFizyczna) && Objects.equals(kodObywatelstwa, that.kodObywatelstwa) && Objects.equals(
				kodRodzDokumTozsam, that.kodRodzDokumTozsam) && Objects.equals(nrDokumTozsam, that.nrDokumTozsam) && Objects.equals(opisRodzDokumTozsam,
				that.opisRodzDokumTozsam) && Objects.equals(organWydDokumTozsam, that.organWydDokumTozsam) && Objects.equals(dataWydDokumTozsam,
				that.dataWydDokumTozsam) && Objects.equals(dataWaznDokumTozsam, that.dataWaznDokumTozsam);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nazwa, nip, pesel, regon, kodPkd, kodPkd2007, idAdresu, agencjaZatrudnienia, pracodawcaZagraniczny, www, nrKraz,
				profilDzialalnosci, formaPrawna, ukaranySkazany, wielkoscPrzed, pracodawca, przedsiebiorca, osobaFizyczna, kodObywatelstwa, kodRodzDokumTozsam,
				nrDokumTozsam, opisRodzDokumTozsam, organWydDokumTozsam, dataWydDokumTozsam, dataWaznDokumTozsam);
	}
}