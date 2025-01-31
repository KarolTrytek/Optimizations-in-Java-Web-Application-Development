package pl.edu.pk.optimizationsapp.data.domain.slowniki;

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

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sl_def_wart_dod", schema = "slowniki")
public class SlDefWartDod implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sl_def_wart_dod_id_gen")
	@SequenceGenerator(name = "sl_def_wart_dod_id_gen", sequenceName = "seq_sl_def_wart_dod", allocationSize = 1)
	@Column(name = "id_def_wart_dod", nullable = false)
	private Long id;

	@Size(max = 50)
	@NotNull
	@Column(name = "nazwa", nullable = false, length = 50)
	private String nazwa;

	@NotNull
	@Column(name = "version", nullable = false, columnDefinition = "integer default 0")
	private Integer version;

	@Size(max = 300)
	@Column(name = "opis", length = 300)
	private String opis;

	@Size(max = 1)
	@NotNull
	@Column(name = "czy_slownikowany", nullable = false, length = 1)
	private String czySlownikowany;

	@Size(max = 1)
	@NotNull
	@Column(name = "czy_wyswietlac", nullable = false, length = 1)
	private String czyWyswietlac;

	@Size(max = 500)
	@Column(name = "format", length = 500)
	private String format;

	@NotNull
	@Column(name = "nr_kolumny", nullable = false)
	private Integer nrKolumny;

	@Size(max = 2)
	@NotNull
	@Column(name = "typ_wart", nullable = false, length = 2)
	private String typWart;

	@Column(name = "wartosc_da")
	private LocalDate wartoscDa;

	@Column(name = "wartosc_i")
	private Integer wartoscI;

	@Size(max = 100)
	@Column(name = "wartosc_l", length = 100)
	private String wartoscL;

	@Column(name = "wartosc_d", precision = 10, scale = 2)
	private BigDecimal wartoscD;

	@Size(max = 400)
	@Column(name = "wartosc_c", length = 400)
	private String wartoscC;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_slownika")
	private SlDefSlow idSlownika;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_slownika_wartosc")
	private SlDefSlow idSlownikaWartosc;
}