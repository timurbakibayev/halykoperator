package kz.sagrad.halykoperator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * Created by Timur_hnimdvi on 15-Oct-16.
 */
public class Ticket {
    public String fireId = "";
    public String ticketNo = "";
    public String serviceNo = "";
    public Date created = new Date();
    public Date served = null;
    public String servedBy = null;
    public int priority = 0;
    public String forceUserId = "";

    @JsonIgnore
    public MenuElement menuElement;

    public Ticket() {

    }

    public Ticket(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getFireId() {
        return fireId;
    }

    public String getServiceNo() {
        return serviceNo;
    }

    public Date getCreated() {
        return created;
    }

    public Date getServed() {
        return served;
    }

    public String getServedBy() {
        return servedBy;
    }

    public int getPriority() {
        return priority;
    }

    public String getForceUserId() {
        return forceUserId;
    }
}
