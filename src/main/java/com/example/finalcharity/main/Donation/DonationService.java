package com.example.finalcharity.main.Donation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.finalcharity.main.PubSub.IObserver;
import com.example.finalcharity.main.Sort.SortingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DonationService {
    private final DonationRepository donationRepository;

    @Autowired
    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public List<Donation> getDonations() {
        return donationRepository.findAll();
    }

    public Optional<Donation> getDonationById(Long id) {
        return donationRepository.findById(id);
    }

    public Donation addDonation(Donation donation) {
        notifyObservers(donation); // Notify all subscribers about the new donation
        return donationRepository.save(donation);
    }

    public Optional<Donation> updateDonation(Long id, Donation donation) {
        return donationRepository.findById(id).map(existingDonation -> {
            existingDonation.setAmount(donation.getAmount());
            existingDonation.setMessage(donation.getMessage());
            existingDonation.setCampaign(donation.getCampaign());
            existingDonation.setCurrency(donation.getCurrency());
            return donationRepository.save(existingDonation);
        });
    }

    public boolean deleteDonation(Long id) {
        if (donationRepository.existsById(id)) {
            donationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @PersistenceContext
    private EntityManager entityManager;

    private List<IObserver> subscribers = new ArrayList<>(); // List of subscribers

    public void addObserver(IObserver subscriber) {
        subscribers.add(subscriber);
    }

    public void removeObserver(IObserver subscriber) {
        subscribers.remove(subscriber);
    }

    private void notifyObservers(Donation donation) {
        for (IObserver subscriber : subscribers) {
            subscriber.update(donation); // Notify each subscriber
        }
    }

    // Method to get sorted donations for a specific campaign
    public List<Donation> getSortedDonations(SortingService sortingService, Long campaignID) {

        String sql = "SELECT * FROM donations WHERE campaign_id = :campaignID";
        Query query = entityManager.createNativeQuery(sql, Donation.class);
        query.setParameter("campaignID", campaignID);

        List<Donation> donations = query.getResultList();
        sortingService.sortDonations(donations);
        return donations;
    }

}
