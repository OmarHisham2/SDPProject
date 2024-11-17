package com.example.finalcharity.main.Donation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DonationConfig {

    @Bean
    CommandLineRunner commandLineRunner(DonationRepository donationRepository) {
        return args -> {
            // Donation donation1 = new Donation(2L, 200L, "Donation 1 for charity", 1L);
            // Donation donation2 = new Donation(2L, 200L, "Donation 2 for charity", 1L);
            // donationRepository.save(donation1);
            // donationRepository.save(donation2);
        };
    }
}
