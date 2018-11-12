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
                createEvent(mainView);
                Toast.makeText(getActivity(), "Create Event!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createEvent(View view){
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
        Event event = new Event(
                event_name.getText().toString().trim(),
                start_time.getText().toString().trim(),
                end_time.getText().toString().trim(),
                event_date.getText().toString().trim(),
                Integer.parseInt(min_number.getText().toString().trim()),
                Integer.parseInt(max_number.getText().toString().trim()),
                Integer.parseInt(min_age.getText().toString().trim()),
                Integer.parseInt(max_age.getText().toString().trim()),
                event_desc.getText().toString().trim()
        );
        DatabaseReference myRef = database.getReference("events").push();
        myRef.setValue(event);
    }
}
