package Application.Model;

/**
 * The Event class stores information about a single event: name, date,
 * location, description, and category.
 *
 * @author Waewarin Chindarassami
 */

public class Event {
    private String eventName;
    private String eventDate;
    private String eventLocation;
    private String eventCategory;
    private String eventDescription;

    public Event(String eventName, String eventDate, String eventLocation, String eventCategory, String eventDescription)
    {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.eventCategory = eventCategory;
        this.eventDescription = eventDescription;
    }

    public String getEventName()
    {
        return this.eventName;
    }

    public void setEventName(String eventName)
    {
        this.eventName = eventName;
    }

    public String getEventDate()
    {
        return this.eventDate;
    }

    public void setEventDate(String eventDate)
    {
        this.eventDate = eventDate;
    }

    public String getEventLocation()
    {
        return this.eventLocation;
    }

    public void setEventLocation(String eventLocation)
    {
        this.eventLocation = eventLocation;
    }

    public String getEventCategory()
    {
        return this.eventCategory;
    }

    public void setEventCategory(String eventCategory)
    {
        this.eventCategory = eventCategory;
    }

    public String getEventDescription()
    {
        return this.eventDescription;
    }

    public void setEventDescription(String eventDescription)
    {
        this.eventDescription = eventDescription;
    }
}

