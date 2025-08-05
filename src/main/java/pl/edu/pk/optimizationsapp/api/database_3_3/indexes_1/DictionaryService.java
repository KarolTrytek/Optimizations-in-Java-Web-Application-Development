package pl.edu.pk.optimizationsapp.api.database_3_3.indexes_1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pk.optimizationsapp.api.database_3_3.indexes_1.dto.DictionaryDto;
import pl.edu.pk.optimizationsapp.data.domain.ofz.JobOfferStatusEnum;
import pl.edu.pk.optimizationsapp.data.domain.ofz.LanguageEnum;
import pl.edu.pk.optimizationsapp.data.domain.slowniki.CountyDict;
import pl.edu.pk.optimizationsapp.data.repository.CityDictRepository;
import pl.edu.pk.optimizationsapp.data.repository.CountyDictRepository;
import pl.edu.pk.optimizationsapp.data.repository.JobOfferRepository;
import pl.edu.pk.optimizationsapp.data.repository.VoivodeshipDictRepository;
import pl.edu.pk.optimizationsapp.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static pl.edu.pk.optimizationsapp.api.database_3_3.indexes_1.dto.LocalizationTypeEnum.COUNTY;
import static pl.edu.pk.optimizationsapp.api.database_3_3.indexes_1.dto.LocalizationTypeEnum.VOIVODESHIP;

@Slf4j
@Service
@RequiredArgsConstructor
public class DictionaryService {

    private final JobOfferRepository jobOfferRepository;

    private final VoivodeshipDictRepository voivodeshipDictRepository;

    private final CityDictRepository cityDictRepository;

    private final CountyDictRepository countyDictRepository;

    public List<String> getJobTitlesAutocomplete(String name, Long limit, JobOfferStatusEnum status, LanguageEnum language) {
        return jobOfferRepository.findJobTitleByJobTitleContainingIgnoreCaseAndLanguageLimit(name, language.getId(), status, limit);
    }

    public List<DictionaryDto> getLocalizationAutocomplete(String localization, Long limit, Boolean optimized) {
        List<DictionaryDto> response;

        var localizationUpperAscii = StringUtils.normalizePolishToAscii(localization.trim()).toUpperCase();

        var suggestVoivodeship = (optimized
                ? voivodeshipDictRepository.findByNameUpperAsciiStartingWithOrderByName(localizationUpperAscii)
                : voivodeshipDictRepository.findByUnaccentNameStartingWithOrderByName(localizationUpperAscii))
                .stream()
                .map(w -> new DictionaryDto(VOIVODESHIP + ":" + w.getId(), w.getName()))
                .toList();
        response = new ArrayList<>(suggestVoivodeship);

        var suggestCity = (optimized
                ? cityDictRepository.findByNameUpperAscii(localizationUpperAscii)
                : cityDictRepository.findByUnaccentName(localizationUpperAscii))
                .stream()
                .map(c -> new DictionaryDto(COUNTY + ":" + c.countyId(), c.getCityCountyVoivodeshipDescription()))
                .toList();
        response.addAll(suggestCity);
        var suggestCounty = (optimized
                ? countyDictRepository.findByNameUpperAsciiStartingWithOrderByName(localizationUpperAscii)
                : countyDictRepository.findByUnaccentNameStartingWithOrderByName(localizationUpperAscii))
                .stream()
                .map(c -> new DictionaryDto(COUNTY + ":" + c.getId(), getNameCountyVoivodeship(c)))
                .toList();
        response.addAll(suggestCounty);

        return response.size() > limit ? response.subList(0, Math.toIntExact(limit)) : response;
    }

    private String getNameCountyVoivodeship(CountyDict county) {
        return county.getName() + ", " + county.getVoivodeshipCode().getName();
    }
}