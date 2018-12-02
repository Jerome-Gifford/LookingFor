package lf.lookingfor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class JoinEventFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(R.string.title_fragment_join_event);
        return inflater.inflate(R.layout.fragment_join_events, null);
    }

    public void onViewCreated(@NonNull final View mainView, @Nullable Bundle savedInstanceState) {
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
