package Model;

//Keeps the information about a single event
    //Name
    //Date
    //Location
    //Description
    //Category

public class Event {
    String eventName;
    String eventDate;
    String eventLocation;
    String eventDescription;
    String eventCategory;

    public Event(String eventName, String eventDate, String eventLocation, String eventDescription, String eventCategory)
    {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.eventDescription = eventDescription;
        this.eventCategory = eventCategory;
    }

    public String getEventName() {
        return this.eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return this.eventDate;
    }

    public void setEventDate(String eventDate)
    {
        this.eventDate = eventDate;
    }

    public String getEventLocation() {
        return this.eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventDescription() {
        return this.eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventCategory() {
        return this.eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventName = eventCategory;
    }
}

