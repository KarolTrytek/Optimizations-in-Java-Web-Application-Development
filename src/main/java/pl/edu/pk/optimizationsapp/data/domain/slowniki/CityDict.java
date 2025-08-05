package pl.edu.pk.optimizationsapp.data.domain.slowniki;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.locationtech.jts.geom.Point;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sl_miejscowosc", schema = "slowniki")
@DynamicInsert
@DynamicUpdate
public class CityDict implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@Size(max = 8)
	@Column(name = "kod_teryt_miejsc", nullable = false, length = 8)
	private String id;

	@Column(name = "data_do")
	private LocalDate dataDo;

	@NotNull
	@Column(name = "data_od", nullable = false)
	private LocalDate dataOd;

	@Size(max = 15)
	@NotNull
	@Column(name = "kod_sprawozd", nullable = false, length = 15)
	private String kodSprawozd;

	@Size(max = 1)
	@NotNull
	@Column(name = "miasto_wies", nullable = false, length = 1)
	private String miastoWies;

	@Size(max = 50)
	@NotNull
	@Column(name = "nazwa_miejsc", nullable = false, length = 50)
	private String nazwaMiejsc;

	@Column(name = "nazwa_miejsc_upper_ascii", nullable = false, length = 50)
	@Size(max = 50)
	@NotNull
	private String nazwaMiejscUpperAscii;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "kod_teryt_gminy", nullable = false)
	private SlGmina kodTerytGminy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kod_teryt_powiatu")
	private CountyDict kodTerytPowiatu;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kod_teryt_wojew")
	private VoivodeshipDict slWojewodztwo;

	@Size(max = 2)
	@Column(name = "rodza_miejsc", length = 2)
	private String rodzaMiejsc;

	@Column(name = "lat")
	private Double latitude;

	@Column(name = "lon")
	private Double longitude;

	@Column(name = "centra")
	private Point centra;

}