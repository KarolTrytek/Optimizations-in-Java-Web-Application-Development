package pl.edu.pk.optimizationsapp.data.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Builder
@Table(name = "sl_def_slow", schema = "slowniki")
public class SlDefSlow implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Size(max = 15)
    @Column(name = "id_slownika", nullable = false, length = 15)
    private String id;

    @Column(name = "data_konca")
    private LocalDate dataKonca;

    @NotNull
    @Column(name = "data_pocz", nullable = false)
    private LocalDate dataPocz;

    @Size(max = 50)
    @NotNull
    @Column(name = "nazwa_slow", nullable = false, length = 50)
    private String nazwaSlow;

    @Size(max = 50)
    @Column(name = "nzw_proc_uruchom", length = 50)
    private String nzwProcUruchom;

    @Size(max = 200)
    @Column(name = "obszar", length = 200)
    private String obszar;

    @Size(max = 500)
    @Column(name = "opis_slow", length = 500)
    private String opisSlow;

    @Size(max = 1)
    @NotNull
    @Column(name = "rodzaj_slownika", nullable = false, length = 1)
    private String rodzajSlownika;

    @Size(max = 1)
    @NotNull
    @Column(name = "typ_slownika", nullable = false, length = 1)
    private String typSlownika;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dataKonca == null) ? 0 : dataKonca.hashCode());
        result = prime * result + ((dataPocz == null) ? 0 : dataPocz.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nazwaSlow == null) ? 0 : nazwaSlow.hashCode());
        result = prime * result + ((nzwProcUruchom == null) ? 0 : nzwProcUruchom.hashCode());
        result = prime * result + ((obszar == null) ? 0 : obszar.hashCode());
        result = prime * result + ((opisSlow == null) ? 0 : opisSlow.hashCode());
        result = prime * result + ((rodzajSlownika == null) ? 0 : rodzajSlownika.hashCode());
        result = prime * result + ((typSlownika == null) ? 0 : typSlownika.hashCode());
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
        SlDefSlow other = (SlDefSlow) obj;
        if (dataKonca == null) {
            if (other.dataKonca != null)
                return false;
        } else if (!dataKonca.equals(other.dataKonca))
            return false;
        if (dataPocz == null) {
            if (other.dataPocz != null)
                return false;
        } else if (!dataPocz.equals(other.dataPocz))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nazwaSlow == null) {
            if (other.nazwaSlow != null)
                return false;
        } else if (!nazwaSlow.equals(other.nazwaSlow))
            return false;
        if (nzwProcUruchom == null) {
            if (other.nzwProcUruchom != null)
                return false;
        } else if (!nzwProcUruchom.equals(other.nzwProcUruchom))
            return false;
        if (obszar == null) {
            if (other.obszar != null)
                return false;
        } else if (!obszar.equals(other.obszar))
            return false;
        if (opisSlow == null) {
            if (other.opisSlow != null)
                return false;
        } else if (!opisSlow.equals(other.opisSlow))
            return false;
        if (rodzajSlownika == null) {
            if (other.rodzajSlownika != null)
                return false;
        } else if (!rodzajSlownika.equals(other.rodzajSlownika))
            return false;
        if (typSlownika == null) {
            return other.typSlownika == null;
        } else return typSlownika.equals(other.typSlownika);
    }
}