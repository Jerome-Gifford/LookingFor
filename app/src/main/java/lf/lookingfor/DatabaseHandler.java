package lf.lookingfor;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DatabaseHandler {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<Event> events = new ArrayList<>();

    public ArrayList<Event> getAllEvents() {
        events.clear();
        DatabaseReference myRef = database.getReference("events");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()){
                    Event event = messageSnapshot.getValue(Event.class);
                    events.add(event);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read has failed");
            }
        });

        return events;
    }

    public ArrayList<Event> getUserEvents(final FirebaseUser user){
        events.clear();
        DatabaseReference myRef = database.getReference("events");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    Event event = messageSnapshot.getValue(Event.class);
                    if(events.size() > 0){
                        for(Event currentEvent: events) {
                            if(!currentEvent.getCurrentUserId().equals(event.getCurrentUserId()) && !currentEvent.getName().equals(event.getName()) && !currentEvent.getDescription().equals(event.getDescription())){
                                if(event.getCurrentUserId().equals(user.getUid())){
                                    events.add(event);
                                }
                            }
                        }
                    } else{
                        if(event.getCurrentUserId().equals(user.getUid())){
                            events.add(event);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read has failed");
            }
        });

        return events;
    }

    public Event getEvent(final String eventId){
        events.clear();
        final Event[] retEvent = new Event[1];
        DatabaseReference myRef = database.getReference("events");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    Event event = messageSnapshot.getValue(Event.class);
                    if(event.getId() == eventId){
                        retEvent[0] = new Event(event);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read has failed");
            }
        });

        return retEvent[0];
    }
}