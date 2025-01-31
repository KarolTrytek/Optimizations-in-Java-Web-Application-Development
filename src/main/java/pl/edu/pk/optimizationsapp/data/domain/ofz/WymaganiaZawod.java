package pl.edu.pk.optimizationsapp.data.domain.ofz;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.SlElemSlowCentr;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "wymagania_zawod", schema = "ofz")
public class WymaganiaZawod implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wymagania_zawod_id_gen")
	@SequenceGenerator(name = "wymagania_zawod_id_gen", sequenceName = "seq_wymagania_zawod", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Long id;

	@NotNull
	@Column(name = "id_oferty_centralny", nullable = false)
	private Long idOfertyCentralny;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "kod_zawodu", nullable = false)
	private SlElemSlowCentr kodZawodu;

	@Size(max = 10)
	@NotNull
	@Column(name = "waga_wymagania", nullable = false, length = 10)
	private String wagaWymagania;

	@Column(name = "staz_wymagania", precision = 4, scale = 2)
	private BigDecimal stazWymagania;

	@Size(max = 50)
	@Column(name = "kod_zawodu_2010", length = 50)
	private String kodZawodu2010;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kod_poziomu_kwalif_zawod")
	private SlElemSlowCentr kodPoziomuKwalifZawod;

	@Size(max = 20)
	@Column(name = "id_wymagania_dziedzinowy", length = 20)
	private String idWymaganiaDziedzinowy;
}