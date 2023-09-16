package com.api.vaccinationmanagement.dto.statistical;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticalRateVaccinatedEachSick {
    private List<RateVaccinatedEachSickDto> rateVaccinatedEachSickDtoList = new ArrayList<>();
    private Integer totalPatient;
}
