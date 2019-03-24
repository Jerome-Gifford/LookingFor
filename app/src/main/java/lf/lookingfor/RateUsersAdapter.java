package lf.lookingfor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class RateUsersAdapter extends RecyclerView.Adapter<RateUsersAdapter.ViewHolder> {

    private ArrayList<User> users;

    RateUsersAdapter(ArrayList<User> users){
        this.users = users;
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
                    bundle.putParcelable("user", users.get(position));

                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    ViewUsersFragment myFragment = new ViewUsersFragment();
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
        viewHolder.itemTitle.setText(users.get(i).getDisplayName());
        viewHolder.itemDetail.setText(users.get(i).getUserEmail());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void updateArrayList(ArrayList<User> newList){
        users = new ArrayList<User>();
        users.addAll(newList);
        notifyDataSetChanged();
    }
}
