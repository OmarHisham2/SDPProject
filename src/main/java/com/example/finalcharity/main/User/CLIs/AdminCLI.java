package com.example.finalcharity.main.User.CLIs;

import com.example.finalcharity.main.Authentication.DBAuthenticator;
import com.example.finalcharity.main.Campaign.Campaign;
import com.example.finalcharity.main.Campaign.CampaignService;
import com.example.finalcharity.main.User.*;
import com.example.finalcharity.main.User.Admin.AdminProxy;
import com.example.finalcharity.main.User.Admin.RealAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

@Component
public class AdminCLI {

    private final UserService userService;
    private final CampaignService campaignService;
    private final RealAdmin realAdmin;
    private final BCryptPasswordEncoder passwordEncoder;
    private final DBAuthenticator dbAuthenticator;

    @Autowired
    public AdminCLI(UserService userService, @Lazy RealAdmin realAdmin, CampaignService campaignService, DBAuthenticator dbAuthenticator) {
        this.userService = userService;
        this.realAdmin = realAdmin;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.campaignService = campaignService;
        this.dbAuthenticator = dbAuthenticator;
    }

    private void printEncodedPassword(String rawPassword) {
        String encoded = passwordEncoder.encode(rawPassword);
        System.out.println("Encoded password: " + encoded);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Admin CLI");

        // Login Step
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if(dbAuthenticator.login(email, password))
        {
            System.out.println("LOGIN SUCCESSFUL!!");
            return;
        }
        else{
            System.out.println("5");
        }
        


        User user = userService.getAllUsers().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email) && passwordEncoder.matches(password, u.getPassword()))
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

        System.out.println("\nLogin successful. Welcome, " + user.getName());

        AdminProxy adminProxy = new AdminProxy(user, realAdmin);

        boolean running = true;
        while (running) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Create User");
            System.out.println("2. Edit User");
            System.out.println("3. Delete User");
            System.out.println("4. Edit Campaign");
            System.out.println("5. Delete Campaign");
            System.out.println("6. Create User Group");
            System.out.println("7. Add User to User Group");
            System.out.println("8. Display User Group Details");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createUser(scanner, adminProxy);
                case 2 -> editUser(scanner, adminProxy);
                case 3 -> deleteUser(scanner, adminProxy);
                case 4 -> editCampaign(scanner, adminProxy);
                case 5 -> deleteCampaign(scanner, adminProxy);
                case 6 -> createUserGroup(scanner);
                case 7 -> addUserToGroup(scanner);
                case 8 -> displayGroupDetails(scanner);
                case 9 -> {
                    System.out.println("Exiting Admin CLI...");
                    running = false;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private void createUser(Scanner scanner, AdminProxy adminProxy) {
        System.out.print("Enter User Name: ");
        String userName = scanner.nextLine();

        System.out.print("Enter User Mail: ");
        String userMail = scanner.nextLine();

        System.out.print("Enter User Phone: ");
        String userPhone = scanner.nextLine();

        System.out.print("Enter User Password: ");
        String userPassword = scanner.nextLine();

        System.out.print("Set User Type (0: Person, 1: Charity Org, 2: Admin): ");
        int userType = scanner.nextInt();
        scanner.nextLine();

        Random random = new Random();
        User createdUser;
        if (userType == 0) {
            createdUser = new NormalUser(random.nextInt(100000), userType, userName, userMail, userPhone, userPassword);
        } else if (userType == 1) {
            createdUser = new CharityOrganizationUser(random.nextInt(100000), userType, userName, userMail, userPhone, userPassword);
        } else {
            createdUser = new AdminUser(random.nextInt(100000), userType, userName, userMail, userPhone, userPassword, 0);
        }

        adminProxy.createUser(createdUser);
        System.out.println("User Created Successfully!");
    }

    private void editUser(Scanner scanner, AdminProxy adminProxy) {
        System.out.print("Enter User ID to edit: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        Optional<User> optionalUser = userService.getUserById(userId);
        if (optionalUser.isEmpty()) {
            System.out.println("User not found!");
            return;
        }

        User userToEdit = optionalUser.get();

        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();

        userToEdit.setName(newName);
        adminProxy.editUser(userToEdit.getUserId(), userToEdit);
        System.out.println("User updated successfully.");
    }

    private void deleteUser(Scanner scanner, AdminProxy adminProxy) {
        System.out.print("Enter User ID to delete: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        Optional<User> optionalUser = userService.getUserById(userId);
        if (optionalUser.isEmpty()) {
            System.out.println("User not found!");
            return;
        }

        User userToDelete = optionalUser.get();
        adminProxy.deleteUser(userToDelete);
        System.out.println("User deleted successfully.");
    }

    private void editCampaign(Scanner scanner, AdminProxy adminProxy) {
        System.out.print("Enter Campaign ID to edit: ");
        long campaignId = scanner.nextLong();
        scanner.nextLine();

        Optional<Campaign> optionalCampaign = campaignService.getCampaignById(campaignId);
        if (optionalCampaign.isEmpty()) {
            System.out.println("Campaign not found!");
            return;
        }

        Campaign campaignToEdit = optionalCampaign.get();

        System.out.print("Enter new campaign name: ");
        String newName = scanner.nextLine();

        campaignToEdit.setName(newName);
        adminProxy.editCampaign(campaignToEdit.getID(), campaignToEdit);
        System.out.println("Campaign updated successfully.");
    }

    private void deleteCampaign(Scanner scanner, AdminProxy adminProxy) {
        System.out.print("Enter Campaign ID to delete: ");
        long campaignId = scanner.nextLong();

        adminProxy.deleteCampaign(campaignId);
        System.out.println("Campaign deleted successfully.");
    }

    private void createUserGroup(Scanner scanner) {
        System.out.print("Enter user group name: ");
        String groupName = scanner.nextLine();

        UserGroup userGroup = new UserGroup(groupName);
        userService.addUserGroup(userGroup);
        System.out.println("User group created successfully.");
    }

    private void addUserToGroup(Scanner scanner) {
        System.out.print("Enter group ID: ");
        Long groupId = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Enter User ID to add to group: ");
        Integer userId = scanner.nextInt();
        scanner.nextLine();

        userService.addUserToGroup(groupId, userId);
        System.out.println("User added to the group successfully.");
    }

    private void displayGroupDetails(Scanner scanner) {
        System.out.print("Enter group ID to display details: ");
        Long groupId = scanner.nextLong();
        scanner.nextLine();

        userService.displayGroupDetails(groupId);
    }
}
