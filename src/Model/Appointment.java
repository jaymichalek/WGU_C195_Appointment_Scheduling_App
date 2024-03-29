package Model;

import java.time.LocalDateTime;

/** This is the Appointment class.*/
public class Appointment {

    private int appointmentId;
    private String title;
    private String desc;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerId;
    private int userId;
    private int contactId;
    private String contactName;

    public Appointment(int appointmentId, String title, String desc, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId, String contactName) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.desc = desc;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.contactName = contactName;
    }

    public Appointment(int appointmentId, String type) {
        this.appointmentId = appointmentId;
        this.type = type;
    }

    /** @return Returns the appointment ID.*/
    public int getAppointmentId() {
        return appointmentId;
    }

    /** @return Returns the appointment title.*/
    public String getTitle() {
        return title;
    }

    /** @return Returns the appointment description.*/
    public String getDesc() {
        return desc;
    }

    /** @return Returns the appointment location.*/
    public String getLocation() {
        return location;
    }

    /** @return Returns the appointment type.*/
    public String getType() {
        return type;
    }

    /** @return Returns the appointment start time and date.*/
    public LocalDateTime getStart() {
        return start;
    }

    /** @return Returns the appointment end time and date.*/
    public LocalDateTime getEnd() {
        return end;
    }

    /** @return Returns the customer Id of who the appointment is for.*/
    public int getCustomerId() {
        return customerId;
    }

    /** @return Returns the user Id.*/
    public int getUserId() {
        return userId;
    }

    /** @return Returns the contact Id.*/
    public int getContactId() {
        return contactId;
    }

    /** @return Returns the contact name.*/
    public String getContactName() {
        return contactName;
    }

}
