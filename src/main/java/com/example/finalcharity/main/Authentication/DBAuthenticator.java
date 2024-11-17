package com.example.finalcharity.main.Authentication;



public class DBAuthenticator implements AuthenticationProvider {

    @Override
    public boolean login(String email, String password) {
//        return userService.findByEmail(email)
//                .map(user -> user.getPassword().equals(password))
//                .orElse(false);
        return true;
    }

    @Override
    public boolean register(String email, String password) {
        // Implement database registration logic here
//        if (userService.findByEmail(email).isPresent()) {
//            return false; // User already exists
//        }
//
//        User newUser = new User();
//        newUser.setEmail(email);
//        newUser.setPassword(password);
//        userService.saveUser(newUser);
        return true;
    }
}
