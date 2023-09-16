package com.api.vaccinationmanagement.dto.statistical;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RateVaccinatedBySIckIdAndRegionDto {
    private Double rate;
    private Integer injected;
    private Integer total;
    private String addressCode;
}
