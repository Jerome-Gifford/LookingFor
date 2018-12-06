package lf.lookingfor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
        TextView eventName = (TextView) mainView.findViewById(R.id.event_title);
        TextView eventDesc = (TextView) mainView.findViewById(R.id.event_desc);
        TextView eventTime = (TextView) mainView.findViewById(R.id.event_time);
        TextView eventLoc = (TextView) mainView.findViewById(R.id.event_location);
        TextView eventCategory = (TextView) mainView.findViewById(R.id.event_category);
        TextView eventMembers = (TextView) mainView.findViewById(R.id.event_members);
        Button btn_event = (Button) mainView.findViewById(R.id.btn_event);
        btn_event.setOnClickListener(btnListener);
        eventName.setText(event.getName());
        eventTime.setText(event.getStartTime() + " - " + event.getEndTime());
        eventDesc.setText(event.getDescription());
        eventLoc.setText(event.getEventAddress());
        eventCategory.setText(event.getCategory());
        eventMembers.setText(Integer.toString(event.getMaxParticipants()));
        Button joinButton = (Button)mainView.findViewById(R.id.btn_event);

        if(event.getCurrentUserId().equals(userId)){
            joinButton.setVisibility(View.INVISIBLE);
            joinButton.setClickable(false);
        }
    }

    private View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseAuth fAuth = FirebaseAuth.getInstance();
            FirebaseUser fUser = fAuth.getCurrentUser();
            final String userId = fUser.getUid();
            final DatabaseReference myRef = database.getReference("registration");
            System.out.println(myRef);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                        System.out.println("Here");
                        registration = messageSnapshot.getValue(Registration.class);
                        if(registration.getEventId().equals(event.getId())){
                            registration.addUser(userId);
                            myRef.child(messageSnapshot.getKey()).setValue(registration);
                            System.out.println(registration.getRegisteredUsers());
                            return;
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read has failed");
                }
            });
        }

    };
}
