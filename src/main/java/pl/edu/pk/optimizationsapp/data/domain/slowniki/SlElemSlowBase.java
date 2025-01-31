package pl.edu.pk.optimizationsapp.data.domain.slowniki;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class SlElemSlowBase implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

	@Id
	@Size(max = 50)
	@SequenceGenerator(name = "sl_elem_slow_centr_id_gen", sequenceName = "seq_sl_def_wart_dod", allocationSize = 1)
	@Column(name = "id_pozycji", nullable = false, length = 50)
	protected String id;

	@Column(name = "data_do")
	protected LocalDate dataDo;

	@NotNull
	@Column(name = "data_od", nullable = false)
	protected LocalDate dataOd;
	
	@NotNull
    @Size(max = 1)
	@Column(name = "grupujaca", nullable = false, length = 1)
	protected String grupujaca;

	@Size(max = 15)
	@NotNull
	@Column(name = "kod_elem", nullable = false, length = 15)
	protected String kodElem;

	@Size(max = 15)
	@Column(name = "kod_poz_nadrz", length = 15)
	protected String kodPozNadrz;

	@Size(max = 15)
	@NotNull
	@Column(name = "kod_sprawozd", nullable = false, length = 15)
	protected String kodSprawozd;

	@Size(max = 500)
	@Column(name = "opis_elem", length = 500)
	protected String opisElem;

    @Size(max = 1)
	@NotNull
	@Column(name = "wybieralna", nullable = false, length = 1)
	protected String wybieralna;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_slownika", nullable = false)
	protected SlDefSlow idSlownika;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idPozycji")
    protected Set<SlWartDod> slWartDods = new HashSet<>();
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dataDo == null) ? 0 : dataDo.hashCode());
        result = prime * result + ((dataOd == null) ? 0 : dataOd.hashCode());
        result = prime * result + ((grupujaca == null) ? 0 : grupujaca.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((kodElem == null) ? 0 : kodElem.hashCode());
        result = prime * result + ((kodPozNadrz == null) ? 0 : kodPozNadrz.hashCode());
        result = prime * result + ((kodSprawozd == null) ? 0 : kodSprawozd.hashCode());
        result = prime * result + ((opisElem == null) ? 0 : opisElem.hashCode());
        result = prime * result + ((idSlownika == null) ? 0 : idSlownika.hashCode());
        result = prime * result + ((wybieralna == null) ? 0 : wybieralna.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SlElemSlowBase other = (SlElemSlowBase) obj;
        if (dataDo == null) {
            if (other.dataDo != null)
                return false;
        } else if (!dataDo.equals(other.dataDo))
            return false;
        if (dataOd == null) {
            if (other.dataOd != null)
                return false;
        } else if (!dataOd.equals(other.dataOd))
            return false;
        if (grupujaca == null) {
            if (other.grupujaca != null)
                return false;
        } else if (!grupujaca.equals(other.grupujaca))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (kodElem == null) {
            if (other.kodElem != null)
                return false;
        } else if (!kodElem.equals(other.kodElem))
            return false;
        if (kodPozNadrz == null) {
            if (other.kodPozNadrz != null)
                return false;
        } else if (!kodPozNadrz.equals(other.kodPozNadrz))
            return false;
        if (kodSprawozd == null) {
            if (other.kodSprawozd != null)
                return false;
        } else if (!kodSprawozd.equals(other.kodSprawozd))
            return false;
        if (opisElem == null) {
            if (other.opisElem != null)
                return false;
        } else if (!opisElem.equals(other.opisElem))
            return false;
        if (idSlownika == null) {
            if (other.idSlownika != null)
                return false;
        } else if (!idSlownika.equals(other.idSlownika))
            return false;
        if (wybieralna == null) {
            return other.wybieralna == null;
        } else return wybieralna.equals(other.wybieralna);
    }

    @Override
    public String toString() {
        return "SlElemSlowBase{" +
                "idPozycji='" + id + '\'' +
                ", opisElem='" + opisElem + '\'' +
                '}';
    }

    
    
}
