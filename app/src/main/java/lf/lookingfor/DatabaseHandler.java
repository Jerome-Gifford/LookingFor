package lf.lookingfor;

import android.widget.ArrayAdapter;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DatabaseHandler {
    DatabaseReference databaseEvents = FirebaseDatabase.getInstance().getReference("events");
    DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("users");
    ArrayList<Event> events = new ArrayList<>();
    ArrayList<User> users = new ArrayList<>();

    public DatabaseHandler() {
        databaseEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                events.clear();
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()){
                    Event event = messageSnapshot.getValue(Event.class);
                    events.add(event);
                }

                RecyclerAdapter adapter = new RecyclerAdapter(events);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read has failed");
            }
        });

        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()){
                    User user = messageSnapshot.getValue(User.class);
                    users.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read has failed");
            }
        });
    }

    public ArrayList<Event> getEvents(){
        return events;
    }

    public ArrayList<User> getUsers() { return users; }

    public Event getEvent(String eventId){
        for (Event event: events) {
            if(event.getId().equals(eventId)){
                return event;
            }
        }
        return null;
    }

    public User getUser(String userID){
        for(User user:users){
            if(userID.equals(user.getUserId())){
                return user;
            }
        }
        return null;
    }
}