package pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class JobOfferPage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int numberOfJobPositions;

    private Page<JobOfferListDto> jobOfferPage;

}