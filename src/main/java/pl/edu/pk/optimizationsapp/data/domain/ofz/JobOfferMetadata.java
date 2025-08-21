package pl.edu.pk.optimizationsapp.data.domain.ofz;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@DynamicUpdate
@Entity
@Table(name = "oferta_metadane", schema = "ofz", uniqueConstraints = @UniqueConstraint(columnNames = "id_oferty_centralny"))
public class JobOfferMetadata implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "id_oferty_centralny", unique = true, nullable = false)
	private Long id;

	@MapsId
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_oferty_centralny", nullable = false)
	private JobOffer jobOffer;

	@Column(name = "id_kom_add")
	private Long idKomAdd;

	@Column(name = "id_kom_mod")
	private Long idKomMod;

	@Column(name = "id_kom_del")
	private Long idKomDel;

	@NotNull
	@Column(name = "data_zgloszenia_cbop", nullable = false)
	private LocalDateTime systemSubmissionDate;

	@Column(name = "data_ost_modyfikacji_cbop")
	private LocalDateTime lastModifiedDate;

	@Column(name = "data_usuniecia_cbop")
	private LocalDateTime deletionDate;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		JobOfferMetadata that = (JobOfferMetadata) o;
		return Objects.equals(id, that.id) && Objects.equals(jobOffer, that.jobOffer) && Objects.equals(idKomAdd, that.idKomAdd) && Objects.equals(
				idKomMod, that.idKomMod) && Objects.equals(idKomDel, that.idKomDel) && Objects.equals(systemSubmissionDate, that.systemSubmissionDate)
				&& Objects.equals(lastModifiedDate, that.lastModifiedDate) && Objects.equals(deletionDate, that.deletionDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, jobOffer, idKomAdd, idKomMod, idKomDel, systemSubmissionDate, lastModifiedDate, deletionDate);
	}
}