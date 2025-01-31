package pl.edu.pk.optimizationsapp.data.domain.slowniki;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sl_elem_slow_centr", schema = "slowniki")
@DynamicInsert
@DynamicUpdate
@ToString(callSuper = true)
public class SlElemSlowCentr extends SlElemSlowBase {

}