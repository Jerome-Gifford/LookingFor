package lf.lookingfor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewEventFragment extends Fragment {
    Event event;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Registration registration;
    FirebaseUser fUser;
    FirebaseAuth fAuth;
    String userId;
    String users = "";
    final DatabaseReference myRef = database.getReference("registration");
    final DatabaseReference myRef2 = database.getReference("events");
    String registrationId;
    TextView eventMembers;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Event");
        Bundle bundle = getArguments();
        event = bundle.getParcelable("event");
        return inflater.inflate(R.layout.fragment_view_event, null);
    }

    public void onViewCreated(@NonNull final View mainView, @Nullable Bundle savedInstanceState) {
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        userId = fUser.getUid();
        final Button joinButton = (Button)mainView.findViewById(R.id.btn_event);
        final Button cancelButton = (Button)mainView.findViewById(R.id.btn_cancel);
        TextView eventName = (TextView) mainView.findViewById(R.id.event_title);
        TextView eventDate = (TextView) mainView.findViewById(R.id.event_date);
        TextView eventDesc = (TextView) mainView.findViewById(R.id.event_desc);
        TextView eventTime = (TextView) mainView.findViewById(R.id.event_time);
        TextView eventLoc = (TextView) mainView.findViewById(R.id.event_location);
        TextView eventCategory = (TextView) mainView.findViewById(R.id.event_category);
        final TextView eventParticipants = (TextView) mainView.findViewById(R.id.event_participant_numbers);
        eventMembers = (TextView) mainView.findViewById(R.id.event_members);
        Button btn_event = (Button) mainView.findViewById(R.id.btn_event);
        btn_event.setOnClickListener(btnListener);
        eventName.setText(event.getName());
        eventTime.setText(event.getStartTime() + " - " + event.getEndTime());
        eventDesc.setText(event.getDescription());
        eventLoc.setText(event.getEventAddress());
        eventDate.setText(event.getDate());
        eventCategory.setText(event.getCategory());
        eventMembers.setText(users);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    registration = messageSnapshot.getValue(Registration.class);
                    if(registration.getEventId().equals(event.getId())){
                        registrationId = messageSnapshot.getKey();
                        eventParticipants.setText("Number of participants: " + registration.getRegisteredUsers().size() + "/" + registration.getMaximumParticipants());
                        if(registration.getRegisteredUsers().size() == registration.getMaximumParticipants()){
                            joinButton.setClickable(false);
                            joinButton.setText("Event Full");
                        }
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read has failed");
            }
        });

        final DatabaseReference myRefUsers = database.getReference("users");
        myRefUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    String userId = messageSnapshot.getKey();
                    if(registration.getRegisteredUsers().contains(userId)){
                        users += messageSnapshot.child("displayName").getValue().toString() + ", ";
                        updateMembers();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read has failed");
            }
        });

        if(event.getCurrentUserId().equals(userId)){
            joinButton.setVisibility(View.INVISIBLE);
            joinButton.setClickable(false);
        }
        else {
            cancelButton.setVisibility(View.INVISIBLE);
            cancelButton.setClickable(false);
        }
        mainView.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                cancelEvent();
            }
        });
    }

    private void cancelEvent() {
        myRef2.child(registration.getEventId()).removeValue();
        myRef.child(registrationId).removeValue();
    }

    private View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseAuth fAuth = FirebaseAuth.getInstance();
            FirebaseUser fUser = fAuth.getCurrentUser();
            final String userId = fUser.getUid();
            registration.addUser(userId);
            myRef.child(registrationId).setValue(registration);
        }

    };

    private void updateMembers(){
        eventMembers.setText(users);
    }
}
