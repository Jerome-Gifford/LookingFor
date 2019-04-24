package lf.lookingfor;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ViewEventFragment extends Fragment {
    Event event;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Registration registration = new Registration();
    FirebaseUser fUser;
    FirebaseAuth fAuth;
    String userId;
    String users = "";
    final DatabaseReference myRef = database.getReference("registration");
    final DatabaseReference myRef2 = database.getReference("events");
    final DatabaseReference myRef3 = database.getReference("notifications");
    final DatabaseReference groupRef = database.getReference("groups");
    String token = new String();
    UserGroup group = new UserGroup();
    User notifyUser = new User();
    ArrayList<User> groupMembers = new ArrayList<User>();
    ArrayList<UserGroup> groups = new ArrayList<UserGroup>();
    ArrayList<String> regUserIds = new ArrayList<String>();
    ArrayList<String> regUserTokens = new ArrayList<String>();
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
        final Button inviteButton = (Button)mainView.findViewById(R.id.btn_invite);

        final Spinner groupSpinner = (Spinner)mainView.findViewById(R.id.groupSpinner);
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        groupSpinner.setAdapter(spinnerAdapter);

        spinnerAdapter.add("Select a group to invite...");

        spinnerAdapter.notifyDataSetChanged();

        groupSpinner.setPrompt("Select a group to invite...");
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
        eventTime.setText("Time: " + event.getStartTime() + " - " + event.getEndTime() + "\n");
        eventDesc.setText("\nDescription: "+ event.getDescription() + "\n");
        eventLoc.setText("Location: " + event.getEventAddress() + "\n");
        eventDate.setText("Date: " + event.getDate() + "\n");
        eventCategory.setText("Category: " + event.getCategory() + "\n");
        eventMembers.setText(users);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    registration = messageSnapshot.getValue(Registration.class);
                    if(registration.getEventId().equals(event.getId())){
                        registrationId = messageSnapshot.getKey();
                        eventParticipants.setText("Number of participants: " + registration.getRegisteredUsers().size() + "/" + registration.getMaximumParticipants() + "\n");
                        if(registration.getRegisteredUsers().size() == registration.getMaximumParticipants()){
                            joinButton.setClickable(false);
                            joinButton.setText("Event Full");
                        }
                        regUserIds = registration.getRegisteredUsers();
                        break;
                    }
                }
                addUsers();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read has failed");
            }
        });

        groups.clear();
        groupRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    UserGroup group = messageSnapshot.getValue(UserGroup.class);
                    groups.add(group);
                }

                for(int i = 0; i < groups.size(); i++){
                    ArrayList<User> currUsers = groups.get(i).getMembers();
                    for (int j = 0; j < currUsers.size(); j++){
                        if(currUsers.get(j).getUserId().equals(fUser.getUid())){
                            spinnerAdapter.add(groups.get(i).getGroupName());
                        }
                    }
                }

                spinnerAdapter.notifyDataSetChanged();
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
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                cancelEvent();
            }
        });

        inviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(groupSpinner.getSelectedItemPosition() != 0){
                    //minus one because of select option at top
                    inviteGroup(groups.get(groupSpinner.getSelectedItemPosition() - 1), event.getName());
                }
            }
        });
    }

    private void addUsers() {
        final DatabaseReference myRefUsers = database.getReference("users");
        myRefUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    String userId = messageSnapshot.getKey();
                    if(registration.getRegisteredUsers().contains(userId)){
                        users += messageSnapshot.child("displayName").getValue().toString() + ", ";
                        regUserTokens.add(messageSnapshot.child("messaging_token").getValue().toString());
                        updateMembers();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read has failed");
            }
        });

    }

    private void cancelEvent() {
        if(regUserIds.size() == regUserTokens.size()){
            Collections.reverse(regUserIds);
            for(int i = 0; i < regUserIds.size(); i++){
                Notification notification = new Notification("Cancellation Notification", "Your event " + event.getName() + " has been cancelled", regUserTokens.get(i), regUserIds.get(i));
                DatabaseReference notificationsReference = database.getReference("notifications").push();
                String id = notificationsReference.getKey();
                notificationsReference.setValue(notification);
                //final DatabaseReference finalRef = notificationsReference.child(regUserIds.get(i));
                //finalRef.setValue(notification);
            }
        }
        myRef2.child(registration.getEventId()).removeValue();
        myRef.child(registrationId).removeValue();
        Toast.makeText(getActivity(), "Event Cancelled", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getContext(), MainActivity.class));
    }

    private void inviteGroup(Object objGroup, String eventName) {
        group = (UserGroup) objGroup;
        groupMembers = group.getMembers();
        for(int i = 0; i < groupMembers.size(); i++){
            notifyUser = groupMembers.get(i);
            DatabaseReference tokenRef = database.getReference("users").child(groupMembers.get(i).getUserId()).child("messaging_token");
            tokenRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    token = dataSnapshot.getValue(String.class);
                    Notification notification = new Notification("Group Invite", "You have been invited to " + event.getName()+ " by a member of your group, " + group.getGroupName(), token, notifyUser.getUserId());
                    DatabaseReference notificationsReference = database.getReference("notifications").push();
                    String id = notificationsReference.getKey();
                    notificationsReference.setValue(notification);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        Toast.makeText(getActivity(), "Group Invited", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getContext(), MainActivity.class));
    }

    private View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseAuth fAuth = FirebaseAuth.getInstance();
            FirebaseUser fUser = fAuth.getCurrentUser();
            final String userId = fUser.getUid();
            registration.addUser(userId);
            myRef.child(registrationId).setValue(registration);
            Toast.makeText(getActivity(), "Event Joined", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getContext(), MainActivity.class));
        }

    };

    private void updateMembers(){
        eventMembers.setText(users);
    }
}
