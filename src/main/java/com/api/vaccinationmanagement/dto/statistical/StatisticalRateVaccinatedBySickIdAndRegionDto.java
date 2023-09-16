package com.api.vaccinationmanagement.dto.statistical;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticalRateVaccinatedBySickIdAndRegionDto {
    private List<RateVaccinatedBySIckIdAndRegionDto> region = new ArrayList<>();
    private Integer sickId;
    private String sickName;
}
