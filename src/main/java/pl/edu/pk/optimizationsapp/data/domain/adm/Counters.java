package pl.edu.pk.optimizationsapp.data.domain.adm;

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

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "liczniki", schema = "adm")
public class Counters implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Size(max = 50)
    @SequenceGenerator(name = "liczniki_id_gen", sequenceName = "seq_definicje_parametrow", allocationSize = 1)
    @Column(name = "kod", nullable = false, length = 50)
    private String id;

    @Size(max = 255)
    @NotNull
    @Column(name = "opis", nullable = false)
    private String description;

    @NotNull
    @Column(name = "wartosc", nullable = false)
    private Long value;
}
