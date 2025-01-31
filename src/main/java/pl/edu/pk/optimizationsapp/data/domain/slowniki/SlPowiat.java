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

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sl_powiat", schema = "slowniki")
public class SlPowiat implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@Size(max = 4)
	@Column(name = "kod_teryt_powiatu", nullable = false, length = 4)
	private String id;

	@Column(name = "data_do")
	private LocalDate dataDo;

	@NotNull
	@Column(name = "data_od", nullable = false)
	private LocalDate dataOd;

	@Size(max = 7)
	@Column(name = "kod_podregionu", length = 7)
	private String kodPodregionu;

	@Size(max = 15)
	@NotNull
	@Column(name = "kod_sprawozd", nullable = false, length = 15)
	private String kodSprawozd;

	@Size(max = 50)
	@NotNull
	@Column(name = "nazwa_powiatu", nullable = false, length = 50)
	private String nazwaPowiatu;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kod_teryt_wojew")
	private SlWojewodztwo kodTerytWojew;
}