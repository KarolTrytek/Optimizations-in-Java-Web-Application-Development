package pl.edu.pk.optimizationsapp.data.domain;

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
@Table(name = "sl_wart_dod", schema = "slowniki")
public class SlWartDod implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_wartosci", nullable = false)
    @SequenceGenerator(name = "seq_sl_wart_dod", sequenceName = "slowniki.seq_sl_wart_dod", allocationSize = 1)
    @GeneratedValue(generator = "seq_sl_wart_dod", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Size(max = 50)
	@NotNull
	@Column(name = "id_pozycji", nullable = false, length = 50)
	private String idPozycji;

	@NotNull
	@Column(name = "version", nullable = false, columnDefinition = "integer default 0")
	private Integer version;

	@Size(max = 20)
	@NotNull
	@Column(name = "tabela", nullable = false, length = 20)
	private String tabela;

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

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_def_wart_dod", nullable = false)
	private SlDefWartDod idDefWartDod;
}