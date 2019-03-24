package lf.lookingfor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Array;
import java.sql.Struct;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<Event> events;

    RecyclerAdapter(ArrayList<Event> events){
        this.events = events;
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public TextView itemTitle;
        public TextView itemDetail;
        public TextView itemCategory;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            itemDetail =
                    (TextView)itemView.findViewById(R.id.item_detail);
            itemCategory =
                    (TextView)itemView.findViewById(R.id.item_category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("event", events.get(position));

                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    ViewEventFragment myFragment = new ViewEventFragment();
                    myFragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.screen_area, myFragment).addToBackStack(null).commit();

                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemTitle.setText(events.get(i).getName());
        viewHolder.itemDetail.setText(events.get(i).getDescription());
        viewHolder.itemCategory.setText(events.get(i).getCategory());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void updateArrayList(ArrayList<Event> newList){
        events = new ArrayList<Event>();
        events.addAll(newList);
        notifyDataSetChanged();
    }
}
