package com.api.vaccinationmanagement;

import com.api.vaccinationmanagement.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VaccinationManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(VaccinationManagementApplication.class, args);
	}

}
