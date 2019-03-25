package lf.lookingfor;

import java.util.ArrayList;

public class Registration {
    private int minimumParticipants;
    private int maximumParticipants;
    private String eventId;
    private ArrayList<String> registeredUsers;

    public Registration() {
    }

    public Registration(int minimumParticipants, int maximumParticipants, String eventId, ArrayList<String> registeredUsers) {
        this.minimumParticipants = minimumParticipants;
        this.maximumParticipants = maximumParticipants;
        this.eventId = eventId;
        this.registeredUsers = registeredUsers;
    }

    public int getMinimumParticipants() {
        return minimumParticipants;
    }

    public void setMinimumParticipants(int minimumParticipants) {
        this.minimumParticipants = minimumParticipants;
    }

    public int getMaximumParticipants() {
        return maximumParticipants;
    }

    public void setMaximumParticipants(int maximumParticipants) {
        this.maximumParticipants = maximumParticipants;
    }

    public ArrayList<String> getRegisteredUsers() {
        return registeredUsers;
    }

    public void setRegisteredUsers(ArrayList<String> registeredUsers) {
        this.registeredUsers = registeredUsers;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void addUser(String userId){
        if(registeredUsers.contains(userId)){
            return;
        }
        else {
            this.registeredUsers.add(userId);
        }
    }
}
