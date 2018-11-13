package lf.lookingfor;

public class Event {
    private String name;
    private String startTime;
    private String endTime;
    private String date;
    private int minParticipants;
    private int maxParticipants;
    private int ageMin;
    private int ageMax;
    private String description;
    private String eventAddress;
    private String eventCity;
    private String eventState;
    private String eventZip;
    private String category;


    public Event(String name, String startTime, String endTime, String date, int minParticipants,
                 int maxParticipants, int ageMin, int ageMax, String description, String eventAddress,
                 String eventCity, String eventState, String eventZip, String category) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.minParticipants = minParticipants;
        this.maxParticipants = maxParticipants;
        this.ageMin = ageMin;
        this.ageMax = ageMax;
        this.description = description;
        this.eventAddress = eventAddress;
        this.eventCity = eventCity;
        this.eventState = eventState;
        this.eventZip = eventZip;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public String getEventCity() {
        return eventCity;
    }

    public void setEventCity(String eventCity) {
        this.eventCity = eventCity;
    }

    public String getEventState() {
        return eventState;
    }

    public void setEventState(String eventState) {
        this.eventState = eventState;
    }

    public String getEventZip() {
        return eventZip;
    }

    public void setEventZip(String eventZip) {
        this.eventZip = eventZip;
    }

    public int getMinParticipants() {
        return minParticipants;
    }

    public void setMinParticipants(int minParticipants) {
        this.minParticipants = minParticipants;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public int getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(int ageMin) {
        this.ageMin = ageMin;
    }

    public int getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(int ageMax) {
        this.ageMax = ageMax;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   // public String getCategory() {
    //    return category;
    //}

    //public void setCategory(String category) {
    //    this.category = category;
    //}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
