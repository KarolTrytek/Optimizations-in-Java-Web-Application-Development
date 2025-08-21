package pl.edu.pk.optimizationsapp.data.domain.ofz;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLInsert;
import org.hibernate.annotations.SQLUpdate;
import pl.edu.pk.optimizationsapp.data.converter.JobOfferStatusEnumConverter;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.ElementCentralDict;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.SlKodPocztowy;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.CityDict;

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
@SQLUpdate(sql = """
        UPDATE ofz.adresy
        	SET
        	    data_przyj_zglosz=?,
        	    email=?,
        	    fax=?,
        	    kod_euro_regionu=?,
        	    kod_kraju=?,
        	    kod_pocztowy=?,
        	    kod_poczty_zagr=?,
        	    kod_teryt_miejsc=?,
        	    lat=?,
        	    lon=?,
        	    nazwa_miejsc_zagr=?,
        	    nazwa_poczty=?,
        	    nr_domu=?,
        	    nr_lokalu=?,
        	    nr_skr_poczt=?,
        	    status=?,
        	    telefon1=?,
        	    telefon2=?,
        	    ulica=?
        	WHERE  id = ?""")
@SQLInsert(sql = "insert into ofz.adresy "
		+ "(data_przyj_zglosz,email,fax,kod_euro_regionu,kod_kraju,kod_pocztowy,kod_poczty_zagr,"
		+ "kod_teryt_miejsc,lat,lon,nazwa_miejsc_zagr,nazwa_poczty,nr_domu,nr_lokalu,nr_skr_poczt,"
		+ "status,telefon1,telefon2,ulica,id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
@Table(name = "adresy", schema = "ofz")
@ToString
public class Addresses implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Addresses_id_gen")
	@SequenceGenerator(name = "Addresses_id_gen", sequenceName = "seq_adresy", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "kod_kraju", nullable = false)
	private ElementCentralDict countryCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kod_pocztowy")
	private SlKodPocztowy kodPocztowy;

	@Size(max = 56)
	@Column(name = "nazwa_poczty", length = 56)
	private String nazwaPoczty;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kod_teryt_miejsc")
	private CityDict cityDict;

	@Size(max = 40)
	@Column(name = "kod_poczty_zagr", length = 40)
	private String kodPocztyZagr;

	@Size(max = 100)
	@Column(name = "nazwa_miejsc_zagr", length = 100)
	private String nazwaMiejscZagr;

	@Size(max = 65)
	@Column(name = "ulica", length = 65)
	private String ulica;

	@Size(max = 10)
	@Column(name = "nr_domu", length = 10)
	private String nrDomu;

	@Size(max = 10)
	@Column(name = "nr_lokalu", length = 10)
	private String nrLokalu;

	@Size(max = 10)
	@Column(name = "nr_skr_poczt", length = 10)
	private String nrSkrPoczt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kod_euro_regionu")
	private ElementCentralDict kodEuroRegionu;

	@Size(max = 20)
	@Column(name = "telefon1", length = 20)
	private String telefon1;

	@Size(max = 20)
	@Column(name = "telefon2", length = 20)
	private String telefon2;

	@Size(max = 20)
	@Column(name = "fax", length = 20)
	private String fax;

	@Size(max = 80)
	@Column(name = "email", length = 80)
	private String email;

	@Size(max = 1)
	@NotNull
	@Column(name = "status", nullable = false, length = 1)
	@Convert(converter = JobOfferStatusEnumConverter.class)
	private JobOfferStatusEnum status;

	@NotNull
	@Column(name = "data_przyj_zglosz", nullable = false)
	private LocalDate dataPrzyjZglosz;

	@Column(name = "lat")
	private Double latitude;
 
	@Column(name = "lon")
	private Double longitude;
	
	@Transient
	public String getNazwaMiejscowosci() {
		if (getCityDict() != null) {
			return getCityDict().getNazwaMiejsc();
		}
		return null;
	}

	@Transient
	public String getNazwaKraju() {
		if (getCountryCode() != null) {
			return getCountryCode().getOpisElem();
		}
		return null;
	}

	@Transient
	public String getNazwaWojewodztwa() {
		if (this.cityDict != null && this.cityDict.getVoivodeshipDict() != null && this.cityDict.getVoivodeshipDict().getName() != null) {
			return this.cityDict.getVoivodeshipDict().getName();
		}
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Addresses adresy = (Addresses) o;
		return Objects.equals(id, adresy.id) && Objects.equals(countryCode, adresy.countryCode) && Objects.equals(kodPocztowy, adresy.kodPocztowy) && Objects.equals(
				nazwaPoczty, adresy.nazwaPoczty) && Objects.equals(cityDict, adresy.cityDict) && Objects.equals(kodPocztyZagr, adresy.kodPocztyZagr)
				&& Objects.equals(nazwaMiejscZagr, adresy.nazwaMiejscZagr) && Objects.equals(ulica, adresy.ulica) && Objects.equals(nrDomu, adresy.nrDomu)
				&& Objects.equals(nrLokalu, adresy.nrLokalu) && Objects.equals(nrSkrPoczt, adresy.nrSkrPoczt) && Objects.equals(kodEuroRegionu,
				adresy.kodEuroRegionu) && Objects.equals(telefon1, adresy.telefon1) && Objects.equals(telefon2, adresy.telefon2) && Objects.equals(fax,
				adresy.fax) && Objects.equals(email, adresy.email) && status == adresy.status && Objects.equals(dataPrzyjZglosz, adresy.dataPrzyjZglosz)
				&& Objects.equals(latitude, adresy.latitude) && Objects.equals(longitude, adresy.longitude);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, countryCode, kodPocztowy, nazwaPoczty, cityDict, kodPocztyZagr, nazwaMiejscZagr, ulica, nrDomu, nrLokalu, nrSkrPoczt,
				kodEuroRegionu, telefon1, telefon2, fax, email, status, dataPrzyjZglosz, latitude, longitude);
	}
}