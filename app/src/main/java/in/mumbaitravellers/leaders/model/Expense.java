package in.mumbaitravellers.leaders.model;

/**
 * Created by Administrator on 06-10-2016.
 */
public class Expense {

    //@PrimaryKey
    private int id;
    private int  eventId;
    private int amount;
    private String type;
    private String description;

    //Constructors
    public Expense() {
    }

    public Expense(int id, int eventId, int amount, String description) {
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
