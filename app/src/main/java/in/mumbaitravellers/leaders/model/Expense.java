package in.mumbaitravellers.leaders.model;

/**
 * Created by Administrator on 06-10-2016.
 */
public class Expense {

    //@PrimaryKey
    private int id;
    private int  eventId;
    private String amount;
    private String description;

    //Constructors
    public Expense() {
    }

    public Expense(int id, int eventId, String amount, String description) {
        this.id = id;
        this.eventId = eventId;
        this.amount = amount;
        this.description = description;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
