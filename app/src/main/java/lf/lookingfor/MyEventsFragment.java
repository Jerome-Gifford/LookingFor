package lf.lookingfor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MyEventsFragment extends Fragment {

    FirebaseAuth fAuth;
    FirebaseUser fUser;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(R.string.title_fragment_my_events);
        return inflater.inflate(R.layout.fragment_my_events, null);
    }

    public void onViewCreated(@NonNull final View mainView, @Nullable Bundle savedInstanceState) {

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        String userId = fUser.getUid();

        ArrayList<Event> userEvents;

        String[] titles = {"Event One",
                "Event Two",
                "Event Three",
                "Event Four",
                "Event Five",
                "Event Six",
                "Event Seven",
                "Event Eight"};

        String[] details = {"Item one details",
                "Item two details", "Item three details",
                "Item four details", "Item file details",
                "Item six details", "Item seven details",
                "Item eight details"};

        RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;
        RecyclerView.Adapter adapter;

        recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapter(titles, details);
        recyclerView.setAdapter(adapter);

        //return recyclerView;
    }
}
