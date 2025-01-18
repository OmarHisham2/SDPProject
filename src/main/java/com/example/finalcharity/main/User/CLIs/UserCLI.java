package com.example.finalcharity.main.User.CLIs;

import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.finalcharity.main.Authentication.DBAuthenticator;
import com.example.finalcharity.main.Campaign.Campaign;
import com.example.finalcharity.main.Campaign.CampaignService;
import com.example.finalcharity.main.User.AdminUser;
import com.example.finalcharity.main.User.CharityOrganizationUser;
import com.example.finalcharity.main.User.NormalUser;
import com.example.finalcharity.main.User.User;
import com.example.finalcharity.main.User.UserService;

@Component
    public class UserCLI {
    
        private final UserService userService;
        private final CampaignService campaignService;

        private final PasswordEncoder passwordEncoder;


        private final DBAuthenticator dbAuthenticator;
    
        @Autowired
        public UserCLI(UserService userService, @Lazy CampaignService campaignService,PasswordEncoder passwordEncoder, DBAuthenticator dbAuthenticator) {
            this.userService = userService;
            this.campaignService = campaignService;
            this.passwordEncoder = passwordEncoder;
            this.dbAuthenticator = dbAuthenticator;
        }
    
        public void start() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome to the User CLI");
    
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.print("Enter your choice: ");
            int initialChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline
    
            if (initialChoice == 1) {
                login(scanner);
            } else if (initialChoice == 2) {
                register(scanner);
            } else {
                System.out.println("Invalid choice! Exiting...");
            }
        }
    
        private void login(Scanner scanner) {
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
    
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
    
            User user = dbAuthenticator.login(email, password);
            if (user != null) {
            
            System.out.println("\nLogin successful. Welcome, " + user.getName());
               
            }
            else { 
                System.out.println("Invalid credentials! Exiting...");
                return;
            }
    
            showUserMenu(scanner, user);
        }
    
        private void register(Scanner scanner) {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
    
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
    
            System.out.print("Enter phone: ");
            String phone = scanner.nextLine();
    
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
    
            System.out.println("Select User Type:");
            System.out.println("1. Normal User");
            System.out.println("2. Admin User");
            System.out.println("3. Charity Organization User");
            System.out.print("Enter your choice: ");
            int userTypeChoice = scanner.nextInt();
            scanner.nextLine();
    
            int userType = switch (userTypeChoice) {
                case 1 -> 1; // Normal User
                case 2 -> 2; // Admin User
                case 3 -> 0; // Charity Organization User
                default -> {
                    System.out.println("Invalid user type! Registration cancelled.");
                    yield -1;
                }
            };
    
            if (userType == -1) return;
    
            Optional<User> existingUser = userService.getAllUsers().stream()
                    .filter(u -> u.getEmail().equalsIgnoreCase(email))
                    .findFirst();
    
            if (existingUser.isPresent()) {
                System.out.println("User with this email already exists! Registration failed.");
                return;
            }
    
            User newUser = switch (userType) {
                case 2 -> new AdminUser();
                case 1 -> new NormalUser();
                case 0 -> new CharityOrganizationUser();
                default -> new User();
            };
    
            newUser.setName(name);
            newUser.setEmail(email);
            newUser.setPhone(phone);
            newUser.setPassword(passwordEncoder.encode(password));
            newUser.setUserType(userType);
    
            userService.addUser(newUser);
            System.out.println("Registration successful! You can now login.");
        }
    
        private void showUserMenu(Scanner scanner, User user) {
            boolean running = true;
            while (running) {
                System.out.println("\nUser Menu:");
                System.out.println("1. Donate to a Campaign");
                System.out.println("2. View Campaign Details");
                System.out.println("3. Update Personal Information");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
    
                int choice = scanner.nextInt();
                scanner.nextLine();
    
                switch (choice) {
                    case 1 -> donateToCampaign(scanner, user);
                    case 2 -> viewCampaignDetails(scanner);
                    case 3 -> updatePersonalInfo(scanner, user);
                    case 4 -> {
                        System.out.println("Exiting User CLI...");
                        running = false;
                    }
                    default -> System.out.println("Invalid choice! Please try again.");
                }
            }
        }
    
        private void donateToCampaign(Scanner scanner, User user) {
            System.out.print("Enter Campaign ID to donate to: ");
            long campaignId = scanner.nextLong();
            scanner.nextLine();
    
            Optional<Campaign> optionalCampaign = campaignService.getCampaignById(campaignId);
            if (optionalCampaign.isEmpty()) {
                System.out.println("Campaign not found!");
                return;
            }
    
            Campaign campaign = optionalCampaign.get();
    
            System.out.print("Enter donation amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
    
            System.out.println("Choose payment method:");
            System.out.println("1. MasterCard");
            System.out.println("2. InstaPay");
            System.out.print("Enter your choice: ");
            int paymentChoice = scanner.nextInt();
            scanner.nextLine();
    
            String paymentMethod = switch (paymentChoice) {
                case 1 -> "MasterCard";
                case 2 -> "InstaPay";
                default -> {
                    System.out.println("Invalid payment method! Cancelling donation.");
                    yield null;
                }
            };
    
            if (paymentMethod != null) {
                campaignService.donateToCampaign(campaign, amount, 0);
                System.out.println("Donation successful!");
            }
        }
    
        private void viewCampaignDetails(Scanner scanner) {
            System.out.print("Enter Campaign ID to view details: ");
            long campaignId = scanner.nextLong();
            scanner.nextLine();
    
            Optional<Campaign> optionalCampaign = campaignService.getCampaignById(campaignId);
            if (optionalCampaign.isEmpty()) {
                System.out.println("Campaign not found!");
                return;
            }
    
            Campaign campaign = optionalCampaign.get();
            System.out.println("\nCampaign Details:");
            System.out.println("ID: " + campaign.getID());
            System.out.println("Name: " + campaign.getName());
            System.out.println("Description: " + campaign.getDescription());
            System.out.println("Goal Amount: $" + campaign.getGoalAmount());
            System.out.println("Current Donations: $" + campaign.getCurrentAmount());
        }
    
        private void updatePersonalInfo(Scanner scanner, User user) {
            System.out.println("\nUpdate Personal Information");
            System.out.println("1. Update Name");
            System.out.println("2. Update Email");
            System.out.println("3. Update Phone");
            System.out.print("Enter your choice: ");
    
            int choice = scanner.nextInt();
            scanner.nextLine();
    
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    user.setName(newName);
                }
                case 2 -> {
                    System.out.print("Enter new email: ");
                    String newEmail = scanner.nextLine();
                    user.setEmail(newEmail);
                }
                case 3 -> {
                    System.out.print("Enter new phone number: ");
                    String newPhone = scanner.nextLine();
                    user.setPhone(newPhone);
                }
                default -> {
                    System.out.println("Invalid choice! Returning to menu...");
                    return;
                }
            }
    
            userService.updateUser(user.getUserId(), user);
            System.out.println("Personal information updated successfully.");
        }
    }