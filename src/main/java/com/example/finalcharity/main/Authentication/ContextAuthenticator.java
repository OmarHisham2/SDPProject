package com.example.finalcharity.main.Authentication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContextAuthenticator {
    private AuthenticationProvider strategy;
    private final GoogleAuthenticator googleAuthenticator;
    private final DBAuthenticator dbAuthenticator;


    @Autowired
    public ContextAuthenticator() {
        this.googleAuthenticator=new GoogleAuthenticator();
        this.dbAuthenticator=new DBAuthenticator();
        this.strategy = dbAuthenticator;
    }

    public ContextAuthenticator setProvider(AuthenticationProvider strategy) {
        this.strategy = strategy;
        return this;
    }

    public boolean login(String email, String password,Long method) {
        if (method==1)  // google
        {
            this.setProvider(googleAuthenticator);
        }
        else if (method==2) // db
        {
            this.setProvider(dbAuthenticator);
        }
        else {
            return false;
        }

        return strategy.login(email, password);

    }

    public boolean register(String email, String password,Long method) {
        if (method==1)  // google
        {
            this.setProvider(googleAuthenticator);
        }
        else if (method==2) // db
        {
            this.setProvider(dbAuthenticator);
        }
        else {
            return false;
        }

        return strategy.register(email, password);
    }
}
