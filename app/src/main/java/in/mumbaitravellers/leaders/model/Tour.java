package in.mumbaitravellers.leaders.model;

/**
 * Created by Administrator on 06-10-2016.
 */
public class Tour {

    //@PrimaryKey
    private int id;
    private int eventId;
    private String eventName;
    private String eventStartDate;
    private String eventEndDate;
    private String leaders;
    private String cashCarried;
    private String onTourCollection;
    private String createdAt;

    //Constructors
    public Tour() {

    }

    public Tour(int eventId, String eventName, String eventStartDate, String eventEndDate,
                String leaders, String cashCarried, String onTourCollection) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.leaders = leaders;
        this.cashCarried = cashCarried;
        this.onTourCollection = onTourCollection;
    }

    public Tour(int id, int eventId, String eventName, String eventStartDate, String eventEndDate,
                String leaders, String cashCarried, String onTourCollection) {
        this.id = id;
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.leaders = leaders;
        this.cashCarried = cashCarried;
        this.onTourCollection = onTourCollection;
    }


    //Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(String eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public String getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(String eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public String getLeaders() {
        return leaders;
    }

    public void setLeaders(String leaders) {
        this.leaders = leaders;
    }

    public String getCashCarried() {
        return cashCarried;
    }

    public void setCashCarried(String cashCarried) {
        this.cashCarried = cashCarried;
    }

    public String getOnTourCollection() {
        return onTourCollection;
    }

    public void setOnTourCollection(String onTourCollection) {
        this.onTourCollection = onTourCollection;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
