package lf.lookingfor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewEventFragment extends Fragment {
    Event event;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Event");
        Bundle bundle = getArguments();
        event = bundle.getParcelable("event");

        return inflater.inflate(R.layout.fragment_view_event, null);
    }
    public void onViewCreated(@NonNull final View mainView, @Nullable Bundle savedInstanceState){
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
    }

    public void addUserToEvent(View view){

    }

}
