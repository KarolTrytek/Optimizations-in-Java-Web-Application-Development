package pl.edu.pk.optimizationsapp.data.domain.ofz;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import pl.edu.pk.optimizationsapp.data.domain.slowniki.SlElemSlowSys;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "wymagania_wyksztalcenie", schema = "ofz")
public class WymaganiaWyksztalcenie implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wymagania_wyksztalcenie_id_gen")
	@SequenceGenerator(name = "wymagania_wyksztalcenie_id_gen", sequenceName = "seq_wymagania_wyksztalcenie", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Long id;

	@NotNull
	@Column(name = "id_oferty_centralny", nullable = false)
	private Long idOfertyCentralny;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "kod_poz_wykszt", nullable = false)
	private SlElemSlowSys kodPozWykszt;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "kod_typu_wykszt", nullable = false)
	private SlElemSlowCentr kodTypuWykszt;

	@Size(max = 10)
	@NotNull
	@Column(name = "waga_wymagania", nullable = false, length = 10)
	private String wagaWymagania;

	@Size(max = 140)
	@Column(name = "kierunek_wykszt", length = 140)
	private String kierunekWykszt;

	@Size(max = 140)
	@Column(name = "spec_zawodowa_wykszt", length = 140)
	private String specZawodowaWykszt;

	@Size(max = 20)
	@Column(name = "id_wymagania_dziedzinowy", length = 20)
	private String idWymaganiaDziedzinowy;
}