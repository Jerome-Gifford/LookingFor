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
    DatabaseReference database = FirebaseDatabase.getInstance().getReference("events");
    ArrayList<Event> events = new ArrayList<>();

    public DatabaseHandler() {
        database.addValueEventListener(new ValueEventListener() {
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
    }

    public ArrayList<Event> getEvents(){
        return events;
    }

    public Event getEvent(String eventId){
        for (Event event: events) {
            if(event.getId().equals(eventId)){
                return event;
            }
        }
        return null;
    }
}