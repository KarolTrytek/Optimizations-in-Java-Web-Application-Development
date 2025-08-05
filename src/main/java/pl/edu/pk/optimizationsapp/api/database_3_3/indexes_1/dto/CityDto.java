package pl.edu.pk.optimizationsapp.api.database_3_3.indexes_1.dto;

public record CityDto(

        String id,
        String city,
        String county,
        String countyId,
        String voivodeship,
        String postalCode
) {
    public String getCityCountyVoivodeshipDescription() {
        return city() + ", " + county() + ", " + voivodeship();
    }
}
