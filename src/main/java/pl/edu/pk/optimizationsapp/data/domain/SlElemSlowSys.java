package pl.edu.pk.optimizationsapp.data.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sl_elem_slow_sys", schema = "slowniki")
public class SlElemSlowSys extends SlElemSlowBase {

}