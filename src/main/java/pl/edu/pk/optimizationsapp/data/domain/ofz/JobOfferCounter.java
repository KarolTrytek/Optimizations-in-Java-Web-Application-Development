package pl.edu.pk.optimizationsapp.data.domain.ofz;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "licznik_ofert", schema = "ofz")
public class JobOfferCounter implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@Size(max = 32)
	@Column(name = "hash", nullable = false, length = 32)
	private String id;

	@NotNull
	@Column(name = "licznik", nullable = false)
	private Integer counter;

}