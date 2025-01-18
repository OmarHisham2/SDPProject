package com.example.finalcharity.main.User.CLIs;

import com.example.finalcharity.main.Campaign.Campaign;
import com.example.finalcharity.main.Campaign.CampaignService;
import com.example.finalcharity.main.Donation.Donation;
import com.example.finalcharity.main.Donation.DonationService;
import com.example.finalcharity.main.Sort.SortingService;
import com.example.finalcharity.main.User.AdminUser;
import com.example.finalcharity.main.User.CharityOrganizationUser;
import com.example.finalcharity.main.User.ConsoleColors;
import com.example.finalcharity.main.User.NormalUser;
import com.example.finalcharity.main.User.User;
import com.example.finalcharity.main.User.UserService;
import com.example.finalcharity.main.User.Admin.AdminProxy;
import com.example.finalcharity.main.User.Admin.RealAdmin;
import com.example.finalcharity.main.facade.CharityOperationsFacade;
import com.example.finalcharity.main.facade.dto.CampaignStatistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class DonationsAdminCLI {



    private final UserService userService;
    private final CampaignService campaignService;
    private final RealAdmin realAdmin;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CharityOperationsFacade charityFacade;
    private final DonationService donationService;


    private final SortingService sortingService;


    @Autowired
    public DonationsAdminCLI(UserService userService, @Lazy RealAdmin realAdmin, CampaignService campaignService, CharityOperationsFacade charityFacade,DonationService donationService
    ,SortingService sortingService) {
        this.userService = userService;
        this.realAdmin = realAdmin;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.campaignService = campaignService;
        this.charityFacade = charityFacade;
        this.donationService = donationService;
        this.sortingService = sortingService;

    }

    private void printEncodedPassword(String rawPassword) {
        String encoded = passwordEncoder.encode(rawPassword);
        System.out.println("Encoded password: " + encoded);
    }

    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to the Donations Administration CLI");

            // Login Step
            System.out.print("Enter email: ");
            String email = scanner.nextLine();

            

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            printEncodedPassword(password);

            User user = userService.getAllUsers().stream()
                    .filter(u -> u.getEmail().equalsIgnoreCase(email) &&  passwordEncoder.matches(password, u.getPassword()))
                    .findFirst()
                    .orElse(null);

            if (user == null) {
                System.out.println("Invalid credentials! Exiting...");
                return;
            }

            if (!(user instanceof AdminUser)) {
                System.out.println("Access denied! Only admin accounts are allowed.");
                return;
            }

            System.out.println("\n" + ConsoleColors.GREEN_BOLD_BRIGHT + "Login successful. Welcome, " + user.getName() + ConsoleColors.RESET);

            // Create AdminProxy with the authenticated user
            AdminProxy adminProxy = new AdminProxy(user, realAdmin);

            // Admin Menu
            boolean running = true;
            while (running) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "\nDonations Management Menu:" + ConsoleColors.RESET);

                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "\nList of Available Campaign:" + ConsoleColors.RESET);

List<CampaignStatistics> allCampaignStats = charityFacade.getAllCampaignStatistics();
for (int i = 0; i < allCampaignStats.size(); i++) {
    CampaignStatistics stats = allCampaignStats.get(i);
    System.out.printf(ConsoleColors.BLACK_BACKGROUND_BRIGHT + "%d. Campaign ID: " +  campaignService.getAllCampaigns().get(i).getID() + ConsoleColors.RESET, i + 1);
    System.out.printf("   \nTotal Donations: %d%n", stats.getTotalDonations());
    System.out.printf("   Total Amount: %.2f%n", stats.getTotalAmount());
    System.out.printf("   Average Donation: %.2f%n", stats.getAverageDonation());
    System.out.printf("   Completion Percentage: %.2f%%%n", stats.getCompletionPercentage());
    System.out.printf("   Active: %b%n%n", stats.isActive());
}

                System.out.println("1. View Donation List Of A User");
                System.out.println("2. View Donation List Of A Campaign");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {

                    case 1 -> {

                    }
                    case 2 -> {
                        System.out.print("Enter Campaign ID: ");
                        Long enteredCampaignID = scanner.nextLong();
                        List<Donation> donations = donationService.getSortedDonations(sortingService, 9L);
                        
                        System.out.println(ConsoleColors.BLUE_BACKGROUND_BRIGHT + donations.size() + ConsoleColors.RESET);
                            
                    }
                 
                    case 3 -> {
                        System.out.println("Exiting Donations Admin CLI...");
                        running = false;
                    }
                    default -> System.out.println("Invalid choice! Please try again.");
                }
            }
        }
    }
}
