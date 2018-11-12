package lf.lookingfor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.cert.TrustAnchor;

public class CreateEventFragment extends Fragment {
    EditText event_name;
    EditText event_date;
    EditText start_time;
    EditText end_time;
    EditText min_number;
    EditText max_number;
    EditText min_age;
    EditText max_age;
    EditText event_desc;
    EditText event_address;
    EditText event_city;
    EditText event_state;
    EditText event_ZIP;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.title_fragment_create_event);
        return inflater.inflate(R.layout.fragment_create_event, null);
    }

    @Override
    public void onViewCreated(@NonNull final View mainView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(mainView, savedInstanceState);

        mainView.findViewById(R.id.create_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String eventResult = createEvent(mainView);
                if(eventResult.equals("Event Created")) {
                    Toast.makeText(getActivity(), "Event Created", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "You are missing " + eventResult, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public String createEvent(View view){
        try{
            event_name = (EditText) view.findViewById(R.id.event_name);
            event_date= (EditText) view.findViewById(R.id.event_date);
            start_time= (EditText) view.findViewById(R.id.start_time);
            end_time= (EditText) view.findViewById(R.id.end_time);
            min_number= (EditText) view.findViewById(R.id.min_number);
            max_number= (EditText) view.findViewById(R.id.max_number);
            min_age= (EditText) view.findViewById(R.id.min_age);
            max_age= (EditText) view.findViewById(R.id.max_age);
            event_desc= (EditText) view.findViewById(R.id.event_desc);
            event_address= (EditText) view.findViewById(R.id.event_address);
            event_city= (EditText) view.findViewById(R.id.event_city);
            event_state= (EditText) view.findViewById(R.id.event_state);
            event_ZIP= (EditText) view.findViewById(R.id.event_ZIP);
            String eventName = event_name.getText().toString().trim();
            if(eventName.equals("")){
                return "Event Name";
            }
            String startTime = start_time.getText().toString().trim();
            String endTime = end_time.getText().toString().trim();
            String eventDate = event_date.getText().toString().trim();
            if(eventDate.equals("")){
                return "Event Date";
            }
            int minParticipants = Integer.parseInt(min_number.getText().toString().trim());
            int maxParticipants = Integer.parseInt(max_number.getText().toString().trim());
            int minAge = Integer.parseInt(min_age.getText().toString().trim());
            int maxAge = Integer.parseInt(max_age.getText().toString().trim());
            String eventDesc = event_desc.getText().toString().trim();
            String eventAddress = event_address.getText().toString().trim();
            String eventCity = event_city.getText().toString().trim();
            String eventState = event_city.getText().toString().trim();
            String eventZip = event_ZIP.getText().toString().trim();
            Event event = new Event(eventName, startTime, endTime, eventDate, minParticipants, maxParticipants,
                minAge, maxAge, eventDesc, eventAddress, eventCity, eventState, eventZip);
            DatabaseReference myRef = database.getReference("events").push();
            myRef.setValue(event);
        }
        catch (Exception e){
            return "Participants or Age";
        }
        return "Event Created";
    }
}