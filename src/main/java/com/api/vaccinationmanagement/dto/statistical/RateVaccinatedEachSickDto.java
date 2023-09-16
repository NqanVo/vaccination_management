package com.api.vaccinationmanagement.dto.statistical;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RateVaccinatedEachSickDto {
    private Double rate;
    private String nameSick;

}
