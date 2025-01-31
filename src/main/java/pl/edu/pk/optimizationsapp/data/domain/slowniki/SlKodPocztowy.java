package pl.edu.pk.optimizationsapp.data.domain.slowniki;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sl_kod_pocztowy", schema = "slowniki")
public class SlKodPocztowy implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Size(max = 6)
    @SequenceGenerator(name = "sl_kod_pocztowy_id_gen", sequenceName = "seq_sl_def_wart_dod", allocationSize = 1)
    @Column(name = "kod_poczty", nullable = false, length = 6)
    private String id;

    @NotNull
    @Column(name = "pna", nullable = false)
    private Boolean pna = false;

    @NotNull
    @Column(name = "skrytka", nullable = false)
    private Boolean skrytka = false;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;

    @Formula("ST_transform(centra, " + 3035 + ")")
    private Point centra;

    @Formula("ST_transform(geom, " + 3035 + ")")
    private MultiPolygon geom;

}