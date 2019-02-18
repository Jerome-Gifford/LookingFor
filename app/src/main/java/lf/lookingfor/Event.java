package lf.lookingfor;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {
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
    private String currentUserId;
    private String id;

    public Event(){

    }

    public Event(Event copyEvent){
        this.name = copyEvent.name;
        this.startTime = copyEvent.startTime;
        this.endTime = copyEvent.endTime;
        this.date = copyEvent.date;
        this.minParticipants = copyEvent.minParticipants;
        this.maxParticipants = copyEvent.maxParticipants;
        this.ageMin = copyEvent.ageMin;
        this.ageMax = copyEvent.ageMax;
        this.description = copyEvent.description;
        this.eventAddress = copyEvent.eventAddress;
        this.eventCity = copyEvent.eventCity;
        this.eventState = copyEvent.eventState;
        this.eventZip = copyEvent.eventZip;
        this.category = copyEvent.category;
        this.currentUserId = copyEvent.currentUserId;
    }

    public Event(String name, String startTime, String endTime, String date, int minParticipants,
                 int maxParticipants, int ageMin, int ageMax, String description, String eventAddress,
                 String eventCity, String eventState, String eventZip, String category, String currentUserId) {
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
        this.currentUserId = currentUserId;
    }

    protected Event(Parcel in) {
        name = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        date = in.readString();
        minParticipants = in.readInt();
        maxParticipants = in.readInt();
        ageMin = in.readInt();
        ageMax = in.readInt();
        description = in.readString();
        eventAddress = in.readString();
        eventCity = in.readString();
        eventState = in.readString();
        eventZip = in.readString();
        category = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

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

    public String getCurrentUserId() {return currentUserId;}

    public void setCurrentUserId(String userId) {
        this.currentUserId = userId;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeString(this.date);
        dest.writeInt(this.minParticipants);
        dest.writeInt(this.maxParticipants);
        dest.writeInt(this.ageMin);
        dest.writeInt(this.ageMax);
        dest.writeString(this.description);
        dest.writeString(this.eventAddress);
        dest.writeString(this.eventCity);
        dest.writeString(this.eventState);
        dest.writeString(this.eventZip);
        dest.writeString(this.category);
    }
}
