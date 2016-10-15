package kz.sagrad.halykoperator;

import java.util.ArrayList;

/**
 * Created by Timur_hnimdvi on 15-Oct-16.
 */
public class User {
    public String fireId = "";
    public String name = "";
    public String windowNo = "";
    public ArrayList<String> services = new ArrayList<String>();
    public boolean loggedIn = true;
    public String action = "";
    public String currentTicketId = "";
    public int waiting = 0;
    public boolean available = true;

    public String getFireId() {
        return fireId;
    }

    public String getName() {
        return name;
    }

    public String getWindowNo() {
        return windowNo;
    }

    public ArrayList<String> getServices() {
        return services;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public String getAction() {
        return action;
    }

    public String getCurrentTicketId() {
        return currentTicketId;
    }
}
