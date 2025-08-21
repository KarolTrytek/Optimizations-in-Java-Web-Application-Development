package pl.edu.pk.optimizationsapp.api.queries_1.multiselectquery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOfferStatusEnum;
import pl.edu.pk.optimizationsapp.data.domain.ofz.LanguageEnum;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for filtering job offer list.
 */
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class JobOfferCriteria implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "List of job titles")
    private List<String> jobTitles;

    @Schema(description = "List of job offer types")
    private List<String> jobOfferTypes;

    @Schema(description = "List of cities with ranges")
    private List<LocalityRangeDto> cities;

    @Schema(description = "List of postal codes")
    private List<String> postalCodes;

    @Schema(description = "List of county identifiers")
    private List<String> countyIds;

    @Schema(description = "List of voivodeship identifiers")
    private List<String> voivodeshipIds;

    @Schema(description = "List of foreign country codes")
    private List<String> foreignCountryCodes;

    @Schema(description = "List of foreign city names")
    private List<String> foreignCityNames;

    @Schema(description = "List of foreign region codes")
    private List<String> foreignRegionsCodes;

    @Schema(description = "List of contract types")
    private List<ContractTypesEnum> contractTypes;

    @Schema(description = "List of foreign languages ")
    private List<ForeignLanguageDto> foreignLanguages;

    @Schema(description = "List of educations")
    private List<EducationDto> educations;

    @Schema(description = "Work experience")
    private BigDecimal workExperience;

    @Schema(description = "List of professions with work experience")
    private List<ProfessionDto> professions;

    @Schema(description = "Is the employer")
    private Boolean isEmployer;

    @Schema(description = "Is the employer an entrepreneur")
    private Boolean isEntrepreneur;

    @Schema(description = "Is the employer a natural person")
    private Boolean isNaturalPerson;

    @Schema(description = "List of employers")
    private List<EmployerDictionaryDto> employers;

    @Schema(description = "Date added from")
    private LocalDate dateAddedFrom;

    @Schema(description = "Date added to")
    private LocalDate dateAddedTo;

    @Schema(description = "Reported date from")
    private LocalDate reportedDateFrom = LocalDate.of(2024, 1, 1);

    @Schema(description = "Reported date to")
    private LocalDate reportedDateTo = LocalDate.of(2025,1,1);

    @Schema(description = "Salary from", example = "2000")
    private BigDecimal salaryFrom;

    @Schema(description = "Salary to", example = "2000")
    private BigDecimal salaryTo;

    @Schema(description = "Employment fraction", example = "0.25")
    private BigDecimal employmentFraction;

    @Schema(description = "Identifiers of work modes")
    private List<String> workModes;

    @Schema(description = "Are the offers intended for people with disabilities", example = "true")
    private Boolean forDisabledPersons;

    @Schema(description = "Chosen language", example = "EN")
    @NotNull
    @Builder.Default
    private LanguageEnum language = LanguageEnum.PL;

    @Schema(description = "Lista of job offer statuses")
    @Builder.Default
    private List<JobOfferStatusEnum> jobOfferStatusList = new ArrayList<>(List.of(JobOfferStatusEnum.ACTIVE));

    @Schema(description = "Show only foreign offers (if false, only domestic offers)", example = "true")
    private Boolean abroad;

}
