package pl.edu.pk.optimizationsapp.data.domain.slowniki;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
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
@Table(name = "sl_wojewodztwo", schema = "slowniki")
@DynamicInsert
@DynamicUpdate
public class SlWojewodztwo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Size(max = 2)
	@Id
	@Column(name = "kod_teryt_wojew", nullable = false, length = 2)
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
	@Column(name = "nazwa_wojew", nullable = false, length = 50)
	private String nazwaWojew;

	@Size(max = 6)
	@Column(name = "kod_euro_regionu", length = 6)
	private String kodEuroRegionu;
}
