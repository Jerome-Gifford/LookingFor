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

public class ViewEventFragment extends Fragment {
    Event event;
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
    public void onViewCreated(@NonNull final View mainView, @Nullable Bundle savedInstanceState){
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        userId = fUser.getUid();

        TextView eventName = (TextView)mainView.findViewById(R.id.event_title);
        TextView eventDesc = (TextView)mainView.findViewById(R.id.event_desc);
        TextView eventTime = (TextView)mainView.findViewById(R.id.event_time);
        TextView eventLoc = (TextView)mainView.findViewById(R.id.event_location);
        TextView eventCategory = (TextView)mainView.findViewById(R.id.event_category);
        TextView eventMembers = (TextView)mainView.findViewById(R.id.event_members);
        eventName.setText(event.getName());
        eventTime.setText(event.getStartTime() + " - " + event.getEndTime());
        eventDesc.setText(event.getDescription());
        eventLoc.setText(event.getEventAddress());
        eventCategory.setText(event.getCategory());
        eventMembers.setText(Integer.toString(event.getMaxParticipants()));
        Button joinButton = (Button)mainView.findViewById(R.id.join);

        if(event.getCurrentUserId().equals(userId)){
            joinButton.setVisibility(View.INVISIBLE);
            joinButton.setClickable(false);
        }
    }

    public void addUserToEvent(View view){

    }

}
