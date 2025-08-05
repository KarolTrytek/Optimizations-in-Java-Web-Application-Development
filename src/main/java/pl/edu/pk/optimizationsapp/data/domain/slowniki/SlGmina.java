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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sl_gmina", schema = "slowniki")
public class SlGmina implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@Size(max = 6)
	@Column(name = "kod_teryt_gminy", nullable = false, length = 6)
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

	@Size(max = 50)
	@NotNull
	@Column(name = "nazwa_gminy", nullable = false, length = 50)
	private String nazwaGminy;

	@Size(max = 20)
	@Column(name = "typ_gminy", length = 20)
	private String typGminy;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "kod_teryt_powiatu", nullable = false)
	private CountyDict kodTerytPowiatu;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kod_teryt_wojew")
	private VoivodeshipDict kodTerytWojew;
}