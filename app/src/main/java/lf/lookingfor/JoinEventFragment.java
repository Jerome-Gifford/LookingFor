package lf.lookingfor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class JoinEventFragment extends Fragment implements SearchView.OnQueryTextListener {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<Event> events = new ArrayList<>();
    RecyclerAdapter adapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.title_fragment_join_event);
        return inflater.inflate(R.layout.fragment_join_events, null);
    }

    public void onViewCreated(@NonNull final View mainView, @Nullable Bundle savedInstanceState) {
         DatabaseReference myRef = database.getReference("events");
         myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    Event event = messageSnapshot.getValue(Event.class);
                    events.add(event);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read has failed");
            }
        });
            RecyclerView recyclerView;
            RecyclerView.LayoutManager layoutManager;


            recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);

            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new RecyclerAdapter(events);
            recyclerView.setAdapter(adapter);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        ArrayList<Event> newList = new ArrayList<>();

        for (Event e : events){
            if(e.getName().toLowerCase().contains(userInput) || e.getDescription().toLowerCase().contains(userInput)){
                newList.add(e);
            }
        }
        adapter.updateArrayList(newList);
        return true;
    }
}
