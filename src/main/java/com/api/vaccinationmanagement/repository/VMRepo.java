package com.api.vaccinationmanagement.repository;

import com.api.vaccinationmanagement.model.PatientModel;
import com.api.vaccinationmanagement.model.SickModel;
import com.api.vaccinationmanagement.model.VMModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VMRepo extends JpaRepository<VMModel, Integer> {
    List<VMModel> findVMModelsByPatientModel(PatientModel patientModel);

    List<VMModel> findVMModelsBySickModel(SickModel sickModel);

    List<VMModel> findVMModelsByPatientModelAndSickModel(PatientModel patientModel, SickModel sickModel);

    @Query("SELECT vm.patientModel FROM vaccination_management vm " +
            "JOIN vm.patientModel p " +
            "WHERE vm.sickModel.id = :sickId AND p.addressCode LIKE :addressCode " +
            "GROUP BY vm.patientModel " +
            "HAVING COUNT(vm.sickModel) >= :minDoses")
    Page<PatientModel> findPatientVaccinatedWithSickIdAndMinDosesAndAddressCode(Integer sickId, Integer minDoses, String addressCode, Pageable pageable);

    @Query(value = "SELECT round((count(DISTINCT vm.patient_id) / (SELECT count(*) from patient)),4) as rate, " +
            "s.name as nameSick " +
            "from vaccination_management vm RIGHT join sick s on vm.sick_id = s.id " +
            "GROUP by s.id", nativeQuery = true)
    List<Object[]> findRatePatientVaccinatedEachSick();

    @Query("SELECT s.id AS sick_id, s.name AS sick_name, " +
            "LEFT(p.addressCode, 4) AS address_code, " +
            "COUNT(DISTINCT p.id) AS total_vaccinated, " +
            "(SELECT COUNT(*) FROM patient p2 WHERE LEFT(p2.addressCode, 4) = LEFT(p.addressCode, 4)) AS total_patients " +
            "FROM patient p LEFT JOIN vaccination_management vm ON p.id = vm.patientModel.id LEFT JOIN sick s ON vm.sickModel.id = s.id " +
            "WHERE s.id = :sickId " +
            "GROUP BY LEFT(p.addressCode, 4)")
    List<Object[]> findRatePatientVaccinatedBySickIdAndAllCity(Integer sickId);

    @Query("SELECT s.id AS sick_id, s.name AS sick_name, " +
            "LEFT(p.addressCode, 9) AS address_code, " +
            "COUNT(DISTINCT p.id) AS total_vaccinated, " +
            "(SELECT COUNT(*) FROM patient p2 WHERE LEFT(p2.addressCode, 9) = LEFT(p.addressCode, 9)) AS total_patients " +
            "FROM patient p LEFT JOIN vaccination_management vm ON p.id = vm.patientModel.id LEFT JOIN sick s ON vm.sickModel.id = s.id " +
            "WHERE s.id = :sickId AND LEFT(p.addressCode, 4) = :addressCode " +
            "GROUP BY LEFT(p.addressCode, 9)")
    List<Object[]> findRatePatientVaccinatedBySickIdAndCity(Integer sickId, String addressCode);

    @Query("SELECT s.id AS sick_id, s.name AS sick_name, p.addressCode, " +
            "COUNT(DISTINCT p.id) AS total_vaccinated, " +
            "(SELECT COUNT(*) FROM patient p2 WHERE p2.addressCode = p.addressCode) AS total_patients " +
            "FROM patient p LEFT JOIN vaccination_management vm ON p.id = vm.patientModel.id LEFT JOIN sick s ON vm.sickModel.id = s.id " +
            "WHERE s.id = :sickId AND LEFT(p.addressCode, 9) = :addressCode " +
            "GROUP BY p.addressCode")
    List<Object[]> findRatePatientVaccinatedBySickIdAndDistrict(Integer sickId, String addressCode);

    @Query("SELECT s.id AS sick_id, s.name AS sick_name, p.addressCode, " +
            "COUNT(DISTINCT p.id) AS total_vaccinated, " +
            "(SELECT COUNT(*) FROM patient p2 WHERE p2.addressCode = p.addressCode) AS total_patients " +
            "FROM patient p LEFT JOIN vaccination_management vm ON p.id = vm.patientModel.id LEFT JOIN sick s ON vm.sickModel.id = s.id " +
            "WHERE s.id = :sickId AND LEFT(p.addressCode, 14) = :addressCode " +
            "GROUP BY p.addressCode")
    List<Object[]> findRatePatientVaccinatedBySickIdAndCommune(Integer sickId, String addressCode);
}